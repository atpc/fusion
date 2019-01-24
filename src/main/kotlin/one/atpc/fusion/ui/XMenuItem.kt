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

import java.awt.Graphics
import javax.swing.Icon
import javax.swing.JMenuItem

open class XMenuItem : JMenuItem, XControl, XView.SwingImpl {

    constructor() : super()

    constructor(icon: Icon) : super(icon)

    constructor(text: String) : super(text)

    constructor(action: Action) : super(action)

    constructor(text: String, icon: Icon) : super(text, icon)

    constructor(text: String, mnemonic: Int) : super(text, mnemonic)


    /**
     * The keyboard shortcut (key combination) for this control.
     * Note that when the keyboard shortcut is typed,
     * it will work whether or not the menu is currently displayed.
     */
    override var keyShortcut: KeyStroke?
        get()      = super.getAccelerator()
        set(value) {
            this.accelerator = value
            this.updateToolTipText()
        }


    final override fun paintComponent(g: Graphics?) = XView.SwingImpl.paintComponent(this, g)

    override fun draw(g: XGraphics) = super.paintComponent(g)


    @Deprecated("Use setMnemonic(Int).", ReplaceWith("this.setMnemonic(keyCode)"))
    override fun setMnemonic(mnemonic: Char) {
        super.setMnemonic(mnemonic)
    }

}