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

import org.junit.Assert.assertTrue
import org.junit.Test
import javax.swing.JComponent
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.memberFunctions

interface XViewTest {

    companion object {

        @JvmStatic
        fun <T> assertPaintComponentFinality(type: KClass<T>) where T : XView, T : JComponent {
            val method = type.getMemberFunction("paintComponent")
            // Ensure the method is final
            assertTrue(method.isFinal)
        }

    }

    @Test
    fun testXViewImplementation()

}

private fun KClass<*>.getMemberFunction(name: String): KFunction<*>
        = this.memberFunctions.first { it.name == name }