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

import javax.swing.Icon

interface XContainer {

    /**
     * Adds the specified [view] to this container.
     *
     * @param view The view to add.
     * @return The added [view].
     */
    fun <T : XView> add(view: T): T


    fun text(text: String): XText = this.add(XText(text))

    fun text(icon: Icon): XText = this.add(XText(icon))

    fun text(icon: Icon, horizontalAlignment: Int) = this.add(XText(icon, horizontalAlignment))

    fun text(text: String, icon: Icon, horizontalAlignment: Int) = this.add(XText(text, icon, horizontalAlignment))

    fun text(text: String, horizontalAlignment: Int) = this.add(XText(text, horizontalAlignment))


    fun button(text: String): XButton = this.add(XButton(text))

    fun button(action: Action): XButton = this.add(XButton(action))

    fun button(icon: Icon): XButton = this.add(XButton(icon))

    fun button(text: String, icon: Icon): XButton = this.add(XButton(text, icon))

}