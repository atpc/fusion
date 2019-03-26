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

import java.awt.color.ColorSpace

open class Color : java.awt.Color {
    // TODO Use genuine algorithms to construct colors from Double instead of float conversion
    companion object {

        // New constructor: Use the unsigned byte values directly, thanks to Kotlin unsigned types
        @JvmStatic
        fun rgb(r: UByte, g: UByte, b: UByte): one.atpc.fusion.ui.Color = Color.rgb(r.toInt(), g.toInt(), b.toInt())

        @JvmStatic
        fun rgb(r: Int, g: Int, b: Int): one.atpc.fusion.ui.Color = one.atpc.fusion.ui.Color(r, g, b)

        @JvmStatic
        fun rgb(r: Float, g: Float, b: Float): one.atpc.fusion.ui.Color = one.atpc.fusion.ui.Color(r, g, b)

        @JvmStatic
        fun rgb(r: Double, g: Double, b: Double): one.atpc.fusion.ui.Color =
            one.atpc.fusion.ui.Color(r.toFloat(), g.toFloat(), b.toFloat())

        @JvmStatic
        fun rgb(rgb: Int): one.atpc.fusion.ui.Color = one.atpc.fusion.ui.Color(rgb)

        @JvmStatic
        fun rgb(rgb: UInt): one.atpc.fusion.ui.Color = one.atpc.fusion.ui.Color(rgb.toInt())


        @JvmStatic
        fun rgba(r: UByte, g: UByte, b: UByte, a: UByte) = Color.rgba(r.toInt(), g.toInt(), b.toInt(), a.toInt())

        @JvmStatic
        fun rgba(r: Int, g: Int, b: Int, a: Int): one.atpc.fusion.ui.Color = one.atpc.fusion.ui.Color(r, g, b, a)

        @JvmStatic
        fun rgba(r: Float, g: Float, b: Float, a: Float): one.atpc.fusion.ui.Color = one.atpc.fusion.ui.Color(r, g, b, a)

        @JvmStatic
        fun rgba(r: Double, g: Double, b: Double, a: Double): one.atpc.fusion.ui.Color =
            one.atpc.fusion.ui.Color(r.toFloat(), g.toFloat(), b.toFloat(), a.toFloat())

        @JvmStatic
        fun rgba(rgba: UInt): one.atpc.fusion.ui.Color = Color(rgba.toInt(), hasAlpha = true)


        /**
         * Creates a `Color` object based on the specified values
         * for the HSV (HSB) color model.
         *
         * The [s] and [v] components should be
         * floating-point values between zero and one
         * (numbers in the range `0..1`).  The [h] component
         * can be any floating-point number.  The floor of this number is
         * subtracted from it to create a fraction between `0` and `1`.  This
         * fractional number is then multiplied by `360` to produce the hue
         * angle in the HSV color model.
         *
         * @param h The hue component of the color.
         * @param s The saturation of the color (`0..1`).
         * @param v The value (brightness) of the color (`0..1`).
         * @return  A `Color` object with the specified hue,
         *                                 saturation, and value.
         */
        @JvmStatic
        fun hsv(h: Float, s: Float, v: Float): one.atpc.fusion.ui.Color = Color.rgb(HSBtoRGB(h, s, v))

        @JvmStatic
        fun hsv(h: Double, s: Double, v: Double): one.atpc.fusion.ui.Color = hsv(h.toFloat(), s.toFloat(), v.toFloat())


        @JvmStatic
        fun hsva(h: Float, s: Float, v: Float, a: Float): one.atpc.fusion.ui.Color {
            if (a < 0.0f || a > 1.0f)
                throw IllegalArgumentException("Color parameter outside of expected range: Alpha")

            // TODO FIX shifting
            return Color.rgba(
                (((a * 255 + 0.5).toUInt() shl 24) or (HSBtoRGB(h, s, v).toUInt() and 0x00_FF_FF_FFU))
            )
        }

        @JvmStatic
        fun hsva(h: Double, s: Double, v: Double, a: Double): one.atpc.fusion.ui.Color = this.hsva(h.toFloat(), s.toFloat(), v.toFloat(), a.toFloat())

        @JvmField
        val white = Color.rgb(0xffffffu)
        @JvmField
        val black = Color.rgb(0x000000u)

    }


    protected constructor(r: Int, g: Int, b: Int) : super(r, g, b)

    protected constructor(r: Int, g: Int, b: Int, a: Int) : super(r, g, b, a)

    protected constructor(rgb: Int) : super(rgb)

    protected constructor(rgba: Int, hasAlpha: Boolean) : super(rgba, hasAlpha)

    protected constructor(r: Float, g: Float, b: Float) : super(r, g, b)

    protected constructor(r: Float, g: Float, b: Float, a: Float) : super(r, g, b, a)

    constructor(cspace: ColorSpace, components: FloatArray, alpha: Float) : super(cspace, components, alpha)


    /**
     * Returns the RGBA value representing the color in the default `sRGB`
     * [java.awt.image.ColorModel].
     * (Bits `24-31` are `alpha`, `16-23` are `red`, `8-15` are `green`, `0-7` are
     * `blue`).
     * Hex representation: `0xAARRGGBB`.
     *
     * @return The RGBA value of the color in the default `sRGB`
     *         `ColorModel`.
     * @see java.awt.image.ColorModel.getRGBdefault
     * @see getRed
     * @see getGreen
     * @see getBlue
     */
    val rgba: UInt get() = super.getRGB().toUInt()

    @Deprecated(message = "getRGB() is ambiguous. Use rgba property instead.", replaceWith = ReplaceWith("rgba"))
    override fun getRGB(): Int = super.getRGB()


    fun getRGBComponents(): FloatArray = this.getRGBComponents(null)

    fun getRGBColorComponents(): FloatArray = this.getRGBColorComponents(null)

    fun deriveAlpha(alpha: Float): one.atpc.fusion.ui.Color {
        if (alpha.isNaN())
            throw IllegalArgumentException("Illegal alpha value: $alpha!")

        var rgb = FloatArray(3)
        rgb = this.getRGBColorComponents(rgb)
        return Color.rgba(rgb[0], rgb[1], rgb[2], alpha)
    }

    fun deriveAlpha(alpha: Double): one.atpc.fusion.ui.Color = this.deriveAlpha(alpha.toFloat())

    fun deriveAlpha(alpha: Int): one.atpc.fusion.ui.Color = one.atpc.fusion.ui.Color(red, green, blue, alpha)

    fun deriveAlpha(alpha: UByte): one.atpc.fusion.ui.Color = this.deriveAlpha(alpha.toInt())


    override fun toString(): String
            = "${javaClass.typeName}[r=$red,g=$green,b=$blue,a=$alpha]"

}


private fun Double.toUInt() = this.toLong().toUInt()