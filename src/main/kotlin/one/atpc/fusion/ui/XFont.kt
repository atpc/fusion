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
import java.awt.Font
import java.awt.FontFormatException
import java.awt.font.TextAttribute
import java.io.IOException
import java.io.InputStream

/**
 * TODO Improve this documentation
 * `XFont` is an immutable type. To change properties, you need to
 * derive a new font:
 * ```
 * // Here, we derive a font with a new size and style
 * val headlineFont = font.deriveFont(size = 28.0, style = Font.BOLD)
 * val textFont = font.deriveFont(14.0)
 * val boldFont = textFont.deriveFont(Font.BOLD)
 * ```
 */
typealias XFont = Font


// Font extensions & code

/**
 * Returns a new [XFont] using the specified font format
 * and input data.  The new `XFont` is
 * created with the specified point size and style [Font.PLAIN].
 * This base font can then be used with the [XFont.deriveFont]
 * methods in this class to derive new `XFont` objects with
 * varying sizes, styles, transforms and font features.  This
 * method does not close the [InputStream].
 * <p>
 * To make the `XFont` available to `XFont` constructors the
 * returned `XFont` must be registered in the
 * [java.awt.GraphicsEnvironment] by calling
 * [java.awt.GraphicsEnvironment.registerFont].
 * @param fontFormat The type of the `XFont`, which is
 *     [Font.TRUETYPE_FONT] if a TrueType resource is specified,
 *     or [Font.TYPE1_FONT] if a Type 1 resource is specified.
 * @param fontStream An `InputStream` object representing the
 *     input data for the font.
 * @return A new `XFont` created with the specified [fontFormat] and [fontSize].
 * @throws IllegalArgumentException If [fontFormat] is not
 *     [Font.TRUETYPE_FONT] or [Font.TYPE1_FONT].
 * @throws FontFormatException If the [fontStream] data does
 *     not contain the required font tables for the specified [fontFormat].
 * @throws IOException If the [fontStream] cannot be completely read.
 * @see Font.createFont
 * @see XFont.deriveFont
 */
@ConstructorFunction(XFont::class)
@Throws(FontFormatException::class, IOException::class)
fun XFont(fontFormat: Int, fontStream: InputStream, fontSize: Double): XFont
        = Font.createFont(fontFormat, fontStream).deriveFont(fontSize)

// TODO Implement constructor with file

fun XFont.deriveFont(size: Double): XFont = this.deriveFont(size.toFloat())

fun XFont.deriveFont(style: Int, fontSize: Double): XFont = this.deriveFont(style, fontSize.toFloat())

private const val KERNING_OFF = 0
fun XFont.deriveWithKerning(enabled: Boolean = true) = this.deriveFont(
    mapOf(Pair(TextAttribute.KERNING, if (enabled) TextAttribute.KERNING_ON else KERNING_OFF))
)

fun XFont.deriveWithLetterSpacing(letterSpacing: Float) = this.deriveFont(
    mapOf(Pair(TextAttribute.TRACKING, letterSpacing))
)