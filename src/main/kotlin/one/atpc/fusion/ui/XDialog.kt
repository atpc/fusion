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

import java.awt.Dialog
import java.awt.Frame
import java.awt.GraphicsConfiguration
import java.awt.Window
import javax.swing.JDialog
import javax.swing.JMenuBar

@Suppress("DeprecatedCallableAddReplaceWith")
open class XDialog : JDialog, XScreenPlaceable, XContainer, XContainer.SwingContainer {

    constructor() : super()

    constructor(owner: Frame) : super(owner)

    constructor(owner: Frame, modal: Boolean) : super(owner, modal)

    constructor(owner: Frame, title: String) : super(owner, title)

    constructor(owner: Frame, title: String, modal: Boolean) : super(owner, title, modal)

    constructor(owner: Frame, title: String, modal: Boolean, gc: GraphicsConfiguration) : super(owner, title, modal, gc)

    constructor(owner: Dialog) : super(owner)

    constructor(owner: Dialog, modal: Boolean) : super(owner, modal)

    constructor(owner: Dialog, title: String) : super(owner, title)

    constructor(owner: Dialog, title: String, modal: Boolean) : super(owner, title, modal)

    constructor(owner: Dialog, title: String, modal: Boolean, gc: GraphicsConfiguration) : super(owner, title, modal, gc)

    constructor(owner: Window) : super(owner)

    constructor(owner: Window, modalityType: ModalityType) : super(owner, modalityType)

    constructor(owner: Window, title: String) : super(owner, title)

    constructor(owner: Window, title: String, modalityType: ModalityType) : super(owner, title, modalityType)

    constructor(owner: Window, title: String, modalityType: ModalityType, gc: GraphicsConfiguration) : super(owner, title, modalityType, gc)


    override var id: String? by XView.IdDelegate()


    override fun draw(g: XGraphics)
            = throw XException(UnsupportedOperationException("Method draw(XGraphics) is not supported in XDialog!"))


    override fun setLocationToCenter() = this.setLocationRelativeTo(null)


    var xMenuBar: XMenuBar
        get() = super.getJMenuBar() as XMenuBar
        set(value) = super.setJMenuBar(value)


    @Deprecated("Swing JMenuBar should not be used for XFrame.")
    override fun setJMenuBar(menubar: JMenuBar?) = super.setJMenuBar(menubar)

    @Deprecated("Swing JMenuBar should not be used for XFrame.")
    override fun getJMenuBar(): JMenuBar? = super.getJMenuBar()

}