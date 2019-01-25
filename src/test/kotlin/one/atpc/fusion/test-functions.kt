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

import org.junit.Assert.*

fun <T : Any> assertStrictEquals(expected: T, actual: T) {
    assertEquals(expected::class, actual::class)
    assertEquals(expected, actual)
}

fun <T : Copyable> assertDeepCopy(obj: T) {
    val objCopy = obj.copy()
    assertNotSame("Copy should not be the same object as source!", obj, objCopy)
    assertEquals("Copy should be equal to source!", obj, obj.copy())
}

fun <T> assertMultiEquals(vararg objects: T) {
    for (i in 0 until objects.size) {
        for (j in 0 until objects.size) {
            if (i != j) {
                assertEquals(objects[i], objects[j])
            }
        }
    }
}


fun <T> testRandom(randomSupplier: () -> T, action: (T) -> Unit) {
    for (n in 0..7)
        action(randomSupplier())
}


inline fun <reified T : Throwable> assertThrown(info: String? = null, function: () -> Unit)
        = assertThrownGeneric<T, Unit>(info, function)

// assertNotThrown is only necessary for specified exceptions
// (In the general case, a throw will just abort the test)
inline fun <reified T : Throwable, R> assertThrownGeneric(info: String? = null, function: () -> R) {
    val callResult = tryFunction(function)
    val thrown: Throwable? = callResult.first
    // Ensure the exception is not null
    assertNotNull(
        "No ${T::class.qualifiedName} was thrown! Call result: ${callResult.second}\n" +
                if (info != null) "Info:\t$info" else "",
        thrown
    )
    assertTrue(thrown is T)
}

inline fun <R> tryFunction(function: () -> R): Pair<Throwable?, R?> =
    try {
        val result = function()
        Pair(null, result)
    }
    catch (t: Throwable) {
        Pair(t, null)
    }
