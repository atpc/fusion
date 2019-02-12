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

import one.atpc.fusion.assertDeepCopy
import one.atpc.fusion.assertMultiEquals
import one.atpc.fusion.assertStrictEquals
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.awt.Point
import java.awt.geom.Point2D
import kotlin.math.max

class Point2Test : VectorTest {
    private val implementations: Array<Point2> = arrayOf(
        Point2.Int(10, 20),
        Point2.Double(10.0, 20.0)
    )

    init {
        // Make sure the starting conditions are proper
        assert(implementations.size == 2)

        assert(Point2.Int::class    == implementations[0]::class)
        assert(Point2.Double::class == implementations[1]::class)

        // Make sure ascend works as expected
        assert(0 ascend 0 == 0)
        assert(0 ascend 1 == 1)
        assert(0 ascend 2 == 2)

        assert(1 ascend 0 == 1)
        assert(1 ascend 1 == 1)
        assert(1 ascend 2 == 2)

        assert(2 ascend 0 == 2)
        assert(2 ascend 1 == 2)
        assert(2 ascend 2 == 2)
    }

    @Test
    fun testLocationGetters() {
        assertEquals(implementations[0].getX(),
                     implementations[1].getX(),
                     0.0)
        assertEquals(implementations[0].getY(),
                     implementations[1].getY(),
                     0.0)

        for (impl in implementations) {
            assertEquals(impl.getX().toInt(), impl.intX)
            assertEquals(impl.getY().toInt(), impl.intY)
        }
    }

    @Test
    fun testEqualsAndHashCode() {
        // Functions for testing both equality and hash code
        val asEq2 = { a: Any, b: Any -> assertEquals(a, b); assertEquals(a.hashCode(), b.hashCode()) }

        // Test equality between different implementations
        asEq2(implementations[0], implementations[1])

        // Test Point2.Int -> Point compatibility
        asEq2(Point2.Int(40, 40), Point(40, 40))
        // Test Point2.Double -> Point2D.Double compatibility
        asEq2(Point2.Double(20.0, 20.0), Point2D.Double(20.0, 20.0))
    }

    // Test the results of math operations
    @Test
    fun testMathOperations() {
        for (impl in implementations) {
            assertEquals(
                Point2.Double(impl.getX() + impl.getX(), impl.getY() + impl.getY()),
                impl + impl
            )
            assertEquals(
                Point2.Double(impl.getX() - impl.getX(), impl.getY() - impl.getY()),
                impl - impl
            )
        }
    }


    @Test
    fun testMathOperationTypePrecedence() {
        // Test type precedence
        val value: Number = 10
        val types = arrayOf(
            Point2.Int::class,      // Int
            Point2.Double::class    // Double
        )
        val instances: Array<Point2> = arrayOf(
            Point2.Int(value.toInt(), value.toInt()),           // Int
            Point2.Double(value.toDouble(), value.toDouble())   // Double
        )

        // Ensure the sizes are right (matching)
        assert(types.size == 2)
        assert(types.size == instances.size)
        // Ensure the types are right
        for (i in 0 until types.size)
            assert(types[i] == instances[i]::class)

        for (i in 0 until types.size) {
            for (j in 0 until types.size) {
                // Int, Float, Double
                assertEquals(types[j ascend i], (instances[i] + instances[j])::class)    // Plus
                assertEquals(types[j ascend i], (instances[i] - instances[j])::class)    // Minus
            }
        }
    }

    @Test
    fun testConstructorFunctions() {
        // Make sure the empty constructor is truly (0|0)
        assertEquals(Point2(0, 0), Point2())

        // Ensure the constructors are correct
        assertThreeEquals(sameClass(Point2.Int(456, 458),      Point2(456, 458)),   Point(456, 458))
        assertThreeEquals(sameClass(Point2.Double(34.0, 35.0), Point2(34.0, 35.0)), Point2D.Double(34.0, 35.0))
    }

    @Test
    fun testConverters() {
        assertStrictEquals(Point2.Int(12, -24), Point2.Double(12.0, -24.0).toInt())
        assertStrictEquals(Point2.Double(457.0, 358.0), Point2.Int(457, 358).toDouble())

        // Ensure the converter result from the same class is a copy
        // [(a: Point2.Int) a.toInt() !== a]
        val intPoint = Point2.Int(23, 32)
        val doublePoint = Point2.Double(67.0, 76.0)

        assertTrue(intPoint !== intPoint.toInt())
        assertTrue(doublePoint !== doublePoint.toDouble())
    }

    @Test
    fun testAWTConverter() {
        assertStrictEquals(Point2.Int(10, 20), Point(10, 20).toPoint2())
    }

    @Test
    fun testCopy() {
        for (impl in implementations) {
            assertDeepCopy(impl)
        }
    }

    @Test
    override fun testXVectorImplementation() {
        for (impl in implementations) {
            assertEquals(impl.getX(), impl[0], 0.0)
            assertEquals(impl.getY(), impl[1], 0.0)

            VectorTest.assertVectorBounds(impl)
        }
    }

    @Test
    override fun testMap() = implementations.forEach(VectorTest.Companion::testMap)

}

private fun <T> assertThreeEquals(ab: Pair<T, T>, c: T)
        = assertMultiEquals(ab.first, ab.second, c)


private fun <T : Any> sameClass(expected: T, actual: T): Pair<T, T> {
    assertEquals(expected::class, actual::class)
    return Pair(expected, actual)
}


private infix fun Int.ascend(b: Int): Int {
    return when (this) {
        0 -> b
        else -> max(this, b)
    }
}