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

// Partial style
class PartStyle private constructor(internal val declarationMap: Map<String, Any?>, copied: Boolean)
    : Iterable<Map.Entry<String, Any?>> by declarationMap.entries {

    constructor(declarations: Map<String, Any?>) : this(declarations.toMap(), copied = true)

    init {
        if (!copied)
            throw IllegalArgumentException("Given declarations are not independent!")
    }


    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(property: String): T? = declarationMap[property] as T?

    operator fun <T> get(property: String, defValue: T): T = this[property] ?: defValue

    val size: Int = declarationMap.size


    infix fun combineWith(other: PartStyle): PartStyle {
        // The other partStyle has priority, which is why we incorporate it first
        val combinedBuilder = PartStyleBuilder(from = this)
        // Incorporate the other Style
        for (entry in other) {
            combinedBuilder[entry.key] = entry.value
        }

        return combinedBuilder.toPartStyle()
    }

}
