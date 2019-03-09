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

package one.atpc.fusion.ui.feature

import one.atpc.fusion.ui.*
import java.awt.Component

abstract class Feature(open val isDrawingHeavy: Boolean = false) {
    private var _connectedComponent: Component? = null
    open var connectedComponent: Component?
        get() = _connectedComponent
        set(value) {
            _connectedComponent = value
        }

    open val componentSize: Dimension2.Int get() = connectedComponent!!.size.toDimension2()
    open val componentBounds: Rectangle4.Int get() = connectedComponent!!.bounds.toRectangle4()

    open fun redrawComponent() {
        if (connectedComponent != null) {
            if (connectedComponent is XView)
                (connectedComponent as XView).redraw()
            else
                // Fail fast if there is no component connected
                connectedComponent!!.repaint()
        }
    }

    abstract fun draw(g: XGraphics)

}