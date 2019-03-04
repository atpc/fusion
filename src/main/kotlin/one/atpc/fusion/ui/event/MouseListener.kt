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

package one.atpc.fusion.ui.event

import java.awt.event.MouseEvent

interface MouseListener : java.awt.event.MouseListener {

    abstract class Adapter : MouseListener {

        override fun mouseEntered(e: MouseEvent?) = Unit

        override fun mouseExited(e: MouseEvent?) = Unit

        override fun mousePressed(e: MouseEvent?) = Unit

        override fun mouseReleased(e: MouseEvent?) = Unit

        override fun mouseClicked(e: MouseEvent?) = Unit

    }

    interface LightAdapter : MouseListener {

        override fun mouseEntered(e: MouseEvent?) = Unit

        override fun mouseExited(e: MouseEvent?) = Unit

        override fun mousePressed(e: MouseEvent?) = Unit

        override fun mouseReleased(e: MouseEvent?) = Unit

        override fun mouseClicked(e: MouseEvent?) = Unit

    }


    class MouseEntered(private val onMouseEntered: (MouseEvent?) -> Unit) : Adapter() {

        override fun mouseEntered(e: MouseEvent?) = onMouseEntered(e)

    }

    class MouseExited(private val onMouseExited: (MouseEvent?) -> Unit) : Adapter() {

        override fun mouseExited(e: MouseEvent?) = onMouseExited(e)

    }

    class MousePressed(private val onMousePressed: (MouseEvent?) -> Unit) : Adapter() {

        override fun mousePressed(e: MouseEvent?) = onMousePressed(e)

    }

    class MouseReleased(private val onMouseReleased: (MouseEvent?) -> Unit) : Adapter() {

        override fun mouseReleased(e: MouseEvent?) = onMouseReleased(e)

    }

    class MouseClicked(private val onMouseClicked: (MouseEvent?) -> Unit) : Adapter() {

        override fun mouseClicked(e: MouseEvent?) = onMouseClicked(e)

    }

}