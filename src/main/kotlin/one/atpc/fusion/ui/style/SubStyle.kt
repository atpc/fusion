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

class SubStyle private constructor(internal val declarationMap: Map<String, Any?>, copied: Boolean)
    : Iterable<Map.Entry<String, Any?>> by declarationMap.entries {

    constructor(declarations: Map<String, Any?>) : this(declarations.toMap(), copied = true)

    init {
        if (!copied)
            throw IllegalArgumentException("Given declarations are not independent!")
    }


    operator fun get(property: String): Any? = declarationMap[property]

    operator fun get(property: String, defValue: Any): Any = this[property] ?: defValue

    val size: Int = declarationMap.size


    infix fun combineWith(other: SubStyle): SubStyle {
        // The other subStyle has priority, which is why we incorporate it first
        val combinedBuilder = SubStyleBuilder(from = this)
        // Incorporate the other Style
        for (entry in other) {
            combinedBuilder[entry.key] = entry.value
        }

        return combinedBuilder.toSubStyle()
    }

}
