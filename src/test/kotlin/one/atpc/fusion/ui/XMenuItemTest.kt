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
import org.junit.Test

class XMenuItemTest : XViewTest {

    @Test
    @Suppress("Deprecation")
    fun testAutomaticToolTipText() {
        val itemDescription = "Example action"

        val menuItem = XMenuItem("Example")
        menuItem.keyShortcut = KeyStroke('E', Modifier.META, Modifier.ALT)
        menuItem.description = itemDescription

        assertEquals("$itemDescription (Alt+⌘E)", menuItem.toolTipText)


        menuItem.keyShortcut = KeyStroke('A', Modifier.META)

        assertEquals("$itemDescription (⌘A)", menuItem.toolTipText)


        menuItem.description = "Sample action"

        assertEquals("Sample action (⌘A)", menuItem.toolTipText)
    }

    @Test
    fun testDescriptionVisibleDefault() {
        val default = XMenuItem()
        assertEquals(true, default.isDescriptionVisible)
    }

    @Test
    override fun testXViewImplementation() {
        XViewTest.assertPaintComponentFinality(XMenuItem::class)
    }

}