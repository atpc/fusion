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

import org.jdesktop.swingx.JXLabel
import java.awt.Graphics
import javax.swing.Icon

open class XText : JXLabel, XView, XView.SwingView {

    constructor() : super()

    constructor(icon: Icon) : super(icon)

    constructor(icon: Icon, horizontalAlignment: Int) : super(icon, horizontalAlignment)

    constructor(text: String?) : super(text)

    constructor(text: String?, icon: Icon, horizontalAlignment: Int) : super(text, icon, horizontalAlignment)

    constructor(text: String?, horizontalAlignment: Int) : super(text, horizontalAlignment)


    override var id: String? by XView.IdDelegate()


    final override fun paintComponent(g: Graphics?) = XView.SwingView.paintComponent(this, g)

    override fun draw(g: XGraphics) = super.paintComponent(g)


    // Ensure a constant height
    // TODO Make this optional (with a property)
    val fontHeight get() = this.getFontMetrics(this.font).height
    override fun getMinimumSize()   = Dimension2(super.getMinimumSize().width, fontHeight)
    override fun getPreferredSize() = Dimension2(super.getPreferredSize().width, fontHeight)
    override fun getMaximumSize()   = Dimension2(super.getMaximumSize().width, fontHeight)

}