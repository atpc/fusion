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

internal object Lexer {
    private val splitters: CharArray = charArrayOf('{', '}', ',', ';', ':', ' ', '\t', '\n')

    internal fun tokenize(text: String): Tokens = tokenize0(text).clean()

    private tailrec fun tokenize0(text: String, tokens: List<String> = emptyList()): List<String> {
        // Find the first splitter
        val splitterIndex = text.indexOfAny(splitters)

        return if (splitterIndex == -1)
            tokens + listOf(text)
        else
            tokenize0(
                text = text.substring(splitterIndex + 1),
                tokens = tokens + listOf(
                    text.substring(0, splitterIndex),
                    text[splitterIndex].toString()
                )
            )
    }


    // this.filter { t -> t.isNotBlank() }
    private fun Tokens.clean(): Tokens = this.fold(emptyList()) {
        acc, e -> if (e.isNotBlank()) acc + e else acc
    }

}
