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
interface Point2 : Vector2 {

    // Necessary to comply with Point2D
    @Px
    fun getX(): kotlin.Double
    @Px
    fun getY(): kotlin.Double

    @Px
    val intX: kotlin.Int
    @Px
    val intY: kotlin.Int

    override operator fun get(index: kotlin.Int) =
        when(index) {
            0 -> this.getX()
            1 -> this.getY()
            else -> throw IndexOutOfBoundsException("Point2 vector only contains 2 numbers!")
        }

    operator fun plus(p: Point2): Point2

    operator fun minus(p: Point2): Point2

    fun toInt(): Point2.Int
            = Point2.Int(this.intX, this.intY)

    fun toDouble(): Point2.Double
            = Point2.Double(this.getX(), this.getY())

    override fun copy(): Point2


    override fun vmap(transform: (kotlin.Double) -> kotlin.Double): Point2.Double = Point2.Double(
        transform(getX()),
        transform(getY())
    )

    override fun vmapIndexed(transform: (index: kotlin.Int, kotlin.Double) -> kotlin.Double): Point2.Double
            = Point2.Double(
        transform(0, getX()),
        transform(1, getY())
    )


    // Equals is built-in to the Point2D subtypes of Point2

    open class Int : Point2, Point {

        constructor() : super()

        constructor(@Px x: kotlin.Int, @Px y: kotlin.Int) : super(x, y)

        constructor(@Px p: Pair<kotlin.Int, kotlin.Int>) : super(p.first, p.second)

        constructor(p: Point) : super(p)

        @Px
        override val intX: kotlin.Int
            get() = this.x
        @Px
        override val intY: kotlin.Int
            get() = this.y


        override operator fun plus(p: Point2): Point2
                = when (p) {
            is Point2.Int -> this.plus(p)
            is Point2.Double -> this.plus(p)
            else -> Point2(this.getX() + p.getX(), this.getY() + p.getY())
        }

        override operator fun minus(p: Point2): Point2
                = when (p) {
            is Point2.Int -> this.minus(p)
            is Point2.Double -> this.minus(p)
            else -> Point2(this.getX() - p.getX(), this.getY() - p.getY())
        }


        operator fun plus(p: Point2.Int): Point2.Int
                = Point2.Int(this.x + p.x, this.y + p.y)


        operator fun plus(p: Point2.Double): Point2.Double
                = Point2.Double(this.x + p.x, this.y + p.y)


        operator fun minus(p: Point2.Int): Point2.Int
                = Point2.Int(this.x - p.x, this.y - p.y)

        operator fun minus(p: Point2.Double): Point2.Double
                = Point2.Double(this.x - p.x, this.y - p.y)


        override fun copy(): Point2.Int = Point2.Int(this)


        override fun toList(): List<kotlin.Int> = listOf(
            this.x,
            this.y
        )

        override fun toPair(): Pair<kotlin.Int, kotlin.Int> = Pair(
            this.x,
            this.y
        )


        inline fun vmap0(f: (kotlin.Int) -> kotlin.Int): Point2.Int = Point2.Int(
            f(x),
            f(y)
        )

        inline fun vmapIndexed0(transform: (index: kotlin.Int, kotlin.Int) -> kotlin.Int): Point2.Int = Point2.Int(
            transform(0, x),
            transform(1, y)
        )

    }

    open class Double : Point2, Point2D.Double {

        constructor() : super()

        constructor(@Px x: kotlin.Double, @Px y: kotlin.Double) : super(x, y)

        constructor(@Px p: Pair<kotlin.Double, kotlin.Double>) : super(p.first, p.second)

        constructor(p: Point2D.Double) : super(p.x, p.y)

        @Px
        override val intX: kotlin.Int
            get() = this.x.toInt()
        @Px
        override val intY: kotlin.Int
            get() = this.y.toInt()


        override operator fun plus(p: Point2): Point2.Double
                = Point2.Double(this.x + p.getX(), this.y + p.getY())


        override operator fun minus(p: Point2): Point2.Double
                = Point2.Double(this.x - p.getX(), this.y - p.getY())


        override fun copy(): Point2.Double = Point2.Double(this)


        override fun toList(): List<kotlin.Double> = listOf(
            this.x,
            this.y
        )

        override fun toPair(): Pair<kotlin.Double, kotlin.Double> = Pair(
            this.x,
            this.y
        )


        override fun vmap(transform: (kotlin.Double) -> kotlin.Double): Point2.Double = this.vmap0(transform)

        // vmap0 is still relevant since it can be inlined
        inline fun vmap0(f: (kotlin.Double) -> kotlin.Double): Point2.Double = Point2.Double(
            f(x),
            f(y)
        )

        override fun vmapIndexed(transform: (index: kotlin.Int, kotlin.Double) -> kotlin.Double): Point2.Double
                = vmapIndexed0(transform)

        inline fun vmapIndexed0(transform: (index: kotlin.Int, kotlin.Double) -> kotlin.Double): Point2.Double
                = Point2.Double(
            transform(0, x),
            transform(1, y)
        )

    }

}


@ConstructorFunction(Point2::class)
fun Point2(): Point2 = Point2.Int()


@ConstructorFunction(Point2::class)
fun Point2(p: Point): Point2.Int = Point2.Int(p)

@ConstructorFunction(Point2::class)
fun Point2(p: Point2D.Double): Point2.Double = Point2.Double(p)

@ConstructorFunction(Point2::class)
fun Point2(p: Point2D): Point2 = Point2.Double(p.x, p.y)


@ConstructorFunction(Point2::class)
fun Point2(@Px x: kotlin.Int, @Px y: kotlin.Int): Point2.Int = Point2.Int(x, y)

@ConstructorFunction(Point2::class)
fun Point2(@Px x: kotlin.Double, @Px y: kotlin.Double): Point2.Double = Point2.Double(x, y)


@ConstructorFunction(Point2::class)
fun Point2(@Px p: Pair<kotlin.Int, kotlin.Int>): Point2.Int = Point2.Int(p)

@ConstructorFunction(Point2::class)
fun Point2(@Px p: Pair<kotlin.Double, kotlin.Double>): Point2.Double = Point2.Double(p)


// Converter function ONLY for the commonly used AWT point class

fun Point.toPoint2(): Point2.Int = Point2.Int(this)