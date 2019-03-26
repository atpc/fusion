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

    @Px
    fun getX(): kotlin.Double
    @Px
    fun getY(): kotlin.Double
    @Px
    fun getWidth(): kotlin.Double
    @Px
    fun getHeight(): kotlin.Double

    override fun get(index: kotlin.Int): kotlin.Double
            =
        when(index) {
            0 -> this.getX()
            1 -> this.getY()
            2 -> this.getWidth()
            3 -> this.getHeight()
            else -> throw IndexOutOfBoundsException("Rectangle4 vector contains only 4 numbers!")
        }

    // (No intX/intY/intWidth/[...] properties) //

    fun getLocation(): Point2

    fun getSize(): Dimension2


    operator fun contains(p: Point2): Boolean

    operator fun contains(r: Rectangle4): Boolean


    override fun copy(): Rectangle4


    override fun vmap(transform: (kotlin.Double) -> kotlin.Double): Rectangle4.Double = Rectangle4.Double(
        transform(getX()),
        transform(getY()),
        transform(getWidth()),
        transform(getHeight())
    )

    override fun vmapIndexed(transform: (index: kotlin.Int, kotlin.Double) -> kotlin.Double): Rectangle4.Double
            = Rectangle4.Double(
        transform(0, getX()),
        transform(1, getY()),
        transform(2, getWidth()),
        transform(3, getHeight())
    )


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

        constructor(@Px x: kotlin.Int, @Px y: kotlin.Int, @Px width: kotlin.Int, @Px height: kotlin.Int) : super(x, y, width, height)

        constructor(@Px width: kotlin.Int, @Px height: kotlin.Int) : super(width, height)

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

        override fun toList(): List<kotlin.Int> = listOf(
            this.x,
            this.y,
            this.width,
            this.height
        )


        inline fun vmap0(f: (kotlin.Int) -> kotlin.Int): Rectangle4.Int = Rectangle4.Int(
            f(x),
            f(y),
            f(width),
            f(height)
        )

        inline fun vmapIndexed0(transform: (index: kotlin.Int, kotlin.Int) -> kotlin.Int): Rectangle4.Int
                = Rectangle4.Int(
            transform(0, x),
            transform(1, y),
            transform(2, width),
            transform(3, height)
        )

    }


    open class Double : Rectangle4, Rectangle2D.Double {

        constructor() : super()

        constructor(rect: Rectangle2D) : super(rect.x, rect.y, rect.width, rect.height)

        constructor(@Px x: kotlin.Double, @Px y: kotlin.Double, @Px width: kotlin.Double, @Px height: kotlin.Double) : super(x, y, width, height)

        constructor(@Px width: kotlin.Double, @Px height: kotlin.Double) : this(0.0, 0.0, width, height)

        constructor(point: Point2D, dimen: Dimension2D) : this(point.x, point.y, dimen.width, dimen.height)


        override fun getLocation(): Point2.Double = Point2.Double(this.x, this.y)

        override fun getSize(): Dimension2.Double = Dimension2.Double(this.width, this.height)


        override operator fun contains(p: Point2): Boolean = this.contains(p.getX(), p.getY())

        override operator fun contains(r: Rectangle4): Boolean = this.contains(r.getX(), r.getY(), r.getWidth(), r.getHeight())


        override fun copy(): Rectangle4.Double = Rectangle4.Double(this)


        override fun toList(): List<kotlin.Double> = listOf(
            this.x,
            this.y,
            this.width,
            this.height
        )


        override fun vmap(transform: (kotlin.Double) -> kotlin.Double): Rectangle4.Double = this.vmap0(transform)

        inline fun vmap0(f: (kotlin.Double) -> kotlin.Double): Rectangle4.Double = Rectangle4.Double(
            f(x),
            f(y),
            f(width),
            f(height)
        )

        override fun vmapIndexed(transform: (index: kotlin.Int, kotlin.Double) -> kotlin.Double): Rectangle4.Double
                = vmapIndexed0(transform)

        inline fun vmapIndexed0(transform: (index: kotlin.Int, kotlin.Double) -> kotlin.Double): Rectangle4.Double
                = Rectangle4.Double(
            transform(0, x),
            transform(1, y),
            transform(2, width),
            transform(3, height)
        )

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
fun Rectangle4(@Px x: kotlin.Int, @Px y: kotlin.Int, @Px width: kotlin.Int, @Px height: kotlin.Int): Rectangle4.Int
        = Rectangle4.Int(x, y, width, height)

@ConstructorFunction(Rectangle4::class)
fun Rectangle4(@Px width: kotlin.Int, @Px height: kotlin.Int): Rectangle4.Int = Rectangle4.Int(width, height)

@ConstructorFunction(Rectangle4::class)
fun Rectangle4(point: Point, dimen: Dimension): Rectangle4.Int = Rectangle4.Int(point, dimen)


@ConstructorFunction(Rectangle4::class)
fun Rectangle4(@Px x: kotlin.Double, @Px y: kotlin.Double, @Px width: kotlin.Double, @Px height: kotlin.Double): Rectangle4.Double
        = Rectangle4.Double(x, y, width, height)

@ConstructorFunction(Rectangle4::class)
fun Rectangle4(@Px width: kotlin.Double, @Px height: kotlin.Double): Rectangle4.Double = Rectangle4.Double(width, height)

@ConstructorFunction(Rectangle4::class)
fun Rectangle4(point: Point2D, dimen: Dimension2D): Rectangle4.Double = Rectangle4.Double(point, dimen)


// Only for AWT Rectangle class:

fun Rectangle.toRectangle4(): Rectangle4.Int = Rectangle4.Int(this.x, this.y, this.width, this.height)