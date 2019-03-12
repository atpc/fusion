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

package one.atpc.fusion.ui.feature

import one.atpc.fusion.ui.*
import one.atpc.fusion.ui.event.MouseListener
import java.awt.event.MouseEvent
import javax.swing.JPasswordField
import javax.swing.text.DefaultEditorKit
import javax.swing.text.JTextComponent

// Cut, Copy, Paste, Delete | Select All
open class TextPopupFeature(textComponent: JTextComponent) : Feature<JTextComponent>(), MouseListener.Adapter {
    private val popup: XPopupMenu = XPopupMenu()
    private val items: Array<XMenuItem?>

    init {
        // Code to retrieve the menu items:
        // (Do not add key shortcuts, they should be known)

        val actions = textComponent.actions

        // The position in the itemArray determines the position of
        // the component. null in this context indicates a separator
        val rawItems = Array<XMenuItem?>(NUM_ITEMS) {null}
        for (action in actions) {
            val name = action.name
            if (name in ITEM_POSITIONS.keys) {
                // Get the item's position
                val index = ITEM_POSITIONS.getValue(name)
                val item = XMenuItem(action)
                // Set the name to the pre-defined, readable name
                item.text = ITEM_NAMES[index]
                // Set the item at the pre-defined position in the array
                // (Use the position information as index)
                rawItems[index] = item
            }
        }
        // Add the delete item (Swing doesn't have this one, so it needs to be added additionally)
        val deleteItem = XMenuItem(ITEM_NAMES[DELETE])
        deleteItem.onActionPerformed {
            // Delete selected text
            textComponent.replaceSelection("")
        }
        rawItems[DELETE] = deleteItem

        // Add the items (and separators)
        for (item in rawItems) {
            if (item == null)
                // null means a separator has to be set here
                popup.addSeparator()
            else {
                popup.add(item)
            }
        }
        // Save all the items
        items = rawItems

        // Add the popup menu
        textComponent.add(popup)
    }

    // Overridden for both mousePressed and mouseReleased for proper cross-platform
    // functionality
    override fun mousePressed(e: MouseEvent?) = mouseActionPerformed(e)

    override fun mouseReleased(e: MouseEvent?) = mouseActionPerformed(e)

    protected open fun mouseActionPerformed(e: MouseEvent?) {
        if (e?.isPopupTrigger == true)
            showPopupMenu(e)
    }

    protected open fun showPopupMenu(e: MouseEvent) {
        // Check if something is selected
        val textSelected = connectedComponent?.isTextSelected ?: false
        // Do not allow cut and copy for password fields!
        val cutAndCopyEnabled = textSelected && connectedComponent !is JPasswordField
        items[CUT]!!.isEnabled  = cutAndCopyEnabled
        items[COPY]!!.isEnabled = cutAndCopyEnabled
        items[DELETE]!!.isEnabled = textSelected
        items[SELECT_ALL]!!.isEnabled = !((connectedComponent?.text).isNullOrEmpty())
        popup.show(e.component, e.x, e.y)
    }

    override fun draw(g: XGraphics) = Unit

    companion object {
        private const val CUT   = 0
        private const val COPY  = 1
        private const val PASTE = 2
        private const val DELETE = 3
        private const val SELECT_ALL = 5
        private const val NUM_ITEMS = 6

        private val ITEM_POSITIONS = mapOf(
            DefaultEditorKit.cutAction to CUT,
            DefaultEditorKit.copyAction to COPY,
            DefaultEditorKit.pasteAction to PASTE,
            DefaultEditorKit.selectAllAction to SELECT_ALL
        )
        private val ITEM_NAMES = listOf(
            "Cut",
            "Copy",
            "Paste",
            "Delete",
            "",     // Separator
            "Select All"
        )
    }

}
