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
import java.awt.Point
import java.awt.geom.Point2D

// Immutable
interface XPoint : XVector2 {

    // Necessary to comply with Point2D
    fun getX(): PxDouble
    fun getY(): PxDouble

    val intX: Px
    val intY: Px

    override operator fun get(index: kotlin.Int) =
        when(index) {
            0 -> this.getX()
            1 -> this.getY()
            else -> throw IndexOutOfBoundsException("XPoint vector only contains 2 numbers!")
        }

    operator fun plus(p: XPoint): XPoint

    operator fun minus(p: XPoint): XPoint

    fun toInt(): XPoint.Int
            = XPoint.Int(this.intX, this.intY)

    fun toDouble(): XPoint.Double
            = XPoint.Double(this.getX(), this.getY())

    override fun copy(): XPoint


    // Equals is built-in to the Point2D subtypes of XPoint

    open class Int : XPoint, Point {

        constructor() : super()

        constructor(x: Px, y: Px) : super(x, y)

        constructor(p: Pair<Px, Px>) : super(p.first, p.second)

        constructor(p: Point) : super(p)

        override val intX: Px
            get() = this.x
        override val intY: Px
            get() = this.y


        override operator fun plus(p: XPoint): XPoint
                = when (p) {
            is XPoint.Int -> this.plus(p)
            is XPoint.Double -> this.plus(p)
            else -> XPoint(this.getX() + p.getX(), this.getY() + p.getY())
        }

        override operator fun minus(p: XPoint): XPoint
                = when (p) {
            is XPoint.Int -> this.minus(p)
            is XPoint.Double -> this.minus(p)
            else -> XPoint(this.getX() - p.getX(), this.getY() - p.getY())
        }


        operator fun plus(p: XPoint.Int): XPoint.Int
                = XPoint.Int(this.x + p.x, this.y + p.y)


        operator fun plus(p: XPoint.Double): XPoint.Double
                = XPoint.Double(this.x + p.x, this.y + p.y)


        operator fun minus(p: XPoint.Int): XPoint.Int
                = XPoint.Int(this.x - p.x, this.y - p.y)

        operator fun minus(p: XPoint.Double): XPoint.Double
                = XPoint.Double(this.x - p.x, this.y - p.y)


        override fun copy(): XPoint.Int = XPoint.Int(this)

    }

    open class Double : XPoint, Point2D.Double {

        constructor() : super()

        constructor(x: PxDouble, y: PxDouble) : super(x, y)

        constructor(p: Pair<PxDouble, PxDouble>) : super(p.first, p.second)

        constructor(p: Point2D.Double) : super(p.x, p.y)

        override val intX: Px
            get() = this.x.toInt()
        override val intY: Px
            get() = this.y.toInt()


        override operator fun plus(p: XPoint): XPoint.Double
                = XPoint.Double(this.x + p.getX(), this.y + p.getY())


        override operator fun minus(p: XPoint): XPoint.Double
                = XPoint.Double(this.x - p.getX(), this.y - p.getY())


        override fun copy(): XPoint.Double = XPoint.Double(this)

    }

}


@ConstructorFunction(XPoint::class)
fun XPoint(): XPoint = XPoint.Int()


@ConstructorFunction(XPoint::class)
fun XPoint(p: Point): XPoint.Int = XPoint.Int(p)

@ConstructorFunction(XPoint::class)
fun XPoint(p: Point2D.Double): XPoint.Double = XPoint.Double(p)

@ConstructorFunction(XPoint::class)
fun XPoint(p: Point2D): XPoint = XPoint.Double(p.x, p.y)


@ConstructorFunction(XPoint::class)
fun XPoint(x: Px, y: Px): XPoint.Int = XPoint.Int(x, y)

@ConstructorFunction(XPoint::class)
fun XPoint(x: PxDouble, y: PxDouble): XPoint.Double = XPoint.Double(x, y)


@ConstructorFunction(XPoint::class)
fun XPoint(p: Pair<Px, Px>): XPoint.Int = XPoint.Int(p)

@ConstructorFunction(XPoint::class)
fun XPoint(p: Pair<PxDouble, PxDouble>): XPoint.Double = XPoint.Double(p)


// Converter function ONLY for the commonly used AWT point class

fun Point.toXPoint(): XPoint.Int = XPoint.Int(this)