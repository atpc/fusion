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
import javax.swing.JCheckBox

open class XCheckBox : JCheckBox, XControl, XView.SwingImpl {

    constructor() : super()

    constructor(icon: Icon) : super(icon)

    constructor(icon: Icon, selected: Boolean) : super(icon, selected)

    constructor(text: String) : super(text)

    constructor(action: Action) : super(action)

    constructor(text: String, selected: Boolean) : super(text, selected)

    constructor(text: String, icon: Icon) : super(text, icon)

    constructor(text: String, icon: Icon, selected: Boolean) : super(text, icon, selected)


    override var id: String? by XView.IdDelegate()


    // No keyShortcut (Unnecessary).


    final override fun paintComponent(g: Graphics?) = XView.SwingImpl.paintComponent(this, g)

    override fun draw(g: XGraphics) = super.paintComponent(g)

}