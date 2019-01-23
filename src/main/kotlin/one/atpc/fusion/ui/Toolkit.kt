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

import java.awt.GraphicsEnvironment

typealias Toolkit = java.awt.Toolkit


// Code for handling different pixel densities

/**
 * The density of the screen, described by [Toolkit].
 *
 * @see Toolkit.getScreenSize
 */
val Toolkit.screenDensity: Double get() = calculateDensity(this.screenSize.toXDimension())


/**
 * The size of the screen described by [GraphicsEnvironment.getDefaultScreenDevice]
 * in a multi-screen environment.
 */
val Toolkit.multiScreenSize: Dimension2.Int get() {
    val displayMode = GraphicsEnvironment.getLocalGraphicsEnvironment().defaultScreenDevice.displayMode
    return Dimension2.Int(displayMode.width, displayMode.height)
}

/**
 * The density of the screen described by [GraphicsEnvironment.getDefaultScreenDevice]
 * in a multi-screen environment.
 *
 * @see Toolkit.multiScreenSize
 */
val Toolkit.multiScreenDensity: Double get() = calculateDensity(this.multiScreenSize)


internal fun calculateDensity(screenSize: Dimension2): Double {
    val a = screenSize.getWidth()
    val b = screenSize.getHeight()

    return if (a > b)
        a / b
    else
        b / a
}