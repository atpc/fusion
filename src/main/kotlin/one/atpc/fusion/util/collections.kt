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


fun <T> List<T>.split(delimiter: T): List<List<T>> = split0(delimiter, this)

private tailrec fun <T> split0(delimiter: T, remaining: List<T>, parts: List<List<T>> = emptyList()): List<List<T>> {
    // Find first occurrence of delimiter
    val firstDelimiterIndex = remaining.indexOf(delimiter)

    return if (firstDelimiterIndex == -1)
        // Finished (no separating element => no parts left)
        parts
    else
        split0(
            delimiter = delimiter,
            remaining = remaining.subList(firstDelimiterIndex+1, remaining.size),
            parts = parts + listOf(remaining.subList(0, firstDelimiterIndex))
        )
}