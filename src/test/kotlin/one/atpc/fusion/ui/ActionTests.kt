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
import javax.swing.ImageIcon
import kotlin.random.Random
import kotlin.reflect.KMutableProperty1

class ActionTests : FreeSpec({
    val testValues = mapOf(
        "test01" to randomString(),
        "test02" to 123,
        "test03" to UnBool.UNCERTAIN
    )
    "set and get operators" {
        val action = ExampleAction()
        
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

    // A map of shorthand property keys along with test values
    val shorthandProperties: Map<Pair<KMutableProperty1<Action, out Any?>, String>, Any> = mapOf(
        Action::name                    to Action.NAME                          to randomString(),
        Action::shortDescription        to Action.SHORT_DESCRIPTION             to randomString(),
        Action::longDescription         to Action.LONG_DESCRIPTION              to randomString(),
        Action::smallIcon               to Action.SMALL_ICON                    to iconValue(),
        Action::largeIcon               to Action.LARGE_ICON_KEY                to iconValue(),
        Action::actionCommand           to Action.ACTION_COMMAND_KEY            to randomString(),
        Action::keyShortcut             to Action.ACCELERATOR_KEY               to shortcutValue(),
        Action::displayedMnemonicIndex  to Action.DISPLAYED_MNEMONIC_INDEX_KEY  to randomInt(),
        Action::selected                to Action.SELECTED_KEY                  to randomBoolean()
    )
    "verify shorthand properties" {
        val action = ExampleAction()
        
        for (propertyPair in shorthandProperties.keys) {
            val property = propertyPair.first
            val key = propertyPair.second

            // Get the test value
            val testValue = shorthandProperties[propertyPair]

            // Set the value
            property.setter.call(action, testValue)
            // Ensure the setting worked:
            // (Property getter)
            property.get(action) shouldBe testValue
            // (get operator)
            action.get<Any>(key) shouldBe testValue
        }
    }

})

private class ExampleAction : AbstractAction() {
    override fun actionPerformed(e: ActionEvent?) = Unit
}

private fun randomString() = UUID.randomUUID().toString()
private fun randomInt() = Random.nextInt()
private fun randomBoolean() = Random.nextBoolean()
private fun iconValue() = ImageIcon(ActionTests::class.java.getResource("test-icon.png"))
private fun shortcutValue() = KeyStroke('Q', Modifier.CTRL)