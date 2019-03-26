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

import one.atpc.fusion.ui.event.MouseListener
import one.atpc.fusion.ui.event.MouseMotionListener
import java.awt.Cursor
import java.awt.GraphicsConfiguration
import java.awt.event.MouseEvent


/**
 * An always-on-screen, non-blocking, ownerless utility window that
 * can be displayed anywhere on the user's desktop.
 * It is invisible in the user's taskbar/dock with the default `type`
 * being [Window.Type.UTILITY].
 *
 * Per default, the user will not able to move this window around;
 * this can be changed by setting the [isMovable] property.
 *
 * @author Thomas Orlando
 */
open class XUtilityWindow : XWindow {

    /**
     * Creates a new utility window. This window will not be focusable.
     *
     * This constructor sets the component's locale property to the value
     * returned by [JComponent.getDefaultLocale].
     *
     * @throws HeadlessException If [GraphicsEnvironment.isHeadless] returns true.
     */
    constructor() : super()

    /**
     * Creates a new utility window with the specified [GraphicsConfiguration]
     * of a screen device. This window will not be focusable.
     *
     * This constructor sets the component's locale property to the value
     * returned by [JComponent.getDefaultLocale].
     *
     * @param gc The `GraphicsConfiguration` that is used to construct the new
     *           window; if `gc` is `null`, the system default
     *           `GraphicsConfiguration` is assumed.
     * @throws HeadlessException If [GraphicsEnvironment.isHeadless] returns true.
     * @throws IllegalArgumentException If [gc] is not from a screen device.
     */
    constructor(gc: GraphicsConfiguration) : super(gc)

    init {
        this.type = Type.UTILITY
    }

    // window is optionally movable (isMovable, default false, ensure default)
    private var moveController: MoveController? = null

    /**
     * Determines whether this utility window is movable or not.
     * If set to `true`, this window can be moved around by
     * being dragged with the cursor.
     */
    var isMovable: Boolean
        get() = moveController != null
        set(value) {
            // Only do something if the value is changing
            if (value != isMovable) {
                if (value) {
                    // Create the move controller
                    this.moveController = MoveController()
                    // Add the controller
                    this.addMouseListener(moveController)
                    this.addMouseMotionListener(moveController)
                }
                else {
                    // Remove the controller
                    this.removeMouseListener(this.moveController)
                    this.removeMouseMotionListener(this.moveController)
                    // Clear the move controller
                    this.moveController = null
                }
            }
        }

    // TODO Outsource moving logic later?
    private inner class MoveController : MouseListener.Adapter, MouseMotionListener.MouseDragged {
        private var oldCursor: Cursor? = null
        private var lastPointerLocation: Point2.Int? = null

        override fun mousePressed(e: MouseEvent?) {
            this.oldCursor = cursor                                 // Store the current cursor
            cursor = Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR) // Set the new cursor
        }

        override fun mouseReleased(e: MouseEvent?) {
            cursor = oldCursor          // Restore the cursor before it was replaced
            this.oldCursor = null       // Clear the old cursor

            // Clear last pointer location
            this.lastPointerLocation = null
        }

        override fun mouseDragged(e: MouseEvent?) {
            // Move position accordingly
            val currentPointerLocation = e!!.point.toPoint2()
            if (lastPointerLocation != null)
                location = location.toPoint2() + (currentPointerLocation - lastPointerLocation!!)

            lastPointerLocation = currentPointerLocation
        }

    }

}