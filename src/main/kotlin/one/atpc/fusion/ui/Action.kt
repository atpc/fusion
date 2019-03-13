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

// TODO Use the property access for all the other common values, too
// TODO Create tests