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

@file:JvmName("Processor")

package one.atpc.fusion.ui.style.css

import one.atpc.fusion.util.compose
import java.io.File

internal fun process(text: String) {
    val result = (Parser::parse compose Lexer::tokenize) (text)

    // TODO Debug code
    // (DEBUG CODE) Write to log file
    File("loader.log").writeText(result.debugString) // DEBUG log output
}

internal typealias Tokens = List<String>
internal typealias Line = List<String>
