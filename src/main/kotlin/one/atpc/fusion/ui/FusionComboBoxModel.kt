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

import java.io.Serializable
import javax.swing.AbstractListModel
import javax.swing.MutableComboBoxModel

// Newer version of DefaultComboBoxModel // TODO @see
// TODO Test event notifications
open class FusionComboBoxModel<E> protected constructor(getter: (Int) -> E, size: Int) : AbstractListModel<E>(), MutableComboBoxModel<E>, Serializable {
    private val items: MutableList<E> = run {
        val list = ArrayList<E>(size)
        for (i in 0 until size)
            list[i] = getter(i)
        list
    }
    // Select the first element, if there is one
    private var selected: E? = if (size > 0) items[0] else null


    constructor(itemList: List<E>) : this(itemList::get, itemList.size)

    constructor(items: Array<E>) : this(items::get, items.size)



    override fun getElementAt(index: Int): E = items[index]

    open fun getIndexOf(item: E): Int = items.indexOf(item)

    override fun getSize(): Int = items.size

    override fun getSelectedItem(): E? = selected


    // Methods that alter the model:

    override fun setSelectedItem(anItem: Any?) {
        try {
            // TODO Maybe check if the item is part of items
            @Suppress("UNCHECKED_CAST")
            this.selected = anItem as E

            // Notify of the changed selected item
            fireContentsChanged(this, -1, -1)
        }
        catch (e: ClassCastException) {
            throw IllegalArgumentException("Item is not an instance of box model type.", e)
        }
    }

    override fun addElement(item: E) {
        items.add(item)

        // Notify of the change
        fireIntervalAdded(this, items.size - 1, items.size - 1)

        // Set the added item as the new selected item, if the model was previously empty
        if (items.size == 1 && selected == null && item != null)
            this.selectedItem = item
    }

    override fun insertElementAt(item: E, index: Int) {
        items.add(index, item)

        fireIntervalAdded(this, index, index)
    }

    override fun removeElementAt(index: Int) {
        // If the element to be removed is selected, change the selected item
        if (getElementAt(index) == selectedItem) {
            selectedItem = if (index == 0)
                if (this.size == 1) null else getElementAt(index + 1)
            else
                getElementAt(index - 1)
        }

        // Remove item at index
        items.removeAt(index)

        // Notify of the change
        fireIntervalRemoved(this, index, index)
    }

    override fun removeElement(obj: Any?) {
        val index = try {
            @Suppress("UNCHECKED_CAST")
            getIndexOf(obj as E)
        }
        catch (e: ClassCastException) {
            -1  // The object can not be contained in the combo box
        }

        // If the object is in the combo box, remove it
        if (index != -1) {
            // The already existing remove method is used here,
            // so the removed notification firing isn't implemented twice,
            // since the index has to be retrieved to fire the intervalRemoved notification
            // anyway
            this.removeElementAt(index)
        }
    }

}