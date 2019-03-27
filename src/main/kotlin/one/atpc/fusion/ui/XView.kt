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
import kotlin.reflect.KProperty

interface XView {

    var id: String?


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
     * @param rect The rectangular area to redraw.
     * @see draw
     */
    fun redraw(rect: Rectangle4)

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
    fun redraw(delay: Long, rect: Rectangle4)



    class IdDelegate {
        private var _id: String? = null

        operator fun getValue(thisRef: XView, property: KProperty<*>): String?
                = _id

        operator fun setValue(thisRef: XView, property: KProperty<*>, value: String?) {
            if (_id == null)
                this._id = value
            else
                throw XException(IllegalStateException("ID can only be set once!"))
        }
    }


    interface SwingView : XView {

        fun repaint()

        fun repaint(delay: Long)

        fun repaint(@Px x: Int, @Px y: Int, @Px width: Int, @Px height: Int)

        fun repaint(delay: Long, @Px x: Int, @Px y: Int, @Px width: Int, @Px height: Int)


        override fun redraw() = this.repaint()

        override fun redraw(delay: Long) = this.repaint(delay)

        override fun redraw(rect: Rectangle4) {
            val ir = rect.toInt()
            this.repaint(ir.x, ir.y, ir.width, ir.height)
        }

        override fun redraw(delay: Long, rect: Rectangle4) {
            val ir = rect.toInt()
            this.repaint(delay, ir.x, ir.y, ir.width, ir.height)
        }


        companion object {

            @JvmStatic
            inline fun <reified R> paintComponent(thisRef: R, g: Graphics?) where R : SwingComponent, R : XView {
                if (g == null)
                    throw NullPointerException("Graphics object can not be null!")
                else
                    thisRef.draw(g.toXGraphics())
            }

        }

    }

}