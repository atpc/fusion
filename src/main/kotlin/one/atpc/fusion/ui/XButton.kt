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

import org.jdesktop.swingx.JXButton
import java.awt.Graphics
import javax.swing.Action
import javax.swing.Icon

open class XButton : JXButton, XControl, XView.SwingImpl {

    constructor() : super()

    constructor(text: String) : super(text)

    constructor(action: Action) : super(action)

    constructor(icon: Icon) : super(icon)

    constructor(text: String, icon: Icon) : super(text, icon)


    override var keyShortcut: KeyStroke?
        get() = this.action[Action.ACCELERATOR_KEY]
        set(value) {
            // Call the super method
            super.keyShortcut = value
        }

    override fun setAction(a: Action?) {
        super.setAction(a)
        if (a != null) {
            // Set the description
            this.description = a[Action.SHORT_DESCRIPTION]
        }
        // Since properties changed => Update toolTipText
        this.updateToolTipText()
    }


    final override fun paintComponent(g: Graphics?) = XView.SwingImpl.paintComponent(this, g)

    override fun draw(g: XGraphics) = super.paintComponent(g)

}