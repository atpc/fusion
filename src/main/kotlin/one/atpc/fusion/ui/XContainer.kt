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

import java.awt.Component
import javax.swing.Icon

interface XContainer {

    /**
     * Adds the specified [view] to this container.
     *
     * @param view The view to add.
     * @return The added [view].
     */
    fun <T : XView> add(view: T): T


    fun text(text: String): XText = this.add(XText(text))

    fun text(icon: Icon): XText = this.add(XText(icon))

    fun text(icon: Icon, horizontalAlignment: Int) = this.add(XText(icon, horizontalAlignment))

    fun text(text: String, icon: Icon, horizontalAlignment: Int) = this.add(XText(text, icon, horizontalAlignment))

    fun text(text: String, horizontalAlignment: Int) = this.add(XText(text, horizontalAlignment))


    fun button(text: String): XButton = this.add(XButton(text))

    fun button(action: Action): XButton = this.add(XButton(action))

    fun button(icon: Icon): XButton = this.add(XButton(icon))

    fun button(text: String, icon: Icon): XButton = this.add(XButton(text, icon))


    fun checkBox(icon: Icon): XCheckBox = this.add(XCheckBox(icon))

    fun checkBox(icon: Icon, selected: Boolean): XCheckBox = this.add(XCheckBox(icon, selected))

    fun checkBox(action: Action): XCheckBox = this.add(XCheckBox(action))

    fun checkBox(text: String): XCheckBox = this.add(XCheckBox(text))

    fun checkBox(text: String, selected: Boolean): XCheckBox = this.add(XCheckBox(text, selected))

    fun checkBox(text: String, icon: Icon): XCheckBox = this.add(XCheckBox(text, icon))

    fun checkBox(text: String, icon: Icon, selected: Boolean): XCheckBox = this.add(XCheckBox(text, icon, selected))


    fun textField(): XTextField = this.add(XTextField())

    fun textField(promptText: String) = this.add(XTextField(promptText))

    fun textField(promptText: String, foreground: Color) = this.add(XTextField(promptText, foreground))

    fun textField(promptText: String, foreground: Color, background: Color) = this.add(
        XTextField(promptText, foreground, background)
    )

    fun textField(columns: Int) = this.add(XTextField(columns))
    
    fun textField(columns: Int, promptText: String) = this.add(XTextField(columns, promptText))


    fun panel(): XPanel = this.add(XPanel())


    interface SwingImpl : XContainer, XView.SwingImpl {

        // TODO The SwingImpl could also provide construction methods for not yet implemented components
        // (Only exist as typealias)


        fun add(comp: Component): Component

        @Suppress("UNCHECKED_CAST")
        override fun <T : XView> add(view: T): T {
            try {
                return this.add(view as Component) as T
            }
            catch (e: ClassCastException) {
                throw XException("Could not add view: Not a subtype of Component!", e)
            }
        }

    }

}