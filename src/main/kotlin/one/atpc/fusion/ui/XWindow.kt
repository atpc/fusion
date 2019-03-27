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

import java.awt.Frame
import java.awt.GraphicsConfiguration
import java.awt.Window
import javax.swing.JWindow

open class XWindow : JWindow, XScreenPlaceable, XContainer, XContainer.SwingContainer {

    constructor() : super()

    constructor(gc: GraphicsConfiguration) : super(gc)

    constructor(owner: Frame) : super(owner)

    constructor(owner: Window) : super(owner)

    constructor(owner: Window, gc: GraphicsConfiguration) : super(owner, gc)


    override var id: String? by XView.IdDelegate()


    override fun draw(g: XGraphics)
            = throw XException(UnsupportedOperationException("Method draw(XGraphics) is not supported in XWindow!"))


    override fun setLocationToCenter() = this.setLocationRelativeTo(null)

}