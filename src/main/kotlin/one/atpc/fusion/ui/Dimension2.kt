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

@file:Suppress("FunctionName")

package one.atpc.fusion.ui

import one.atpc.fusion.ConstructorFunction
import java.awt.Dimension
import java.awt.geom.Dimension2D

// Immutable
interface Dimension2 : Vector2 {

    // Necessary to comply with Dimension2D
    fun getWidth(): kotlin.Double
    fun getHeight(): kotlin.Double

    val intWidth: Px
    val intHeight: Px

    operator fun plus(d: Dimension2): Dimension2

    operator fun minus(d: Dimension2): Dimension2

    // TODO times()

    override fun copy(): Dimension2


    // Vector2 implementation
    override operator fun get(index: kotlin.Int): kotlin.Double =
        when (index) {
            0 -> this.getWidth()
            1 -> this.getHeight()
            else -> throw IndexOutOfBoundsException("Dimension2 vector contains only 2 numbers!")
        }


    fun toInt(): Dimension2.Int
            = Dimension2.Int(this.intWidth, this.intHeight)

    fun toDouble(): Dimension2.Double
            = Dimension2.Double(this.getWidth(), this.getHeight())


    fun defaultEquals(other: Dimension2)
            = this.getWidth() == other.getWidth() && this.getHeight() == other.getHeight()

    fun defaultHashCode(): kotlin.Int {
        val sum = intWidth + intHeight
        return sum * (sum + 1) / 2 + intWidth
    }


    open class Int : Dimension2, Dimension {

        constructor() : super()

        constructor(width: Px, height: Px) : super(width, height)

        constructor(d: Pair<Px, Px>) : super(d.first, d.second)

        constructor(d: Dimension) : super(d)

        override val intWidth: Px
            get() = this.width
        override val intHeight: Px
            get() = this.height

        override operator fun plus(d: Dimension2): Dimension2
                = Dimension2.Double(this.width + d.getWidth(), this.height + d.getHeight())

        operator fun plus(d: Dimension2.Int): Dimension2.Int
                = this.plus(d as Dimension)

        operator fun plus(d: Dimension): Dimension2.Int
                = Dimension2.Int(this.width + d.width, this.height + d.height)

        override operator fun minus(d: Dimension2): Dimension2
                = Dimension2.Double(this.width - d.getWidth(), this.height - d.getHeight())

        operator fun minus(d: Dimension2.Int): Dimension2.Int
                = this.minus(d as Dimension)

        operator fun minus(d: Dimension): Dimension2.Int
                = Dimension2.Int(this.width - d.width, this.height - d.height)

        override fun copy(): Dimension2.Int = Dimension2.Int(this.width, this.height)

        override fun equals(other: Any?): Boolean {
            return when (other) {
                is Dimension -> super.equals(other)
                // Do not check for Dimension2D
                is Dimension2 -> super.defaultEquals(other)
                else -> false
            }
        }

        @Suppress("RemoveExplicitSuperQualifier")
        override fun hashCode(): kotlin.Int = super<Dimension>.hashCode()

    }

    open class Double(@JvmField var width: PxDouble, @JvmField var height: PxDouble) : Dimension2, Dimension2D() {

        constructor() : this(0.0, 0.0)

        constructor(d: Pair<PxDouble, PxDouble>) : this(d.first, d.second)

        constructor(d: Dimension2D) : this(d.width, d.height)

        override fun getWidth(): kotlin.Double = this.width

        override fun getHeight(): kotlin.Double = this.height

        override val intWidth: Px
            get() = this.width.toInt()
        override val intHeight: Px
            get() = this.height.toInt()

        override fun setSize(width: kotlin.Double, height: kotlin.Double) {
            this.width = width
            this.height = height
        }

        override operator fun plus(d: Dimension2): Dimension2.Double
                = Dimension2.Double(this.width + d.getWidth(), this.height + d.getHeight())

        override operator fun minus(d: Dimension2): Dimension2.Double
                = Dimension2.Double(this.width - d.getWidth(), this.height - d.getHeight())

        override fun copy(): Dimension2.Double = Dimension2.Double(this.width, this.height)

        override fun equals(other: Any?): Boolean {
            return when (other) {
                is Dimension2D -> this.getWidth() == other.width && this.getHeight() == other.height
                // Do not check for Dimension2D
                is Dimension2 -> super.defaultEquals(other)
                else -> false
            }
        }

        override fun hashCode(): kotlin.Int = super.defaultHashCode()

    }

}

// Dimension2 "constructors"

@ConstructorFunction(Dimension2::class)
fun Dimension2(): Dimension2 = Dimension2.Int()

@ConstructorFunction(Dimension2::class)
fun Dimension2(d: Dimension): Dimension2.Int = Dimension2.Int(d)

@ConstructorFunction(Dimension2::class)
fun Dimension2(d: Dimension2D): Dimension2
// Use Double per default
        = Dimension2.Double(d.width, d.height)

@ConstructorFunction(Dimension2::class)
fun Dimension2(width: Px, height: Px): Dimension2.Int = Dimension2.Int(width, height)

@ConstructorFunction(Dimension2::class)
fun Dimension2(width: PxDouble, height: PxDouble): Dimension2.Double = Dimension2.Double(width, height)


@ConstructorFunction(Dimension2::class)
fun Dimension2(d: Pair<Px, Px>): Dimension2.Int = Dimension2.Int(d)

@ConstructorFunction(Dimension2::class)
fun Dimension2(d: Pair<PxDouble, PxDouble>): Dimension2.Double = Dimension2.Double(d)


// Dimension2 converter function ONLY for the commonly used AWT Dimension class
// No converters for the abstract Dimension2D base class

fun Dimension.toDimension2(): Dimension2.Int = Dimension2.Int(this)