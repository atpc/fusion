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

package one.atpc.fusion.ui.style

import one.atpc.fusion.ui.Color

open class SubStyleBuilder @JvmOverloads constructor(initialCapacity: Int = 0) {

    constructor(from: SubStyle) : this(from.size) {
        declarationMap.putAll(from.declarationMap)
    }


    private val declarationMap: MutableMap<String, Any?> = HashMap(initialCapacity)


    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(property: String): T? = declarationMap[property] as T?

    operator fun <T> get(property: String, defValue: T): T = this[property] ?: defValue


    operator fun set(property: String, number: MeasuredValue?) {
        declarationMap[property] = number
    }

    operator fun set(property: String, color: Color?) {
        declarationMap[property] = color
    }

    operator fun set(property: String, keyword: String?) {
        declarationMap[property] = keyword
    }


    fun toSubStyle(): SubStyle = SubStyle(declarationMap)

}