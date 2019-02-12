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
import one.atpc.fusion.assertStrictEquals
import one.atpc.fusion.ui.VectorTest.Companion.assertVectorBounds
import org.junit.Assert.assertEquals
import org.junit.Test
import java.awt.Dimension
import java.awt.geom.Dimension2D

class Dimension2Test : VectorTest {
    private val implementations: Array<Dimension2> = arrayOf(
        Dimension2.Int(10, 20),
        Dimension2.Double(10.0, 20.0)
    )

    init {
        // Make sure the test starting state is proper
        assert(implementations.size == 2)

        assert(Dimension2.Int::class   == implementations[0]::class)
        assert(Dimension2.Double::class == implementations[1]::class)
    }

    @Test
    fun testSizeGetters() {
        assertEquals(implementations[0].getWidth(), implementations[1].getWidth(), 0.0)
        assertEquals(implementations[0].getHeight(), implementations[1].getHeight(), 0.0)

        // Make sure the different type getters are equal
        for (impl in implementations) {
            assertEquals(impl.getWidth().toInt(), impl.intWidth)
            assertEquals(impl.getHeight().toInt(), impl.intHeight)
        }
    }

    @Test
    fun testEquals() {
        // Test Dimension2 equality (both ways)
        assertEquals(implementations[0], implementations[1])
        assertEquals(implementations[1], implementations[0])

        // Test Dimension2.Int -> Dimension equality
        assertEquals(Dimension2.Int(100, 100), Dimension(100, 100))

        // Test Dimension2.Double -> Dimension2D equality
        assertEquals(
            Dimension2.Double(100.0, 100.0),
            object : Dimension2D() {
                override fun getWidth(): Double = 100.0

                override fun getHeight(): Double = 100.0

                override fun setSize(width: Double, height: Double)
                        = throw UnsupportedOperationException()
            }
        )
    }

    @Test
    fun testHashCode() {
        // Dimension2 -> Dimension2 compatibility
        assertEquals(implementations[0].hashCode(), implementations[1].hashCode())

        // Dimension2.Int -> Dimension compatibility
        assertEquals(Dimension2.Int(100, 100).hashCode(), Dimension(100, 100).hashCode())

        // (Dimension2.Double testing is not necessary) //
    }

    @Test
    fun testCopy() {
        for (impl in implementations) {
            assertDeepCopy(impl)
        }
    }

    @Test
    fun testConverters() {
        assertStrictEquals(Dimension2.Double(25.0, -27.0), Dimension2.Int(25, -27).toDouble())
        assertStrictEquals(Dimension2.Int(50, -58), Dimension2.Double(50.0, -58.0).toInt())
    }

    @Test
    fun testAWTConverter() {
        assertStrictEquals(Dimension2.Int(34, 56), Dimension(34, 56).toDimension2())
    }

    @Test
    override fun testXVectorImplementation() {
        for (impl in implementations) {
            assertEquals(impl.getWidth(),  impl[0], 0.0)
            assertEquals(impl.getHeight(), impl[1], 0.0)

            assertVectorBounds(impl)
        }
    }

    @Test
    override fun testVMap() = implementations.forEach(VectorTest.Companion::testVMap)

    @Test
    override fun testVMapIndexed() = implementations.forEach(VectorTest.Companion::testVMap)

}