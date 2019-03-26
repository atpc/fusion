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
import javax.swing.ComboBoxModel
import javax.swing.JComboBox

// Does not extend JXComboBox (for various reasons)
open class XComboBox<E> : JComboBox<E>, XControl, XView.SwingImpl {

    constructor(model: ComboBoxModel<E>) : super(model)

    constructor(items: Array<E>) : this(FusionComboBoxModel(items))

    constructor(itemList: List<E>) : this(FusionComboBoxModel(itemList))

    constructor() : this(FusionComboBoxModel())


    /**
     * Returns the current selected item.
     * <p>
     * If the combo box is editable, then this value may not have been added
     * to the combo box with [addItem], [insertItemAt] or the data constructors.
     *
     * @return The current selected item,
     *         or `null` if nothing is selected or the combo box is empty.
     * @see setSelectedItem
     */
    @Suppress("UNCHECKED_CAST")
    override fun getSelectedItem(): E?
            = super.getSelectedItem() as E?     // E? is nullable, because nothing could have been selected,
                                                // or the ComboBox is empty


    // No keyShortcut (Unnecessary, since this is an input component).


    final override fun paintComponent(g: Graphics?) = XView.SwingImpl.paintComponent(this, g)

    override fun draw(g: XGraphics) = super.paintComponent(g)

}
