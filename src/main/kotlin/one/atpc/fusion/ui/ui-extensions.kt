/*
 * Copyright (c) 2019  Thomas Orlando, ATPC
 *
 * This file is part of Fusion.
 *
 * Fusion is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Fusion is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Fusion.  If not, see <https://www.gnu.org/licenses/>.
 */

package one.atpc.fusion.ui

import one.atpc.fusion.util.UnBool
import javax.swing.JComponent
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

// Base for all Fusion components
typealias SwingComponent = JComponent

/**
 * The view description.
 * If the view is a control (subtype of [XControl]), the description should be **imperative**,
 * otherwise it should be **describing**. Take, for example, an instance of [XText]:
 * <br>
 * It's description of the text should only further describe it's **content**, or the gist of it.
 * An instance of [XButton], in contrast, is carrying out an action, so it's description
 * should describe it's **action** when activated by the user,
 * in the sense of _`"If you click this button, it will [description]"`_.
 * <br>
 * The description should not end with a dot.
 *
 * @see isDescriptionVisible
 */
var <T : SwingComponent> T.description: String? by DescriptionDelegate()


/**
 * Determines whether the [description] is actively shown to the user.
 * This means — in most cases — that the user will see a tooltip popping up
 * if the cursor highlights the component for a while.
 * If this property is `false`, the description will not be visible.
 */
var <T : SwingComponent> T.isDescriptionVisible: Boolean by IsDescriptionVisibleDelegate<T>()


val <T : SwingComponent> T.isControl: Boolean
    get() = this is AbstractButton || this is XControl


private class DescriptionDelegate<R : SwingComponent> : ReadWriteProperty<R, String?> {
    private var description: String? = null

    override operator fun getValue(thisRef: R, property: KProperty<*>): String?
            = this.description

    override operator fun setValue(thisRef: R, property: KProperty<*>, value: String?) {
        this.description = value
        // Update toolTipText
        thisRef.updateToolTipText()
    }

}

private class IsDescriptionVisibleDelegate<R : SwingComponent> : ReadWriteProperty<R, Boolean> {
    private var isDescriptionVisible: UnBool = UnBool.UNCERTAIN

    override operator fun getValue(thisRef: R, property: KProperty<*>): Boolean
            = isDescriptionVisible.collapse(thisRef.isControl)

    override operator fun setValue(thisRef: R, property: KProperty<*>, value: Boolean) {
        this.isDescriptionVisible = UnBool.of(value)
        // Update toolTipText
        thisRef.updateToolTipText()
    }
}


fun <T : SwingComponent> T.updateToolTipText() {
    this.toolTipText = if (this is XControl)
        makeControlToolTipText(this, this as XControl)
    else
        this.makeViewToolTipText()
}

// Re-creates the toolTipText for instances of XControl
// each time the keyShortcut/description is changed
private fun <T : SwingComponent, U : XControl> makeControlToolTipText(baseRef: T,
                                                                      controlRef: U): String? =
    if (baseRef.isDescriptionVisible) {
        if (controlRef.keyShortcut != null)
            baseRef.description + " (${controlRef.keyShortcut!!.stringSignature})"
        else
            baseRef.description
    }
    else
        null

// Re-creates the toolTipText for instances of XView
// each time the description is changed
private fun <T : SwingComponent> T.makeViewToolTipText(): String? =
    if (this.isDescriptionVisible)
        this.description
    else
        null