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

import org.junit.Test

class ResourcesTest {

    @Test
    fun testConstructor() {
        // Ensure nothing is thrown with those constructors
        Resources("/one/atpc/fusion")
        Resources("/one/atpc/fusion/")
        Resources(Resources::class.java.`package`)

        // Make sure an empty resource root throws an exception
        assertThrown<IllegalArgumentException> { Resources("") }

        assertThrown<IllegalArgumentException> { Resources("one/atpc/fusion") }

        // Ensure backspaces aren't allowed
        assertThrown<IllegalArgumentException> { Resources("one\\atpc\\malluma") }
    }

}