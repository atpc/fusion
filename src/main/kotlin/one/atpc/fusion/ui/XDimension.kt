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
interface XDimension : XVector2 {

    // Necessary to comply with Dimension2D
    fun getWidth(): kotlin.Double
    fun getHeight(): kotlin.Double

    val intWidth: Px
    val intHeight: Px

    operator fun plus(d: XDimension): XDimension

    operator fun minus(d: XDimension): XDimension

    // TODO times()

    override fun copy(): XDimension


    // XVector2 implementation
    override operator fun get(index: kotlin.Int): kotlin.Double =
        when (index) {
            0 -> this.getWidth()
            1 -> this.getHeight()
            else -> throw IndexOutOfBoundsException("XDimension vector contains only 2 numbers!")
        }


    fun toInt(): XDimension.Int
            = XDimension.Int(this.intWidth, this.intHeight)

    fun toDouble(): XDimension.Double
            = XDimension.Double(this.getWidth(), this.getHeight())


    fun defaultEquals(other: XDimension)
            = this.getWidth() == other.getWidth() && this.getHeight() == other.getHeight()

    fun defaultHashCode(): kotlin.Int {
        val sum = intWidth + intHeight
        return sum * (sum + 1) / 2 + intWidth
    }


    open class Int : XDimension, Dimension {

        constructor() : super()

        constructor(width: Px, height: Px) : super(width, height)

        constructor(d: Pair<Px, Px>) : super(d.first, d.second)

        constructor(d: Dimension) : super(d)

        override val intWidth: Px
            get() = this.width
        override val intHeight: Px
            get() = this.height

        override operator fun plus(d: XDimension): XDimension
                = XDimension.Double(this.width + d.getWidth(), this.height + d.getHeight())

        operator fun plus(d: XDimension.Int): XDimension.Int
                = this.plus(d as Dimension)

        operator fun plus(d: Dimension): XDimension.Int
                = XDimension.Int(this.width + d.width, this.height + d.height)

        override operator fun minus(d: XDimension): XDimension
                = XDimension.Double(this.width - d.getWidth(), this.height - d.getHeight())

        operator fun minus(d: XDimension.Int): XDimension.Int
                = this.minus(d as Dimension)

        operator fun minus(d: Dimension): XDimension.Int
                = XDimension.Int(this.width - d.width, this.height - d.height)

        override fun copy(): XDimension.Int = XDimension.Int(this.width, this.height)

        override fun equals(other: Any?): Boolean {
            return when (other) {
                is Dimension -> super.equals(other)
                // Do not check for Dimension2D
                is XDimension -> super.defaultEquals(other)
                else -> false
            }
        }

        @Suppress("RemoveExplicitSuperQualifier")
        override fun hashCode(): kotlin.Int = super<Dimension>.hashCode()

    }

    open class Double(@JvmField var width: PxDouble, @JvmField var height: PxDouble) : XDimension, Dimension2D() {

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

        override operator fun plus(d: XDimension): XDimension.Double
                = XDimension.Double(this.width + d.getWidth(), this.height + d.getHeight())

        override operator fun minus(d: XDimension): XDimension.Double
                = XDimension.Double(this.width - d.getWidth(), this.height - d.getHeight())

        override fun copy(): XDimension.Double = XDimension.Double(this.width, this.height)

        override fun equals(other: Any?): Boolean {
            return when (other) {
                is Dimension2D -> this.getWidth() == other.width && this.getHeight() == other.height
                // Do not check for Dimension2D
                is XDimension -> super.defaultEquals(other)
                else -> false
            }
        }

        override fun hashCode(): kotlin.Int = super.defaultHashCode()

    }

}

// XDimension "constructors"

@ConstructorFunction(XDimension::class)
fun XDimension(): XDimension = XDimension.Int()

@ConstructorFunction(XDimension::class)
fun XDimension(d: Dimension): XDimension.Int = XDimension.Int(d)

@ConstructorFunction(XDimension::class)
fun XDimension(d: Dimension2D): XDimension
// Use Double per default
        = XDimension.Double(d.width, d.height)

@ConstructorFunction(XDimension::class)
fun XDimension(width: Px, height: Px): XDimension.Int = XDimension.Int(width, height)

@ConstructorFunction(XDimension::class)
fun XDimension(width: PxDouble, height: PxDouble): XDimension.Double = XDimension.Double(width, height)


@ConstructorFunction(XDimension::class)
fun XDimension(d: Pair<Px, Px>): XDimension.Int = XDimension.Int(d)

@ConstructorFunction(XDimension::class)
fun XDimension(d: Pair<PxDouble, PxDouble>): XDimension.Double = XDimension.Double(d)


// XDimension converter function ONLY for the commonly used AWT Dimension class
// No converters for the abstract Dimension2D base class

fun Dimension.toXDimension(): XDimension.Int = XDimension.Int(this)