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

@file:JvmName("NumberUtils")

package one.atpc.fusion

import org.jetbrains.annotations.Contract
import java.math.BigDecimal

/**
 * Determines whether a number is a floating-point.
 *
 * @return `true` if the number is a floating-point number,
 *          `false` otherwise.
 * @author Thomas Orlando
 */
val Number.isFloatingPoint
    @Contract(pure = true)
    get() = this is Float || this is Double || this is BigDecimal