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
    val blocks = Tokens(tokens).clean().splitToBlocks()
    return "$tokens\n\n\n$blocks"
    // TODO Build a selector dependency graph and apply the style graph along
}


private const val BLOCK_START = "{"
private const val BLOCK_END = "}"
private const val LINE_SEPARATOR = ";"
private const val DECLARATION_SEPARATOR = ":"


private inline class Tokens(val value: List<String>) : List<String> {

    companion object {
        private tailrec fun splitToBlocks0(tokens: Tokens, declarationBlockList: List<DeclarationBlock> = emptyList()): List<DeclarationBlock> {
            // Get first block end (Use block ends as indicators)
            val firstBlockEnd = tokens.indexOf(BLOCK_END)

            // If there is no block left, end the recursion and return the finished the block list
            return if (firstBlockEnd == -1)
                declarationBlockList
            else
                splitToBlocks0(
                    Tokens(tokens.subList(firstBlockEnd+1, tokens.size)),
                    declarationBlockList + Tokens(tokens[0..firstBlockEnd]).toBlock()
                )
        }
    }

    fun clean(): Tokens = Tokens(value.filter { t -> t.isNotBlank() })

    fun splitToLines(): List<Line> = value.split(LINE_SEPARATOR)

    fun toBlock(): DeclarationBlock {
        val blockStartIndex = this.indexOf(BLOCK_START)
        val blockEndIndex = this.lastIndexOf(BLOCK_END)

        val selectors = this.subList(0, blockStartIndex)
        val lines = Tokens(this.subList(blockStartIndex+1, blockEndIndex)).splitToLines()

        return DeclarationBlock(selectors, lines.map { it.toDeclaration() })
    }

    fun splitToBlocks() = splitToBlocks0(this)


    override val size: Int get() = value.size

    override fun isEmpty(): Boolean = value.isEmpty()

    override fun contains(element: String): Boolean = value.contains(element)

    override fun containsAll(elements: Collection<String>): Boolean = value.containsAll(elements)

    override fun get(index: Int): String = value[index]

    override fun subList(fromIndex: Int, toIndex: Int): List<String> = value.subList(fromIndex, toIndex)

    override fun indexOf(element: String): Int = value.indexOf(element)

    override fun lastIndexOf(element: String): Int = value.lastIndexOf(element)

    override fun iterator(): Iterator<String> = value.iterator()

    override fun listIterator(): ListIterator<String> = value.listIterator()

    override fun listIterator(index: Int): ListIterator<String> = value.listIterator(index)

}


private typealias Line = List<String>

private fun Line.toDeclaration(): Declaration {
    if (this.isEmpty())
        throw IllegalArgumentException("Declaration line is empty!")

    val declarationParts = this.split(DECLARATION_SEPARATOR)

    when {
        declarationParts.size > 2 -> throw ParserException("Too many colons: $declarationParts!")
        declarationParts.size < 2 -> throw ParserException("Not a valid declaration: $declarationParts!")
        else -> {
            // Check declaration parts size (must not exceed 1)
            declarationParts.forEach { if (it.size > 1) ParserException("Number of declaration parts exceeds 1!") }
            return Declaration(
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
