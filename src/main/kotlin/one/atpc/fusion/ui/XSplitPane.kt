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

import org.intellij.lang.annotations.MagicConstant
import java.awt.Component
import java.awt.Graphics
import javax.swing.JSplitPane

open class XSplitPane : JSplitPane, XView, XView.SwingImpl {

    constructor() : super()

    constructor(@MagicConstant(intValues = [HORIZONTAL_SPLIT.toLong(), VERTICAL_SPLIT.toLong()]) newOrientation: Int)
            : super(newOrientation)

    constructor(@MagicConstant(intValues = [HORIZONTAL_SPLIT.toLong(), VERTICAL_SPLIT.toLong()]) newOrientation: Int,
                newContinuousLayout: Boolean) : super(newOrientation, newContinuousLayout)

    constructor(@MagicConstant(intValues = [HORIZONTAL_SPLIT.toLong(), VERTICAL_SPLIT.toLong()]) newOrientation: Int,
                newLeftComponent: Component,
                newRightComponent: Component)
            : super(newOrientation, newLeftComponent, newRightComponent)

    constructor(@MagicConstant(intValues = [HORIZONTAL_SPLIT.toLong(), VERTICAL_SPLIT.toLong()]) newOrientation: Int,
                newContinuousLayout: Boolean,
                newLeftComponent: Component,
                newRightComponent: Component)
            : super(newOrientation, newContinuousLayout, newLeftComponent, newRightComponent)


    override var id: String? by XView.IdDelegate()


    override fun draw(g: XGraphics) = super.paintComponent(g)

    final override fun paintComponent(g: Graphics?) = XView.SwingImpl.paintComponent(this, g)


    companion object {
        const val HORIZONTAL_SPLIT = JSplitPane.HORIZONTAL_SPLIT
        const val VERTICAL_SPLIT = JSplitPane.VERTICAL_SPLIT
    }

}