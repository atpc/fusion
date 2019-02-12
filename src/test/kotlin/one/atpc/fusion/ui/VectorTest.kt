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

package one.atpc.fusion.ui

import one.atpc.fusion.assertThrown
import org.junit.Assert.assertEquals
import org.junit.Test

interface VectorTest {
    companion object {

        @JvmStatic
        fun assertVectorBounds(vector: Vector) {
            // Make sure everything withing the vector bounds goes without complications
            for (i in 0 until vector.size) {
                vector[i]
            }

            // Ensure an IndexOutOfBoundsException is thrown
            // if an element outside the vector's bounds is accessed
            assertThrown<IndexOutOfBoundsException> { vector[vector.size] }
        }

        @JvmStatic
        fun testVMap(vector: Vector) {
            val f = { x: Double -> x*x }

            val result = vector.vmap(f)

            vector.forEachIndexed { index, elem ->
                assertEquals(f(elem), result[index], 0.0)
            }
        }

        @JvmStatic
        fun testVMapIndexed(vector: Vector) {
            val g = { n: Int, x: Double -> n*x + x - n }

            val result = vector.vmapIndexed(g)

            vector.forEachIndexed { index, elem ->
                assertEquals(g(index, elem), result[index], 0.0)
            }
        }

    }

    @Test
    fun testXVectorImplementation()

    @Test
    fun testVMap()

    @Test
    fun testVMapIndexed()

}