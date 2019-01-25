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

import org.junit.Assert.assertNotNull
import org.junit.Test
import org.reflections.Reflections
import org.reflections.scanners.SubTypesScanner

// Meta-verifies the tests
class TestTest {

    @Test
    fun testTest() {
        val rootPackageName = this::class.java.`package`.name
        val reflections = Reflections(rootPackageName, SubTypesScanner(false))
        val allTypes  = reflections.getSubTypesOf(Any::class.java)

        assert(!allTypes.isEmpty())

        // Go through all classes which end in 'Test'...
        for (type in allTypes) {
            if (type.name.endsWith("Test") && !type.isInterface) {
                for (method in type.methods) {
                    // ...and ensure all functions starting with 'test' are annotated as '@Test'
                    if (method.name.startsWith("test")) {
                        assertNotNull(
                            "Method '${method.name}' in test class '${type.canonicalName}' is missing @Test annotation!",
                            method.declaredAnnotations.singleOrNull {
                                it -> it is org.junit.Test
                            }
                        )
                    }
                }
            }
        }
    }

}