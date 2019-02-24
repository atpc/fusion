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

package one.atpc.fusion.util

import kotlin.reflect.KClass

inline fun <R> autocatch(block: () -> R): R? {
    return try {
        block()
    }
    catch (e: Exception) {
        handleException(e)
        null
    }
}

inline fun <T : Exception, R> autocatchJust(exceptionType: KClass<T>, block: () -> R): R? {
    return try {
        block()
    }
    catch(e: Exception) {
        if (exceptionType.isInstance(e)) {
            handleException(e)
            null
        }
        else throw e
    }
}

inline fun <reified T : Exception, R> autocatchJust(block: () -> R): R? {
    return try {
        block()
    }
    // We explicitly not catch Error
    catch (e: Exception) {
        if (e is T) {
            handleException(e)
            null
        }
        else throw e
    }
}