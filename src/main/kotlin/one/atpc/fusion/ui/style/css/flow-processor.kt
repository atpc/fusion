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

package one.atpc.fusion.ui.style.css

import java.io.File

internal fun process(text: String) {
    // Idealized pipeline:
    // [Token] = Tokens
    // Text -> Tokens -> [Line(Tokens)] -> [[Selector] & [Declaration(Tokens, Tokens)])]
    //      -> [[Selector] & [SubStyle]] -> Style
    var intermediateResult: Any? = TextData(text)
    while (intermediateResult != null && intermediateResult is Data<*>) {
        intermediateResult = intermediateResult.next()
    }

    val result = intermediateResult.toString()  // DEBUG toString()
    // (DEBUG CODE) Write to log file
    File("loader.log").writeText(result)
}


internal interface Data<R> {

    fun next(): R

}

internal data class TextData(val content: String) : Data<TokenData> {

    // Tokenize & clean
    override fun next(): TokenData = TokenData(Lexer.tokenize(text = content))

}

internal data class TokenData(val tokens: Tokens) : Data<String> {

    override fun next(): String = Parser.parse(tokens)

}

internal typealias Tokens = List<String>
internal typealias Line = List<String>
