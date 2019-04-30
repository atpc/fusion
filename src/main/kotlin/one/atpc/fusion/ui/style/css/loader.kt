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

@file:JvmName("CSSLoader")

package one.atpc.fusion.ui.style.css

import one.atpc.fusion.util.toString
import java.io.File
import java.io.InputStream
import java.nio.charset.Charset

// TODO Stub
fun loadCSS(file: File, charset: Charset? = null) = loadCSS(
    if (charset != null) file.readText(charset) else file.readText()
)

fun loadCSS(inputStream: InputStream, charset: Charset? = null) = loadCSS(
    if (charset != null) toString(inputStream, charset) else toString(inputStream)
)

fun loadCSS(text: String) {
    val tokens = tokenize(text)
    val result = parse(tokens)
    // Write to log file
    File("loader.log").writeText(result)
}