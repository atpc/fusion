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

interface XView {

    /**
     * Draws this view.
     *
     * @param g The [XGraphics] object to draw this view.
     */
    fun draw(g: XGraphics)


    /**
     * Redraws this view.
     *
     * @see draw
     */
    fun redraw()

    /**
     * Redraws the specified rectangle of this view.
     *
     * @param rect The rectangle to redraw.
     * @see draw
     */
    fun redraw(rect: XRectangle)

    /**
     * Redraws this view after the specified delay.
     *
     * @param delay Maximum time in milliseconds before redraw.
     * @see draw
     */
    fun redraw(delay: Long)

    /**
     * Redraws the specified rectangle of this view after the specified delay.
     *
     * @param delay Maximum time in milliseconds before redraw.
     * @param rect The rectangle to redraw.
     * @see draw
     */
    fun redraw(delay: Long, rect: XRectangle)

}