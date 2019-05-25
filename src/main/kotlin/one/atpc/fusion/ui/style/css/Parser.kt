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

import one.atpc.fusion.ui.Color
import one.atpc.fusion.ui.style.PartStyle
import one.atpc.fusion.ui.style.PartStyleBuilder
import one.atpc.fusion.ui.style.Style
import one.atpc.fusion.ui.style.StyleBuilder
import one.atpc.fusion.util.compose
import one.atpc.fusion.util.foldToString
import one.atpc.fusion.util.get
import one.atpc.fusion.util.split
import java.lang.Long.parseLong

internal object Parser {

    private data class DeclarationBlock(val selectors: List<String>,
                                        val declarations: List<Declaration>) {
        override fun toString(): String {
            val builder = StringBuilder("$selectors {\n")
            declarations.forEach { declaration ->
                builder.append("\t$declaration")
            }
            builder.append('}')

            return builder.toString()
        }
    }

    private data class Declaration(val property: List<String>,
                                   val value: List<String>) {
        override fun toString(): String = "[$property: $value;]"
    }



    internal fun parse(tokens: Tokens): Style = (::parseBlocks compose ::splitToDeclarationBlocks) (tokens)


    private fun parseBlocks(blocks: List<DeclarationBlock>): Style {
        val styleBuilder = StyleBuilder()
        blocks.forEach { block ->
            // Convert the block declarations into a PartStyle
            // TODO Use fold() or foldRight()?
            val blockSubStyle = block.declarations.foldRight(PartStyleBuilder()) { declaration, builder ->
                // Parse value (using the internal Any setter)
                builder[declaration.property.foldToString()] = ValueParser.parseValue(declaration.value)
                // Builder is returned since it's the accumulator
                builder
            }.toSubStyle()
            
            // Add the PartStyle to the specified selectors in the Style
            block.selectors.forEach { selector ->
                val partStyleAtSelector: PartStyle? = styleBuilder[selector]

                if (partStyleAtSelector != null)
                    // Merge styles (the newer – blockSubStyle – overrides the older)
                    styleBuilder[selector] = partStyleAtSelector combineWith blockSubStyle
                else
                    styleBuilder[selector] = blockSubStyle
            }
        }

        return styleBuilder.toStyle()
    }



    private const val BLOCK_START           = "{"
    private const val BLOCK_END             = "}"
    private const val LINE_SEPARATOR        = ";"
    private const val DECLARATION_SEPARATOR = ":"


    private fun splitToDeclarationBlocks(tokens: Tokens) = splitToDeclarationBlocks0(tokens)

    private tailrec fun splitToDeclarationBlocks0(tokens: Tokens,
                                                  declarationBlockList: List<DeclarationBlock> = emptyList()
    ): List<DeclarationBlock> {

        // Get first block end (Use block ends as indicators)
        val firstBlockEnd = tokens.indexOf(BLOCK_END)

        // If there is no block left, end the recursion and return the finished the block list
        return if (firstBlockEnd == -1)
            declarationBlockList
        else
            splitToDeclarationBlocks0(
                tokens.subList(firstBlockEnd+1, tokens.size),
                declarationBlockList + tokens[0..firstBlockEnd].toBlock()
            )
    }

    private fun Tokens.toBlock(): DeclarationBlock {
        val blockStartIndex = this.indexOf(BLOCK_START)
        val blockEndIndex = this.lastIndexOf(BLOCK_END)

        val selectors = this.subList(0, blockStartIndex)
        val lines = this.subList(blockStartIndex+1, blockEndIndex).splitToLines()

        return DeclarationBlock(selectors, lines.map { it.toDeclaration() })
    }

    private fun Tokens.splitToLines(): List<Line> = this.split(LINE_SEPARATOR)


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

}

// Parsing CSS values [200px, #ffd9e5, rgb(0, 12, 134), calc(20px - 4em), ...]
private object ValueParser {

    internal fun parseValue(value: Tokens): Any {
        // IMPORTANT All this code assumes that the value does not contain whitespace tokens
        return if (value.size == 1) {
            val token = value[0]
            if (token.startsWith("#")) {
                // (Try to) interpret as hex color
                val hexColorValue = token.substring(1)
                Color.rgb(when (hexColorValue.length) {
                    3 -> parseLong(hexColorValue.fold("") { acc, c -> acc + c + c }, 16) // Expand #RGB to #RRGGBB
                    6 -> parseLong(hexColorValue, 16)   // Use the hex color value directly
                    else -> throw ParserException("Illegal CSS color value: '$token'!")
                }.toUInt())
            }
            else {
                TODO("Missing branch for non-hex-color single-token values")
                // Keywords, Color names (TODO color names in external properties), etc.
            }
        }
        else {
            for (token in value) {
                // Further tokenize the value
            }
        }
    }

    // TODO Stub
    private fun fineTokenize(value: Tokens): Tokens = value.fold(emptyList()) {
            acc: Tokens, token: String -> acc + token
    }

}