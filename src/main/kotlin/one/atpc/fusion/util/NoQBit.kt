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

package one.atpc.fusion.util

enum class NoQBit(private val booleanValue: Boolean?) {
    FALSE (false),
    TRUE (true),
    SUPER (null)
    ;

    fun collapse(tendency: Boolean): Boolean = booleanValue ?: tendency

    companion object {

        @JvmStatic
        fun of(value: Boolean?): NoQBit = when (value) {
            false -> FALSE
            true -> TRUE
            else -> SUPER
        }


    }

}
