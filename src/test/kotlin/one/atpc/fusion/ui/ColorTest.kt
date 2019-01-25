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

import one.atpc.fusion.assertMultiEquals
import one.atpc.fusion.assertThrown
import one.atpc.fusion.assertThrownGeneric
import one.atpc.fusion.testRandom
import org.junit.Assert.*
import org.junit.Test
import kotlin.random.Random
import kotlin.random.nextUInt

class ColorTest {

    @Test
    fun testRGBConstructors() {
        val exampleColor = Color.rgb(0xabcdef)
        assertEquals(0xab, exampleColor.red)
        assertEquals(0xcd, exampleColor.green)
        assertEquals(0xef, exampleColor.blue)
        assertEquals(255,  exampleColor.alpha)

        val transparentBlackColor = Color.rgba(0xab000000u)
        assertEquals(0xab, transparentBlackColor.alpha)

        val blackColor = Color.rgb(0)
        assertNotEquals(blackColor.rgb, transparentBlackColor.rgb)  // Ensure the transparent is not equal to the opaque
        assertEquals(blackColor.rgb.toUInt(), transparentBlackColor.rgb.toUInt() or 0xFF000000_u)

        assertMultiEquals(Color.rgb(0xabcdef), Color.rgb(0xabcdefU),
                          Color.rgb(0xab, 0xcd, 0xef), Color.rgb(0xabU, 0xcdU, 0xefU))
    }

    @Test
    fun testHSVAConstructors() {
        // Ensure transparency is equal to the RGBA constructor counterparts
        assertEquals(Color.rgba(0xff000000u), Color.hsva(0f, 0f, 0f, 1f))
        assertEquals(Color.rgba(1f, 0f, 0f, 0.5f), Color.hsva(0f, 1f, 1f, 0.5f))

        val ill: Array<Float> = arrayOf(2f, 0f, 0f, 0f, 0f)
        for (i in 0 until 4) {
            // Ensure the current changed value is illegal
            assert(ill[i] > 1f)

            assertThrown<java.lang.IllegalArgumentException> { Color.rgba(ill[0], ill[1], ill[2], ill[3]) }
            // Shift the illegal input
            ill[i+1] = ill[i]
            ill[i]   = 0f
        }
    }

    @Test
    fun testDeriveAlpha() {
        val color = Color.rgb(0xaa2211)

        // Int & UInt
        assertMultiEquals(Color.rgba(0x40aa2211u), color.deriveAlpha(0x40), color.deriveAlpha(0x40u))

        // Double & Float
        val black = Color.rgb(0)
        val transBlack = Color.rgba(0.0, 0.0, 0.0, 0.3421)
        assertMultiEquals(transBlack, black.deriveAlpha(0.3421), black.deriveAlpha(0.3421f))

        // Test illegal inputs
        arrayOf<Number>(
            -.304,
            1e4,
            -.291f,
            1.0345e4f,
            -10,
            256,
            Float.NEGATIVE_INFINITY,
            Float.POSITIVE_INFINITY,
            Float.NaN,
            Double.NEGATIVE_INFINITY,
            Double.POSITIVE_INFINITY,
            Double.NaN
        ).forEach {
                it ->
            assertThrownGeneric<IllegalArgumentException, Any>("($it)::class = ${it::class.qualifiedName}",
                when(it) {
                    is Int -> {{ black.deriveAlpha(it) }}
                    is Float -> {{ black.deriveAlpha(it) }}
                    is Double -> {{ black.deriveAlpha(it) }}
                    else -> {{ fail("Unknown type of object: $it") }}
                }
            )
        }
    }

    @Test
    fun testComponentGetters() {
        val color = Color.rgba(Random.nextUInt())
        assertArrayEquals(color.getRGBComponents(null), color.getRGBComponents(), 0.0f)
        assertArrayEquals(color.getRGBColorComponents(null), color.getRGBColorComponents(), 0.0f)
    }

    @Test
    fun testToString() {
        assertEquals("one.atpc.fusion.ui.Color[r=255,g=255,b=255,a=255]", Color.rgb(0xfffffff).toString())
    }

    @Test
    fun testAWTCompatibility() {
        assertEquals(java.awt.Color(0xA2FA33), Color.rgb(0xA2FA33))
        assertEquals(java.awt.Color(0xff001122.toInt(), true), Color.rgba(0xff001122_u))

        // Biggest difference: Double constructors with HSV
        assertEquals(java.awt.Color.getHSBColor(1f, .45f, .34f), Color.hsv(1.0, .45, .34))

        // Test random numbers, additionally to the static tests
        testRandom({ Random.nextUInt() }) {
                r -> assertEquals(java.awt.Color(r.toInt(), true), Color.rgba(r))
        }
    }

}