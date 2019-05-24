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

import one.atpc.fusion.util.copy

// A Style is comprised of partial styles
class Style private constructor(private val subStyleMap: Map<String, SubStyle>) {

    internal constructor(subStyles: Map<String, SubStyle>, copied: Boolean)
            : this(if (copied) subStyles else subStyles.copy())

    companion object {

        fun of(subStyles: Map<Selector, SubStyle>) = Style(subStyles.let { original ->
            val copy = HashMap<String, SubStyle>(original.size)
            original.forEach { (k, v) -> copy[k.value] = v }
            copy
        })

    }

    operator fun get(selector: String): SubStyle? = subStyleMap[selector]

    operator fun get(selector: Selector): SubStyle? = subStyleMap[selector.value]

}
