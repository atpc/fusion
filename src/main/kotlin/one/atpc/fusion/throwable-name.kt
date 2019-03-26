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

package one.atpc.fusion

import org.jetbrains.annotations.Contract

/**
 * The name of the `Throwable` instance's class.
 *
 * @author Thomas Orlando
 */
val <T : Throwable> T.name: String
    @Contract(pure = true)
    get() = this::class.simpleName ?: "<Anonymous Exception>"

/**
 * The qualified name of the `Throwable` instance's class.
 *
 * @author Thomas Orlando
 */
val <T : Throwable> T.qualifiedName: String
    @Contract(pure = true)
    get() = this::class.qualifiedName ?: "<Anonymous Exception>"