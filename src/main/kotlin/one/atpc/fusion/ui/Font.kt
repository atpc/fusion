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
import java.awt.FontFormatException
import java.awt.font.TextAttribute
import java.io.IOException
import java.io.InputStream

/**
 * TODO Improve this documentation
 * `Font` is an immutable type. To change it's properties, you need to
 * derive a new font:
 * ```
 * // Here, we derive a font with a new size and style
 * val headlineFont = font.deriveFont(size = 28.0, style = Font.BOLD)
 * val textFont = font.deriveFont(14.0)
 * val boldFont = textFont.deriveFont(Font.BOLD)
 * ```
 */
typealias Font = java.awt.Font


// Font extensions & code

/**
 * Returns a new [Font] using the specified font format
 * and input data.  The new `Font` is
 * created with the specified point size and style [Font.PLAIN].
 * This base font can then be used with the [Font.deriveFont]
 * methods in this class to derive new `Font` objects with
 * varying sizes, styles, transforms and font features.  This
 * method does not close the [InputStream].
 * <p>
 * To make the `Font` available to `Font` constructors the
 * returned `Font` must be registered in the
 * [java.awt.GraphicsEnvironment] by calling
 * [java.awt.GraphicsEnvironment.registerFont].
 * @param fontFormat The type of the `Font`, which is
 *     [Font.TRUETYPE_FONT] if a TrueType resource is specified,
 *     or [Font.TYPE1_FONT] if a Type 1 resource is specified.
 * @param fontStream An `InputStream` object representing the
 *     input data for the font.
 * @return A new `Font` created with the specified [fontFormat] and [fontSize].
 * @throws IllegalArgumentException If [fontFormat] is not
 *     [Font.TRUETYPE_FONT] or [Font.TYPE1_FONT].
 * @throws FontFormatException If the [fontStream] data does
 *     not contain the required font tables for the specified [fontFormat].
 * @throws IOException If the [fontStream] cannot be completely read.
 * @see Font.createFont
 * @see Font.deriveFont
 */
@ConstructorFunction(Font::class)
@Throws(FontFormatException::class, IOException::class)
fun Font(fontFormat: Int, fontStream: InputStream, fontSize: Double): Font
        = Font.createFont(fontFormat, fontStream).deriveFont(fontSize)

// TODO Implement constructor with file

fun Font.deriveFont(size: Double): Font = this.deriveFont(size.toFloat())

fun Font.deriveFont(style: Int, fontSize: Double): Font = this.deriveFont(style, fontSize.toFloat())

private const val KERNING_OFF = 0
fun Font.deriveWithKerning(enabled: Boolean = true) = this.deriveFont(
    mapOf(Pair(TextAttribute.KERNING, if (enabled) TextAttribute.KERNING_ON else KERNING_OFF))
)

fun Font.deriveWithLetterSpacing(letterSpacing: Float) = this.deriveFont(
    mapOf(Pair(TextAttribute.TRACKING, letterSpacing))
)