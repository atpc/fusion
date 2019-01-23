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

import java.awt.Component
import java.awt.Container
import java.awt.Dimension
import java.awt.LayoutManager2

open class FrameLayout : LayoutManager2 {
    private var component: Component? = null

    override fun addLayoutComponent(comp: Component?, constraints: Any?) {
        this.component = comp
    }

    override fun addLayoutComponent(name: String?, comp: Component?) {
        this.component = comp
    }

    override fun removeLayoutComponent(comp: Component?) {
        // Remove if matches
        if (component == comp)
            this.component = null
    }

    override fun minimumLayoutSize(parent: Container?): Dimension
            = component?.minimumSize ?: Dimension2.Int()

    override fun maximumLayoutSize(target: Container?): Dimension
            = component?.maximumSize ?: Dimension2.Int()

    override fun preferredLayoutSize(parent: Container?): Dimension
            = component?.preferredSize ?: Dimension2.Int()

    // Ignore, as we do not cache any information
    override fun invalidateLayout(target: Container?) {}

    override fun layoutContainer(target: Container?) {
        if (target == null)
            throw NullPointerException("Container can not be null!")

        synchronized (target.treeLock) {
            val insets = target.insets
            val top    = insets.top
            val bottom = target.height - insets.bottom
            val left   = insets.left
            val right  = target.width - insets.right

            component?.setBounds(left, top, right - left, bottom - top);
        }
    }

    override fun getLayoutAlignmentX(target: Container?): Float
            = 0.5f

    override fun getLayoutAlignmentY(target: Container?): Float
            = 0.5f

}