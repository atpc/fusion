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

@file:Suppress("FunctionName")

package one.atpc.fusion.ui.event

import one.atpc.fusion.ConstructorFunction
import java.awt.event.FocusEvent

interface FocusListener : java.awt.event.FocusListener {
    companion object {

        @JvmStatic
        @ConstructorFunction(FocusListener.FocusGained::class)
        fun FocusGained(onFocusGained: (FocusEvent?) -> Unit): FocusListener.FocusGained
                = object : FocusGained {
            override fun focusGained(e: FocusEvent?) = onFocusGained(e)
        }

        @JvmStatic
        @ConstructorFunction(FocusListener.FocusLost::class)
        fun FocusLost(onFocusLost: (FocusEvent?) -> Unit): FocusListener.FocusLost
                = object : FocusLost {
            override fun focusLost(e: FocusEvent?) = onFocusLost(e)
        }

    }

    @FunctionalInterface
    interface FocusGained : one.atpc.fusion.ui.event.FocusListener {

        override fun focusLost(e: FocusEvent?) = Unit

    }

    @FunctionalInterface
    interface FocusLost : one.atpc.fusion.ui.event.FocusListener {

        override fun focusGained(e: FocusEvent?) = Unit

    }

}