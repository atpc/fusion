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

import one.atpc.fusion.assertThrown
import org.junit.Assert.assertTrue
import org.junit.Test

class XWindowTest : XContainerTest {

    @Test
    override fun testXViewImplementation() {
        // => No paintComponentFinality assertion necessary
    }

    @Test
    override fun testAddXView() {
        val window = XWindow()
        val text = XText()

        window.add(text) // Should be fine (no exceptions)
        // Ensure text has been added to window
        assertTrue(text in window.contentPane.components)

        // Should NOT be fine
        assertThrown<Throwable> { window.add(dummyXView) }
    }

}

private val dummyXView = object : XView {
    override fun draw(g: XGraphics) = Unit

    override fun redraw() = Unit
    override fun redraw(rect: Rectangle4) = Unit
    override fun redraw(delay: Long) = Unit
    override fun redraw(delay: Long, rect: Rectangle4) = Unit
}