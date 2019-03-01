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

open class FusionComboBoxModel<E> protected constructor(getter: (Int) -> E, size: Int) : AbstractListModel<E>(), MutableComboBoxModel<E>, Serializable {
    private val items: MutableList<E> = run {
        val list = ArrayList<E>(size)
        for (i in list.indices)
            list[i] = getter(i)
        list
    }
    private var selected: E? = null


    constructor(itemList: List<E>) : this(itemList::get, itemList.size)

    constructor(items: Array<E>) : this(items::get, items.size)

    

    override fun getElementAt(index: Int): E = items[index]

    fun getIndexOf(item: E): Int = items.indexOf(item)

    override fun getSize(): Int = items.size

    override fun setSelectedItem(anItem: Any?) {
        try {
            @Suppress("UNCHECKED_CAST")
            this.selected = anItem as E
        }
        catch (e: ClassCastException) {
            throw IllegalArgumentException("Item is not an instance of box model type.", e)
        }
    }

    override fun getSelectedItem(): E? = selected

    override fun addElement(item: E) {
        items.add(item)
    }

    override fun removeElementAt(index: Int) {
        items.removeAt(index)
    }

    override fun insertElementAt(item: E, index: Int) {
        items.add(index, item)
    }

    override fun removeElement(obj: Any?) {
        items.remove(obj)
    }

}