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

import io.kotlintest.shouldBe
import io.kotlintest.specs.FreeSpec
import one.atpc.fusion.util.UnBool
import java.awt.event.ActionEvent
import java.util.*
import javax.swing.AbstractAction

class ActionTests : FreeSpec({
    val action = object : AbstractAction() {
        override fun actionPerformed(e: ActionEvent?) = Unit
    }

    val testValues = mapOf(
        "test01" to randomString(),
        "test02" to 123,
        "test03" to UnBool.UNCERTAIN
    )
    "set and get operators" {
        for (key in testValues.keys) {
            val testValue = testValues[key]

            // Assign a value
            action[key] = testValue
            // Verify assignment worked
            action.get<Any>(key) shouldBe testValue

            // Ensure get is compatible with Action.getValue()
            action.getValue(key) shouldBe action[key]
            action.getValue(key) shouldBe testValue
        }
    }

    // TODO Verify shorthand properties
})

private fun randomString() = UUID.randomUUID().toString()