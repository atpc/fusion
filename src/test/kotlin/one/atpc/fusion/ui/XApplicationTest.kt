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

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test
import javax.swing.WindowConstants

class XApplicationTest {
    companion object {
        @JvmField
        val testApp = object : XApplication(arrayOf()) {}
    }

    @Test
    fun testIsVisibleDefault() {
        assertFalse(testApp.isVisible)
    }

    object TestRun {
        // Test function entry
        @Suppress("UnusedMainParameter")
        @JvmStatic
        fun main(args: Array<String>) {
            testApp.run()
        }
    }

    class UIConfigurationTest {

        @Test
        fun testDefaults() {
            // Ensure certain defaults
            val default = XApplication.UIConfiguration()
            assertEquals(WindowConstants.EXIT_ON_CLOSE, default.defaultCloseOperation)
            assertEquals(true, default.rememberBounds)
            assertEquals(XApplication.UIConfiguration.DEFAULT_LAF, default.lookAndFeel)
        }

    }

}