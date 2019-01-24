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
import java.awt.HeadlessException

/**
 * Tests whether or not a display, keyboard, and mouse can be
 * supported in this environment.  If this method returns `true`,
 * the environment is a non-graphical environment
 * (e.g. a plain terminal). This also means that a [HeadlessException]
 * is thrown from areas of the [Toolkit] and [GraphicsEnvironment]
 * that are dependent on a display, keyboard, or mouse.
 *
 * @return `true` if this environment cannot support
 * a display, keyboard, and mouse; `false`
 * otherwise.
 * @see GraphicsEnvironment.isHeadless
 * @author Thomas Orlando
 */
// Improved isHeadless() function
fun isHeadless(): Boolean {
    if (GraphicsEnvironment.isHeadless()) {
        return true
    }
    return try {
        val screenDevices = GraphicsEnvironment.getLocalGraphicsEnvironment().screenDevices
        screenDevices == null || screenDevices.isEmpty()
    } catch (e: HeadlessException) {
        e.printStackTrace()
        true
    }

}