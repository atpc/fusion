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

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class SystemInfoTest {

    @Test
    fun testOSType() {
        assertNotNull(SystemInfo.osType)
    }

    @Test
    fun testSystemPropertyConstants() {
        val sysInfoEq = { a: String, b: String -> assertEquals(System.getProperty(a), SystemInfo[b]) }
        sysInfoEq("java.version",        SystemInfo.JAVA_VERSION)
        sysInfoEq("java.vendor",         SystemInfo.JAVA_VENDOR)
        sysInfoEq("os.version",          SystemInfo.OS_VERSION)
        sysInfoEq("os.arch",             SystemInfo.OS_ARCH)
        sysInfoEq("sun.arch.data.model", SystemInfo.ARCH_DATA_MODEL)
    }

}