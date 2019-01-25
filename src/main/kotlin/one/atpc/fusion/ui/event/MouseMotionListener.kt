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

@file:Suppress("FunctionName")

package one.atpc.fusion.ui.event

import one.atpc.fusion.ConstructorFunction
import java.awt.event.MouseEvent

interface MouseMotionListener : java.awt.event.MouseMotionListener {
    companion object {

        @JvmStatic
        @ConstructorFunction(MouseMoved::class)
        fun MouseMoved(onMouseMoved: (MouseEvent?) -> Unit): MouseMotionListener.MouseMoved
                = object : MouseMotionListener.MouseMoved {
            override fun mouseMoved(e: MouseEvent?)
                    = onMouseMoved(e)
        }


        @JvmStatic
        @ConstructorFunction(MouseDragged::class)
        fun MouseDragged(onMouseDragged: (MouseEvent?) -> Unit): MouseMotionListener.MouseDragged
                = object : MouseMotionListener.MouseDragged {
            override fun mouseDragged(e: MouseEvent?)
                    = onMouseDragged(e)
        }

    }

    @FunctionalInterface
    interface MouseMoved : MouseMotionListener {

        override fun mouseDragged(e: MouseEvent?) = Unit

    }

    @FunctionalInterface
    interface MouseDragged : MouseMotionListener {

        override fun mouseMoved(e: MouseEvent?) = Unit

    }

}