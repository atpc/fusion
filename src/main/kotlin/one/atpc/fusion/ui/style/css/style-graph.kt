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

internal interface StyleNode<T, E> {

    val content: T
    val children: List<E>

}

internal data class DeclarationBlock(val selectors: List<String>,
                                     val declarations: List<Declaration>) : StyleNode<List<String>, Declaration> {

    override val content: List<String> = selectors
    override val children: List<Declaration> = declarations

    override fun toString(): String {
        val builder = StringBuilder("$selectors {\n")
        declarations.forEach { declaration ->
            builder.append("\t$declaration")
        }
        builder.append('}')

        return builder.toString()
    }

}

internal data class Declaration(val property: List<String>,
                                val value: List<String>) : StyleNode<Pair<List<String>, List<String>>, List<Nothing>> {

    override val content: Pair<List<String>, List<String>> = Pair(property, value)
    override val children: List<Nothing> = emptyList()

    override fun toString(): String = "[$property: $value;]"
}
