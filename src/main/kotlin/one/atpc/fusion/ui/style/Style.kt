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
class Style private constructor(private val partStyleMap: Map<String, PartStyle>) {

    internal constructor(partStyles: Map<String, PartStyle>, copied: Boolean)
            : this(if (copied) partStyles else partStyles.copy())

    companion object {

        fun of(partStyles: Map<Selector, PartStyle>) = Style(partStyles.let { original ->
            val copy = HashMap<String, PartStyle>(original.size)
            original.forEach { (k, v) -> copy[k.value] = v }
            copy
        })

    }

    operator fun get(selector: String): PartStyle? = partStyleMap[selector]

    operator fun get(selector: Selector): PartStyle? = partStyleMap[selector.value]


    val debugString: String get() {
        val builder = StringBuilder()

        for ((selector, partStyle) in partStyleMap) {
            builder.append("\n$selector {\n")
            for ((property, value) in partStyle) {
                builder.append("\t$property: $value;\n")
            }
            builder.append("}\n")
        }

        return builder.toString()
    }

}
