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

@file:JvmName("ActionUtils")

package one.atpc.fusion.ui

import javax.swing.Icon
import kotlin.reflect.KProperty

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
var Action.name: String? by ShorthandPropertyDelegate(Action.NAME)

var Action.shortDescription: String? by ShorthandPropertyDelegate(Action.SHORT_DESCRIPTION)

var Action.longDescription: String? by ShorthandPropertyDelegate(Action.LONG_DESCRIPTION)

var Action.smallIcon: Icon? by ShorthandPropertyDelegate(Action.SMALL_ICON)

var Action.largeIcon: Icon? by ShorthandPropertyDelegate(Action.LARGE_ICON_KEY)

var Action.actionCommand: String? by ShorthandPropertyDelegate(Action.ACTION_COMMAND_KEY)

var Action.keyShortcut: KeyStroke? by ShorthandPropertyDelegate(Action.ACCELERATOR_KEY)

// Setting the mnemonic the old Swing/Fusion way is
// (even though only the char method is deprecated) obsolete,
// and therefore not implemented as a shorthand.
//
// 'displayedMnemonicIndex' should be used instead

var Action.displayedMnemonicIndex: Int? by ShorthandPropertyDelegate(Action.DISPLAYED_MNEMONIC_INDEX_KEY)

var Action.selected: Boolean? by ShorthandPropertyDelegate(Action.SELECTED_KEY)


private class ShorthandPropertyDelegate<T>(private val actionConstant: String) {
    operator fun getValue(thisRef: Action, property: KProperty<*>): T?
            = thisRef[actionConstant]

    operator fun setValue(thisRef: Action, property: KProperty<*>, value: T?) {
        thisRef[actionConstant] = value
    }
}

// TODO Create tests