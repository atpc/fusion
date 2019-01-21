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

import javax.swing.JComponent
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

// Base for all Fusion components
typealias XBase = JComponent

/**
 * The view description.
 * If the view is a control (subtype of [XControl]), the description should be _imperative_,
 * otherwise it should be _describing_. Take, for example, an instance of [XText]:<br>
 * It's description of the text should only further describe it's **content**, or the gist of it.
 * An instance of [XButton], in contrast, is carrying out an action, so it's description
 * should describe it's **action** when activated by the user,
 * in the sense of _"If you click this button, it will <Your description>"_.
 * <br>
 * The description should not end with a dot.
 */
var <T : XBase> T.description: String? by DescriptionDelegate()


/**
 * Determines whether the [description] is actively shown to the user.
 * This means — in most cases — that the user will see a tooltip popping up
 * if the cursor highlights the component for a while.
 * If this property is `false`, the description will not be visible.
 */
var <T : XBase> T.isDescriptionVisible: Boolean by IsDescriptionVisibleDelegate()


private class DescriptionDelegate<R : XBase> : ReadWriteProperty<R, String?> {
    private var description: String? = null

    override operator fun getValue(thisRef: R, property: KProperty<*>): String?
            = this.description

    override operator fun setValue(thisRef: R, property: KProperty<*>, value: String?) {
        this.description = value
        // Update toolTipText
        thisRef.updateToolTipText()
    }

}

private class IsDescriptionVisibleDelegate<R : XBase> : ReadWriteProperty<R, Boolean> {
    private var isDescriptionVisible: Boolean = false

    override operator fun getValue(thisRef: R, property: KProperty<*>): Boolean = isDescriptionVisible

    override operator fun setValue(thisRef: R, property: KProperty<*>, value: Boolean) {
        this.isDescriptionVisible = value
        // Update toolTipText
        thisRef.updateToolTipText()
    }

}


private fun <T : XBase> T.updateToolTipText() {
    // if (this is XControl)
    //    this.updateControlToolTipText()
    // else
        this.updateViewToolTipText()
}

// Method for re-creating the toolTipText each time the keyShortcut/description is changed
/*private fun <T : XBase> T.updateControlToolTipText() {
    this.toolTipText =
            if (this.isDescriptionVisible) {
                if (this.keyShortcut != null)
                    this.description + " (${this.keyShortcut!!.stringSignature})"
                else
                    this.description
            }
            else
                null
}*/

private fun <T : XBase> T.updateViewToolTipText() {
    this.toolTipText =
            if (this.isDescriptionVisible)
                this.description
            else
                null
}