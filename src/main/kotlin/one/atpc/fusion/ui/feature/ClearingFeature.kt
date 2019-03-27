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
import one.atpc.fusion.ui.event.MouseListener
import one.atpc.fusion.ui.event.MouseMotionListener
import java.awt.BasicStroke
import java.awt.BasicStroke.CAP_ROUND
import java.awt.BasicStroke.JOIN_ROUND
import java.awt.Cursor
import java.awt.Polygon
import java.awt.event.MouseEvent
import java.awt.geom.Line2D
import javax.swing.text.JTextComponent
import kotlin.math.roundToInt

open class ClearingFeature : Feature<JTextComponent>(), MouseListener.Adapter, MouseMotionListener.MouseMoved {
    private var state = State.IDLE
    private var oldCursor: Cursor? = null
    // TODO Flag to make it only visible when there's input

    override fun draw(g: XGraphics) {
        val size = componentSize
        val width = size.width
        val height = size.height

        val bg = connectedComponent!!.background
        val fg = Color.rgb(connectedComponent!!.foreground.rgb)

        val rectEdge = getRectEdge(height)
        val margin = getMargin(height)

        // Fill feature bounds and margin with background color
        g.paint = bg
        // Retrieve the feature bounds
        val bounds = getFeatureBounds(width, height)
        // Extend bounds with margin
        val intMargin = margin.roundToInt()
        bounds.x -= intMargin
        bounds.width += intMargin
        // Fill the bounds
        g.fill(bounds)


        // Draw the button:

        // Create small rectangle
        val rectStart = width-margin-rectEdge
        // Depending on the current state, the alpha value will change
        g.paint = fg.deriveAlpha(state.alpha)
        g.fill(Rectangle4(rectStart, margin, rectEdge, rectEdge))

        // Create triangle
        val points = listOf(
            Point2(rectStart, margin),
            Point2(rectStart-margin, margin + rectEdge/2),
            Point2(rectStart, margin + rectEdge)
        )
        val triangle = Polygon(
            IntArray(3) { i -> points[i].getX().roundToInt() },
            IntArray(3) { i -> points[i].getY().roundToInt() },
            3
        )
        g.fill(triangle)

        // Draw cross
        val halfMargin = margin * 0.5
        g.paint = bg
        g.stroke = BasicStroke((halfMargin * 0.5).toFloat(), CAP_ROUND, JOIN_ROUND)
        val lax = rectStart+halfMargin
        val lay = margin+halfMargin
        val lbx = rectStart+rectEdge-halfMargin
        val lby = height-margin-halfMargin
        g.draw(Line2D.Double(Point2(lax, lay), Point2(lbx, lby)))
        g.draw(Line2D.Double(Point2(lax, lby), Point2(lbx, lay)))
    }


    override fun mousePressed(e: MouseEvent?) = synchronized(this) {
        if (e?.point in getFeatureBounds(componentSize))
            changeState(State.PRESSED)
    }

    override fun mouseReleased(e: MouseEvent?) = synchronized(this) {
        changeState(
            if (e?.point in getFeatureBounds(componentSize)) State.HOVERED else State.IDLE
        )
    }

    override fun mouseMoved(e: MouseEvent?) = synchronized(this) {
        when {
            e?.point in getFeatureBounds(componentSize) -> changeState(State.HOVERED)
            state == State.HOVERED -> changeState(State.IDLE)
        }
    }

    override fun mouseExited(e: MouseEvent?) = synchronized(this) {
        changeState(State.IDLE)
    }


    private fun changeState(newState: State) {
        // Allow all transitions, but only if the state has actually changed
        if (newState != state) {
            val oldState = state
            this.state = newState
            when (this.state) {
                State.IDLE -> if (oldCursor != null) {
                    connectedComponent?.cursor = oldCursor
                    // Save old cursor
                    oldCursor = null
                }
                State.HOVERED -> {
                    // Only change cursor after transition from idle states
                    if (oldState == State.IDLE) {
                        // Change cursor to default cursor
                        oldCursor = connectedComponent?.cursor
                        connectedComponent?.cursor = Cursor.getDefaultCursor()
                    }
                }
                State.PRESSED -> {
                    // Clear text field
                    connectedComponent?.text = ""
                }
            }
            // Redraw
            redrawComponent()
        }
    }


    companion object {
        private const val SCALE_FACTOR = 0.5  // Scale factor for the icon

        @JvmStatic
        private fun getRectEdge(height: Int) = height * SCALE_FACTOR

        @JvmStatic
        private fun getMargin(height: Int) = height*((1-SCALE_FACTOR)/2)


        @JvmStatic
        private fun getFeatureBounds(width: Int, height: Int): Rectangle4.Int {
            return Rectangle4.Int(width - height, 0, height, height)
        }

        @JvmStatic
        private fun getFeatureBounds(size: Dimension2.Int): Rectangle4.Int
                = getFeatureBounds(size.width, size.height)

    }

    private enum class State(val alpha: Double) {
        IDLE (0.8),
        HOVERED (0.9),
        PRESSED (1.0)
    }

}