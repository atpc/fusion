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
import java.awt.Point
import java.awt.Rectangle
import java.awt.geom.Dimension2D
import java.awt.geom.Point2D
import java.awt.geom.Rectangle2D

// TODO Create tests
interface XRectangle : XVector4 {

    fun getX(): PxDouble
    fun getY(): PxDouble
    fun getWidth(): PxDouble
    fun getHeight(): PxDouble

    override fun get(index: kotlin.Int): kotlin.Double
            =
        when(index) {
            0 -> this.getX()
            1 -> this.getY()
            2 -> this.getWidth()
            3 -> this.getHeight()
            else -> throw IndexOutOfBoundsException("XRectangle vector contains only 4 numbers!")
        }

    // (No intX/intY/intWidth/[...] methods) //

    fun getLocation(): XPoint

    fun getSize(): XDimension


    operator fun contains(p: XPoint): Boolean

    operator fun contains(r: XRectangle): Boolean


    override fun copy(): XRectangle


    // This MUST create a copy, even if it is the XRectangle.Int class
    fun toInt(): XRectangle.Int = XRectangle.Int(
        this.getX().toInt(),
        this.getY().toInt(),
        this.getWidth().toInt(),
        this.getHeight().toInt()
    )

    fun toDouble(): XRectangle.Double = XRectangle.Double(this.getX(), this.getY(), this.getWidth(), this.getHeight())


    open class Int : XRectangle, Rectangle {

        constructor() : super()

        constructor(rect: Rectangle) : super(rect)

        constructor(x: Px, y: Px, width: Px, height: Px) : super(x, y, width, height)

        constructor(width: Px, height: Px) : super(width, height)

        constructor(point: Point, dimen: Dimension) : super(point, dimen)

        constructor(p: Point) : super(p)

        constructor(d: Dimension) : super(d)


        override fun getLocation(): XPoint.Int = XPoint.Int(this.x, this.y)

        override fun getSize(): XDimension.Int = XDimension.Int(this.width, this.height)


        override operator fun contains(p: XPoint): Boolean
                =
            if (p is Point)
                this.contains(p as Point)
            else
                this.contains(p.getX(), p.getY())


        override operator fun contains(r: XRectangle): Boolean
                =
            if (r is Rectangle)
                this.contains(r as Rectangle)
            else
                this.contains(r.getX(), r.getY(), r.getWidth(), r.getHeight())


        override fun copy(): XRectangle.Int = XRectangle.Int(this)

        override fun toInt(): XRectangle.Int = XRectangle.Int(
            this.x,
            this.y,
            this.width,
            this.height
        )

    }


    open class Double : XRectangle, Rectangle2D.Double {

        constructor() : super()

        constructor(rect: Rectangle2D) : super(rect.x, rect.y, rect.width, rect.height)

        constructor(x: PxDouble, y: PxDouble, width: PxDouble, height: PxDouble) : super(x, y, width, height)

        constructor(width: PxDouble, height: PxDouble) : this(0.0, 0.0, width, height)

        constructor(point: Point2D, dimen: Dimension2D) : this(point.x, point.y, dimen.width, dimen.height)


        override fun getLocation(): XPoint.Double = XPoint.Double(this.x, this.y)

        override fun getSize(): XDimension.Double = XDimension.Double(this.width, this.height)


        override operator fun contains(p: XPoint): Boolean = this.contains(p.getX(), p.getY())

        override operator fun contains(r: XRectangle): Boolean = this.contains(r.getX(), r.getY(), r.getWidth(), r.getHeight())


        override fun copy(): XRectangle.Double = XRectangle.Double(this)

    }

}


// Constructor functions

@ConstructorFunction(XRectangle::class)
fun XRectangle(): XRectangle = XRectangle.Int()


@ConstructorFunction(XRectangle::class)
fun XRectangle(rect: Rectangle): XRectangle.Int = XRectangle.Int(rect)

@ConstructorFunction(XRectangle::class)
fun XRectangle(rect: Rectangle2D): XRectangle.Double = XRectangle.Double(rect)


@ConstructorFunction(XRectangle::class)
fun XRectangle(x: Px, y: Px, width: Px, height: Px): XRectangle.Int = XRectangle.Int(x, y, width, height)

@ConstructorFunction(XRectangle::class)
fun XRectangle(width: Px, height: Px): XRectangle.Int = XRectangle.Int(width, height)

@ConstructorFunction(XRectangle::class)
fun XRectangle(point: Point, dimen: Dimension): XRectangle.Int = XRectangle.Int(point, dimen)


@ConstructorFunction(XRectangle::class)
fun XRectangle(x: PxDouble, y: PxDouble, width: PxDouble, height: PxDouble): XRectangle.Double
        = XRectangle.Double(x, y, width, height)

@ConstructorFunction(XRectangle::class)
fun XRectangle(width: PxDouble, height: PxDouble): XRectangle.Double = XRectangle.Double(width, height)

@ConstructorFunction(XRectangle::class)
fun XRectangle(point: Point2D, dimen: Dimension2D): XRectangle.Double = XRectangle.Double(point, dimen)


// Only for AWT Rectangle class:

fun Rectangle.toXRectangle(): XRectangle.Int = XRectangle.Int(this.x, this.y, this.width, this.height)