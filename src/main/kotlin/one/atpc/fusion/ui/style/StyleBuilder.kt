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

open class StyleBuilder @JvmOverloads constructor(initialCapacity: Int = 0) {
    private val partStyleMap: HashMap<String, PartStyle> = HashMap(initialCapacity)

    operator fun get(selector: String): PartStyle? = partStyleMap[selector]

    operator fun get(selector: Selector): PartStyle? = partStyleMap[selector.value]

    operator fun set(selector: String, partStyle: PartStyle): PartStyle? = partStyleMap.put(selector, partStyle)

    operator fun set(selector: Selector, partStyle: PartStyle): PartStyle? = partStyleMap.put(selector.value, partStyle)


    fun toStyle(): Style = Style(partStyleMap, copied = false)

}
