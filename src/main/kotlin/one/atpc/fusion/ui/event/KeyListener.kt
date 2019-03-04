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
import java.awt.event.KeyEvent

interface KeyListener : java.awt.event.KeyListener {

    companion object {

        @JvmStatic
        @ConstructorFunction(KeyListener.KeyPressed::class)
        fun KeyPressed(onKeyPressed: (KeyEvent?) -> Unit): KeyListener.KeyPressed
                = object : KeyPressed {
            override fun keyPressed(e: KeyEvent?) = onKeyPressed(e)
        }

        @JvmStatic
        @ConstructorFunction(KeyListener.KeyReleased::class)
        fun KeyReleased(onKeyReleased: (KeyEvent?) -> Unit): KeyListener.KeyReleased
                = object : KeyReleased {
            override fun keyReleased(e: KeyEvent?) = onKeyReleased(e)
        }

        @JvmStatic
        @ConstructorFunction(KeyListener.KeyTyped::class)
        fun KeyTyped(onKeyTyped: (KeyEvent?) -> Unit): KeyListener.KeyTyped
                = object : KeyTyped {
            override fun keyTyped(e: KeyEvent?) = onKeyTyped(e)
        }

    }


    abstract class Adapter : KeyListener {

        override fun keyPressed(e: KeyEvent?) = Unit

        override fun keyReleased(e: KeyEvent?) = Unit

        override fun keyTyped(e: KeyEvent?) = Unit

    }

    interface LightAdapter : KeyListener {

        override fun keyPressed(e: KeyEvent?) = Unit

        override fun keyReleased(e: KeyEvent?) = Unit

        override fun keyTyped(e: KeyEvent?) = Unit

    }


    @FunctionalInterface
    interface KeyPressed : KeyListener {

        override fun keyReleased(e: KeyEvent?) = Unit

        override fun keyTyped(e: KeyEvent?) = Unit

    }

    @FunctionalInterface
    interface KeyReleased : KeyListener {

        override fun keyPressed(e: KeyEvent?) = Unit

        override fun keyTyped(e: KeyEvent?) = Unit

    }

    @FunctionalInterface
    interface KeyTyped : KeyListener {

        override fun keyReleased(e: KeyEvent?) = Unit

        override fun keyPressed(e: KeyEvent?) = Unit

    }

}