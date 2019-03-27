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

import java.awt.GraphicsConfiguration
import java.awt.MenuBar
import javax.swing.JFrame
import javax.swing.JMenuBar

@Suppress("DeprecatedCallableAddReplaceWith")
open class XFrame : JFrame, XScreenPlaceable, XContainer, XContainer.SwingContainer {

    constructor() : super()

    constructor(title: String) : super(title)

    constructor(gc: GraphicsConfiguration) : super(gc)

    constructor(title: String, gc: GraphicsConfiguration) : super(title, gc)


    override var id: String? by XView.IdDelegate()


    override fun draw(g: XGraphics)
            = throw XException(UnsupportedOperationException("Method draw(XGraphics) is not supported in XFrame!"))


    override fun setLocationToCenter() = this.setLocationRelativeTo(null)


    var xMenuBar: XMenuBar
        get() = super.getJMenuBar() as XMenuBar
        set(value) = super.setJMenuBar(value)


    @Deprecated("AWT MenuBar should not be used for XFrame.")
    override fun setMenuBar(mb: MenuBar?): Unit = super.setMenuBar(mb)

    @Deprecated("AWT MenuBar should not be used for XFrame.")
    override fun getMenuBar(): MenuBar? = super.getMenuBar()

    @Deprecated("Swing JMenuBar should not be used for XFrame.")
    override fun setJMenuBar(menubar: JMenuBar?) = super.setJMenuBar(menubar)

    @Deprecated("Swing JMenuBar should not be used for XFrame.")
    override fun getJMenuBar(): JMenuBar? = super.getJMenuBar()

}