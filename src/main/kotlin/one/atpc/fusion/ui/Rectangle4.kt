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
interface Rectangle4 : Vector4 {

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
            else -> throw IndexOutOfBoundsException("Rectangle4 vector contains only 4 numbers!")
        }

    // (No intX/intY/intWidth/[...] methods) //

    fun getLocation(): Point2

    fun getSize(): Dimension2


    operator fun contains(p: Point2): Boolean

    operator fun contains(r: Rectangle4): Boolean


    override fun copy(): Rectangle4


    // This MUST create a copy, even if it is the Rectangle4.Int class
    fun toInt(): Rectangle4.Int = Rectangle4.Int(
        this.getX().toInt(),
        this.getY().toInt(),
        this.getWidth().toInt(),
        this.getHeight().toInt()
    )

    fun toDouble(): Rectangle4.Double = Rectangle4.Double(this.getX(), this.getY(), this.getWidth(), this.getHeight())


    open class Int : Rectangle4, Rectangle {

        constructor() : super()

        constructor(rect: Rectangle) : super(rect)

        constructor(x: Px, y: Px, width: Px, height: Px) : super(x, y, width, height)

        constructor(width: Px, height: Px) : super(width, height)

        constructor(point: Point, dimen: Dimension) : super(point, dimen)

        constructor(p: Point) : super(p)

        constructor(d: Dimension) : super(d)


        override fun getLocation(): Point2.Int = Point2.Int(this.x, this.y)

        override fun getSize(): Dimension2.Int = Dimension2.Int(this.width, this.height)


        override operator fun contains(p: Point2): Boolean
                =
            if (p is Point)
                this.contains(p as Point)
            else
                this.contains(p.getX(), p.getY())


        override operator fun contains(r: Rectangle4): Boolean
                =
            if (r is Rectangle)
                this.contains(r as Rectangle)
            else
                this.contains(r.getX(), r.getY(), r.getWidth(), r.getHeight())


        override fun copy(): Rectangle4.Int = Rectangle4.Int(this)

        override fun toInt(): Rectangle4.Int = Rectangle4.Int(
            this.x,
            this.y,
            this.width,
            this.height
        )

    }


    open class Double : Rectangle4, Rectangle2D.Double {

        constructor() : super()

        constructor(rect: Rectangle2D) : super(rect.x, rect.y, rect.width, rect.height)

        constructor(x: PxDouble, y: PxDouble, width: PxDouble, height: PxDouble) : super(x, y, width, height)

        constructor(width: PxDouble, height: PxDouble) : this(0.0, 0.0, width, height)

        constructor(point: Point2D, dimen: Dimension2D) : this(point.x, point.y, dimen.width, dimen.height)


        override fun getLocation(): Point2.Double = Point2.Double(this.x, this.y)

        override fun getSize(): Dimension2.Double = Dimension2.Double(this.width, this.height)


        override operator fun contains(p: Point2): Boolean = this.contains(p.getX(), p.getY())

        override operator fun contains(r: Rectangle4): Boolean = this.contains(r.getX(), r.getY(), r.getWidth(), r.getHeight())


        override fun copy(): Rectangle4.Double = Rectangle4.Double(this)

    }

}


// Constructor functions

@ConstructorFunction(Rectangle4::class)
fun Rectangle4(): Rectangle4 = Rectangle4.Int()


@ConstructorFunction(Rectangle4::class)
fun Rectangle4(rect: Rectangle): Rectangle4.Int = Rectangle4.Int(rect)

@ConstructorFunction(Rectangle4::class)
fun Rectangle4(rect: Rectangle2D): Rectangle4.Double = Rectangle4.Double(rect)


@ConstructorFunction(Rectangle4::class)
fun Rectangle4(x: Px, y: Px, width: Px, height: Px): Rectangle4.Int = Rectangle4.Int(x, y, width, height)

@ConstructorFunction(Rectangle4::class)
fun Rectangle4(width: Px, height: Px): Rectangle4.Int = Rectangle4.Int(width, height)

@ConstructorFunction(Rectangle4::class)
fun Rectangle4(point: Point, dimen: Dimension): Rectangle4.Int = Rectangle4.Int(point, dimen)


@ConstructorFunction(Rectangle4::class)
fun Rectangle4(x: PxDouble, y: PxDouble, width: PxDouble, height: PxDouble): Rectangle4.Double
        = Rectangle4.Double(x, y, width, height)

@ConstructorFunction(Rectangle4::class)
fun Rectangle4(width: PxDouble, height: PxDouble): Rectangle4.Double = Rectangle4.Double(width, height)

@ConstructorFunction(Rectangle4::class)
fun Rectangle4(point: Point2D, dimen: Dimension2D): Rectangle4.Double = Rectangle4.Double(point, dimen)


// Only for AWT Rectangle class:

fun Rectangle.toRectangle4(): Rectangle4.Int = Rectangle4.Int(this.x, this.y, this.width, this.height)