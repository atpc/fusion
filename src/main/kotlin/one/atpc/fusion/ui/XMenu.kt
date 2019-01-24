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
import javax.swing.Icon
import javax.swing.JMenu

open class XMenu : JMenu, XView, XView.SwingImpl {

    constructor() : super()

    constructor(text: String) : super(text)

    constructor(action: Action) : super(action)

    constructor(text: String, tearOff: Boolean) : super(text, tearOff)


    fun menu(text: String): XMenu = this.add(XMenu(text)) as XMenu

    fun menu(action: Action): XMenu = this.add(XMenu(action)) as XMenu


    fun item(text: String): XMenuItem = this.add(XMenuItem(text)) as XMenuItem

    fun item(icon: Icon): XMenuItem = this.add(XMenuItem(icon)) as XMenuItem

    fun item(action: Action): XMenuItem = this.add(XMenuItem(action)) as XMenuItem

    fun item(text: String, icon: Icon): XMenuItem = this.add(XMenuItem(text, icon)) as XMenuItem

    fun item(text: String, mnemonic: Int): XMenuItem = this.add(XMenuItem(text, mnemonic)) as XMenuItem


    final override fun paintComponent(g: Graphics?) = XView.SwingImpl.paintComponent(this, g)

    override fun draw(g: XGraphics) = super.paintComponent(g)

}