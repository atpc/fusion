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

package one.atpc.fusion.ui

import one.atpc.fusion.ConstructorFunction
import java.awt.event.InputEvent
import java.awt.event.KeyEvent
import java.util.*
import javax.swing.KeyStroke

typealias XKeyStroke = KeyStroke

@JvmOverloads
@ConstructorFunction(XKeyStroke::class)
fun XKeyStroke(keyChar: Char, modifier: Modifier? = null): XKeyStroke
        = XKeyStroke.getKeyStroke(keyChar, modifier?.code ?: 0)

@ConstructorFunction(XKeyStroke::class)
fun XKeyStroke(keyChar: Char, modifier: Modifier, vararg additionalModifiers: Modifier): KeyStroke {
    var mask = modifier.code
    for (additionalModifier in additionalModifiers)
        mask = mask or additionalModifier.code

    return XKeyStroke.getKeyStroke(keyChar, mask)
}

@JvmOverloads
@ConstructorFunction(XKeyStroke::class)
fun XKeyStroke(keyCode: Int, modifier: Modifier? = null): XKeyStroke
        = XKeyStroke.getKeyStroke(keyCode, modifier?.code ?: 0)

@ConstructorFunction(XKeyStroke::class)
fun XKeyStroke(keyChar: Char, modifiers: Int): XKeyStroke = XKeyStroke.getKeyStroke(keyChar, modifiers)


private val keyStrokeModifierObjectsMap: MutableMap<XKeyStroke, List<Modifier>> = hashMapOf()

val XKeyStroke.modifierObjects: List<Modifier> get() {
    val precomputedModifierObjects = keyStrokeModifierObjectsMap[this]
    if (precomputedModifierObjects != null)
        return precomputedModifierObjects
    else {
        val modifiers = this.modifiers
        val modifierList = arrayListOf<Modifier>()

        // Go through each modifier constant in the Modifier enum
        // and test if this KeyStroke's modifiers contain the constant's value
        for (modifier in Modifier.values()) {
            if ((modifiers and modifier.code) != 0)
                modifierList.add(modifier)
        }

        keyStrokeModifierObjectsMap[this] = modifierList

        return modifierList
    }
}

val XKeyStroke.stringSignature: String get() {
    val builder = StringBuilder()

    for (modifier in modifierObjects) {
        builder.append(modifier.toString())
        // If the modifier is not the meta key, append a +
        if (modifier != Modifier.META)
            builder.append('+')
    }
    // Detect if we use a keyChar or keyCode
    if (this.keyCode == KeyEvent.VK_UNDEFINED)
        builder.append(this.keyChar)
    else {
        val stringRep = Keys.getVKText(keyCode).toLowerCase(Locale.ENGLISH)
        builder.append(stringRep.replaceAt(0, stringRep[0].toUpperCase()))
    }

    return builder.toString()
}


private val modifierCodeMap = hashMapOf<Int, Modifier>()

enum class Modifier(val code: Int, private val stringRepresentation: String?) {
    // Use the menu order (Ctrl+Alt+Shift+⌘C)
    CTRL      (InputEvent.CTRL_DOWN_MASK, "Ctrl"),
    ALT       (InputEvent.ALT_DOWN_MASK, "Alt"),
    SHIFT     (InputEvent.SHIFT_DOWN_MASK, "Shift"),
    ALT_GRAPH (InputEvent.ALT_GRAPH_DOWN_MASK, "AltGr"),
    BUTTON1   (InputEvent.BUTTON1_DOWN_MASK, null),
    BUTTON2   (InputEvent.BUTTON2_DOWN_MASK, null),
    BUTTON3   (InputEvent.BUTTON3_DOWN_MASK, null),
    META      (InputEvent.META_DOWN_MASK, "⌘")
    ;

    companion object {
        @JvmStatic
        val menuShortcutKeyMask: Modifier get() = Modifier[Toolkit.getDefaultToolkit().menuShortcutKeyMask]!!

        @JvmStatic
        operator fun get(modifier: Int): Modifier? = modifierCodeMap[modifier]
    }

    init {
        modifierCodeMap[code] = this
    }

    override fun toString(): String = this.stringRepresentation.toString()

}


private fun String.replaceAt(index: Int, replacement: Char): String {
    val chars = this.toCharArray()
    chars[index] = replacement
    return String(chars)
}