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

enum class MeasureUnit(val symbol: String) {
    NUMERIC (""),

    // ABSOLUTES
    PX ("px"),          // Pixel
    Q  ("q"),           // Quarter millimeters
    MM ("mm"),          // Millimeters
    CM ("cm"),          // Centimeters
    IN ("in"),          // Inches
    PT ("pt"),          // Points (1/72 of an inch)
    PC ("pc"),          // Picas (12 points)

    // RELATIVES
    EM  ("em"),         // 1em = font-size
    EX  ("ex"),         // Height of lower-case x
    CH  ("ch"),         // Width of number 0
    REM ("rem"),        // Root em
    VW ("vw"),          // 1% of viewport width
    VH ("vh"),          // 1% of viewport height
    PERCENT ("%"),      // Relative to whatever parent property
}
