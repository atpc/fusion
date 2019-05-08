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

open class StyleBuilder {
    private val subStyleMap: HashMap<String, SubStyle> = hashMapOf()

    operator fun get(selector: String): SubStyle? = subStyleMap[selector]

    operator fun get(selector: Selector): SubStyle? = subStyleMap[selector.value]

    operator fun set(selector: String, subStyle: SubStyle): SubStyle? = subStyleMap.put(selector, subStyle)

    operator fun set(selector: Selector, subStyle: SubStyle): SubStyle? = subStyleMap.put(selector.value, subStyle)


    fun toStyle(): Style = Style(subStyleMap.copy(), Unit)

}
