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

internal fun parse(tokens: List<String>) {
    val blocks = splitToBlocks(cleanTokens(tokens))
    // TODO Build a selector dependency graph and apply the style graph along
}


private val WHITESPACES = listOf(" ", "\t", "\n")

private fun cleanTokens(tokens: List<String>): List<String> = tokens.filter { t -> t in WHITESPACES }


private data class DeclarationBlock(val selectors: List<String>,
                                    val declarations: List<List<String>>) {

    companion object {

        fun fromTokens(tokens: List<String>): DeclarationBlock {
            val blockStartIndex = tokens.indexOf(BLOCK_START)
            val blockEndIndex = tokens.lastIndexOf(BLOCK_END)

            val selectors = tokens.subList(0, blockStartIndex)
            val declarations = splitToLines(tokens.subList(blockStartIndex+1, blockEndIndex))

            return DeclarationBlock(selectors, declarations)
        }

    }

}


private const val BLOCK_START = "{"
private const val BLOCK_END = "}"
private const val LINE_SEPARATOR = ";"

private tailrec fun splitToBlocks(tokens: List<String>,
                                  blockList: List<DeclarationBlock> = emptyList()): List<DeclarationBlock> {
    // Get first block end (Use block ends as indicators)
    val firstBlockEnd = tokens.indexOf(BLOCK_END)

    // If there is no block left, end the recursion and return the finished the block list
    return if (firstBlockEnd == -1)
        blockList
    else
        splitToBlocks(
            tokens.subList(firstBlockEnd+1, tokens.size),
            blockList + DeclarationBlock.fromTokens(tokens[0..firstBlockEnd])
        )
}

private tailrec fun splitToLines(tokens: List<String>, lines: List<List<String>> = emptyList()): List<List<String>> {
    // Collect lines
    val firstSemicolonIndex = tokens.indexOf(LINE_SEPARATOR)

    return if (firstSemicolonIndex == -1)
        // Finished (no semicolon => no lines left)
        lines
    else
        splitToLines(
            tokens.subList(firstSemicolonIndex+1, tokens.size),
            lines + lines.subList(0, firstSemicolonIndex)
        )
}


// TODO Move to util
private operator fun <T> List<T>.get(slice: IntRange): List<T> = this.slice(slice)
