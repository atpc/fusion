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

import one.atpc.fusion.util.get
import one.atpc.fusion.util.split

internal fun parse(tokens: List<String>): String {
    val blocks = splitToBlocks(cleanTokens(tokens))
    return "$tokens\n\n\n$blocks"
    // TODO Build a selector dependency graph and apply the style graph along
}


internal /*private*/ fun cleanTokens(tokens: List<String>): List<String> = tokens.filter { t -> t.isNotBlank() }


private data class StyleBlock(val selectors: List<String>,
                              val declarations: List<StyleDeclaration>) {

    override fun toString(): String {
        val builder = StringBuilder("$selectors {\n")
        declarations.forEach { declaration ->
            builder.append("\t$declaration")
        }
        builder.append('}')

        return builder.toString()
    }

    companion object {

        fun fromTokens(tokens: List<String>): StyleBlock {
            val blockStartIndex = tokens.indexOf(BLOCK_START)
            val blockEndIndex = tokens.lastIndexOf(BLOCK_END)

            val selectors = tokens.subList(0, blockStartIndex)
            val lines = splitToLines(tokens.subList(blockStartIndex+1, blockEndIndex))

            return StyleBlock(selectors, lines.map(::toDeclaration))
        }

    }

}

private data class StyleDeclaration(val property: List<String>, val value: List<String>) {
    override fun toString(): String = "[$property:$value]"
}


private const val BLOCK_START = "{"
private const val BLOCK_END = "}"
private const val LINE_SEPARATOR = ";"
private const val DECLARATION_SEPARATOR = ":"

private tailrec fun splitToBlocks(tokens: List<String>,
                                  blockList: List<StyleBlock> = emptyList()): List<StyleBlock> {
    // Get first block end (Use block ends as indicators)
    val firstBlockEnd = tokens.indexOf(BLOCK_END)

    // If there is no block left, end the recursion and return the finished the block list
    return if (firstBlockEnd == -1)
        blockList
    else
        splitToBlocks(
            tokens.subList(firstBlockEnd+1, tokens.size),
            blockList + StyleBlock.fromTokens(tokens[0..firstBlockEnd])
        )
}

private fun splitToLines(tokens: List<String>): List<List<String>> = tokens.split(LINE_SEPARATOR)

private fun toDeclaration(line: List<String>): StyleDeclaration {
    if (line.isEmpty()) {
        println("Empty line!")
        return StyleDeclaration(emptyList(), emptyList())
    }

    val declarationParts = line.split(DECLARATION_SEPARATOR)

    when {
        declarationParts.size > 2 -> throw ParserException("Too many colons: $declarationParts!")
        declarationParts.size < 2 -> throw ParserException("Not a valid declaration: $declarationParts!")
        else -> {
            // Check declaration parts size (must not exceed 1)
            declarationParts.forEach { if (it.size > 1) ParserException("Number of declaration parts exceeds 1!") }
            return StyleDeclaration(
                declarationParts[0],
                declarationParts[1]
            )
        }
    }
}

// TODO Move to util
private fun List<String>.foldToString(): String = this.fold("") {
    acc, s -> acc + s
}
