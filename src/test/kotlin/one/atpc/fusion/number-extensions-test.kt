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

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.math.BigDecimal
import java.math.BigInteger

class NumberExtensionsTest {

    @Test
    fun testIsFloatingPoint() {
        // Ensure floating point numbers return true
        arrayOf<Number>(
            2.718281828f,
            3.141592654,
            BigDecimal("10e45")
        ).forEach {
            fpNumber -> assertTrue(fpNumber.isFloatingPoint)
        }

        // Ensure non-floating points return false
        arrayOf<Number>(
            12.toByte(),
            13.toShort(),
            14,
            15L,
            BigInteger("16000000000000000000")
        ).forEach {
            number: Number -> assertFalse(number.isFloatingPoint)
        }
    }

}