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
import java.util.*

class ComponentEventExtensionsTest {
    private val component = XComponent()
    private val button = XButton()

    @Test
    fun testComponentEventExtensions() {
        assertListenerAdded(component::getFocusListeners, component::onFocusGained, component::onFocusLost)

        assertListenerAdded(component::getMouseMotionListeners, component::onMouseMoved, component::onMouseDragged)
        assertListenerAdded(component::getMouseListeners,
            component::onMouseEntered,
            component::onMouseExited,
            component::onMousePressed,
            component::onMouseReleased,
            component::onMouseClicked
        )
        assertListenerAdded(component::getMouseWheelListeners, component::onMouseWheelMoved)

        assertListenerAdded(component::getKeyListeners,
            component::onKeyPressed,
            component::onKeyReleased,
            component::onKeyTyped
        )

        assertListenerAdded(button::getActionListeners, button::onActionPerformed)
    }

    private fun <T : EventListener, U> assertListenerAdded(getListeners: () -> Array<T>, vararg actions: (action: (e: U?) -> Unit) -> Unit) {
        for ((count, action) in actions.withIndex()) {
            assertEquals(count, getListeners().size)
            action { }
            assertEquals(count + 1, getListeners().size)
        }
    }

}