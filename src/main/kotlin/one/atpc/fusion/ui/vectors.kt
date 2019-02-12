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

package one.atpc.fusion.ui

import one.atpc.fusion.Copyable

/**
 * Representation of an abstract, indefinite vector.
 * <br>
 * TODO Short explanation and purpose
 *
 * @author Thomas Orlando
 */
// Vector should not be a list (but maybe add a conversion method toList())
interface Vector : Copyable, Iterable<Double> {

    /**
     * The size (dimensions) of this vector.
     */
    val size: Int

    operator fun get(index: Int): Double

    // Do not add operator functions like + and -,
    // it would be misleading if you could add e.g. Points and Dimensions together
    // (strong typing, Kotlin-style)

    override fun copy(): Vector

    override fun iterator(): Iterator<Double> = object : Iterator<Double> {
        private var index: Int = 0

        override fun hasNext(): Boolean = index < this@Vector.size

        override fun next(): Double = this@Vector[index++]
    }


    fun vmap(transform: (Double) -> Double): Vector

    fun vmapIndexed(transform: (index: Int, Double) -> Double): Vector

}

/**
 * Base for a two-dimensional vector.
 *
 * @author Thomas Orlando
 */
interface Vector2 : Vector {

    override val size: Int get() = 2

    override fun copy(): Vector2

    override fun vmap(transform: (Double) -> Double): Vector2

    override fun vmapIndexed(transform: (index: Int, Double) -> Double): Vector2

}

/**
 * Base for a four-dimensional vector.
 *
 * @author Thomas Orlando
 */
interface Vector4 : Vector {

    override val size: Int get() = 4

    override fun copy(): Vector4

    override fun vmap(transform: (Double) -> Double): Vector4

    override fun vmapIndexed(transform: (index: Int, Double) -> Double): Vector4

}
