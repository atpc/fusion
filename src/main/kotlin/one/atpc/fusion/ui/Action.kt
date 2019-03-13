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

import javax.swing.Icon

typealias Action = javax.swing.Action

@Suppress("UNCHECKED_CAST")
operator fun <T> Action.get(key: String): T?
        = this.getValue(key) as T?

operator fun <T> Action.set(key: String, value: T?)
        = this.putValue(key, value)


/**
 * The action name, used for a menu or button.
 * Shorthand for accessing [`[Action.NAME]`][javax.swing.Action.NAME].
 *
 * @see javax.swing.Action.NAME
 * @see javax.swing.Action.get
 * @author Thomas Orlando
 */
var Action.name: String?
    get() = this[Action.NAME]
    set(value) {
        this[Action.NAME] = value
    }

var Action.shortDescription: String?
    get() = this[Action.SHORT_DESCRIPTION]
    set(value) {
        this[Action.SHORT_DESCRIPTION] = value
    }

var Action.longDescription: String?
    get() = this[Action.LONG_DESCRIPTION]
    set(value) {
        this[Action.LONG_DESCRIPTION] = value
    }

var Action.smallIcon: Icon?
    get() = this[Action.SMALL_ICON]
    set(value) {
        this[Action.SMALL_ICON] = value
    }

var Action.largeIcon: Icon?
    get() = this[Action.LARGE_ICON_KEY]
    set(value) {
        this[Action.LARGE_ICON_KEY] = value
    }

var Action.actionCommand: String?
    get() = this[Action.ACTION_COMMAND_KEY]
    set(value) {
        this[Action.ACTION_COMMAND_KEY] = value
    }

var Action.keyShortcut: KeyStroke?
    get() = this[Action.ACCELERATOR_KEY]
    set(value) {
        this[Action.ACCELERATOR_KEY] = value
    }

// Setting the mnemonic the old Swing/Fusion way is
// (even though only the char method is deprecated) obsolete,
// and therefore not implemented as a shorthand.
//
// 'displayedMnemonicIndex' should be used instead

var Action.displayedMnemonicIndex: Int?
    get() = this[Action.DISPLAYED_MNEMONIC_INDEX_KEY]
    set(value) {
        this[Action.DISPLAYED_MNEMONIC_INDEX_KEY] = value
    }

var Action.selected: Boolean?
    get() = this[Action.SELECTED_KEY]
    set(value) {
        this[Action.SELECTED_KEY] = value
    }


// TODO Create tests