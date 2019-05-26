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

package one.atpc.fusion.ui.style.css

import one.atpc.fusion.ui.Color

// Basic color keywords:
// https://drafts.csswg.org/css-color-3/#colorunits

// TODO Add tests?
internal val colorKeywords: Map<String, Color> = mapOf(
    // Basic color names
    "black"   to 0x000000u,
    "silver"  to 0xC0C0C0u,
    "gray"    to 0x808080u,
    "white"   to 0xFFFFFFu,
    "maroon"  to 0x800000u,
    "red"     to 0xFF0000u,
    "purple"  to 0x800080u,
    "fuchsia" to 0xFF00FFu,
    "green"   to 0x008000u,
    "lime"    to 0x00FF00u,
    "olive"   to 0x808000u,
    "yellow"  to 0xFFFF00u,
    "navy"    to 0x000080u,
    "blue"    to 0x0000FFu,
    "teal"    to 0x008080u,
    "aqua"    to 0x00FFFFu
).mapValues { Color.rgb(it.value) } + mapOf(
    // Transparent is a special color keyword
    "transparent" to Color.rgba(0, 0, 0, 0)
)


// All the other keywords
internal val otherValueKeywords: List<String> = listOf(
    "initial",
    "inherit",

    /*
     * Special color keyword, but not a color itself.
     * Is the current value of the 'color' property or,
     * if applied to 'color',
     * equal to `color: inherit`.
     */
    "currentColor",

    "none"
)
