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

@file:JvmName("CollectionUtils")

package one.atpc.fusion.util

/**
 * Returns the slice (section) of the list specified by the [indices] range.
 *
 * @return A list containing elements at indices in the specified [indices] range.
 * @see List.slice
 * @author Thomas Orlando
 */
operator fun <T> List<T>.get(indices: IntRange): List<T> = this.slice(indices)


fun <T> List<T>.split(delimiter: T): List<List<T>> = when {
    this.isEmpty() -> emptyList()
    this.size == 1 -> if (this[0] == delimiter) emptyList() else listOf(listOf(this[0]))
    else -> {
        val parts: ArrayList<List<T>> = ArrayList()
        var lastDelimiterIndex = -1
        for (i in this.indices) {
            if (this[i] == delimiter) {
                parts.add(this.subList(lastDelimiterIndex+1, i))
                lastDelimiterIndex = i
            }
        }
        if (size - lastDelimiterIndex > 1)
            parts.add(this.subList(lastDelimiterIndex+1, size))

        parts
    }
}


@Suppress("UNCHECKED_CAST")
fun <K, V> HashMap<K, V>.copy(): HashMap<K, V> = this.clone() as HashMap<K, V>
