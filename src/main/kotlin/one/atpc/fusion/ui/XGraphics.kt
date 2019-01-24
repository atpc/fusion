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

import java.awt.*
import java.awt.font.FontRenderContext
import java.awt.font.GlyphVector
import java.awt.geom.AffineTransform
import java.awt.image.BufferedImage
import java.awt.image.BufferedImageOp
import java.awt.image.ImageObserver
import java.awt.image.RenderedImage
import java.awt.image.renderable.RenderableImage
import java.text.AttributedCharacterIterator

/**
 * Converts the given graphics instance to an instance of [XGraphics].
 *
 * @return The converted `XGraphics` instance.
 * @author Thomas Orlando
 */
fun <T : Graphics> T.toXGraphics(): XGraphics
        = if (this is XGraphics) this else XGraphics(this)

/**
 * A modified Graphics object with an emphasis on rendering quality.
 * <br>
 * To create an instance of `XGraphics`, it has to be converted from
 * an instance of `Graphics` by calling [Graphics.toXGraphics].
 *
 * @author Thomas Orlando
 */
open class XGraphics internal constructor(private val g2d: Graphics2D) : Graphics2D() {

    internal constructor(g: Graphics) : this(
        if (g is Graphics2D) g else throw ClassCastException("Graphics object is not subclass of Graphics2D!")
    )

    init {
        // Set smooth RenderingHints
        val rh = this.renderingHints
        rh[RenderingHints.KEY_ANTIALIASING]        = RenderingHints.VALUE_ANTIALIAS_ON
        rh[RenderingHints.KEY_TEXT_ANTIALIASING]   = RenderingHints.VALUE_TEXT_ANTIALIAS_ON
        rh[RenderingHints.KEY_ALPHA_INTERPOLATION] = RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY
        rh[RenderingHints.KEY_RENDERING]           = RenderingHints.VALUE_RENDER_QUALITY
        rh[RenderingHints.KEY_COLOR_RENDERING]     = RenderingHints.VALUE_COLOR_RENDER_QUALITY
        // Do not touch interpolation, fractionalmetrics, stroke control, contrast, dithering
        this.setRenderingHints(rh)
    }

    override fun getClipBounds(): Rectangle = g2d.clipBounds

    override fun drawPolyline(xPoints: IntArray?, yPoints: IntArray?, nPoints: Int)
            = g2d.drawPolyline(xPoints, yPoints, nPoints)

    override fun rotate(theta: PxDouble) = g2d.rotate(theta)

    override fun rotate(theta: PxDouble, x: PxDouble, y: PxDouble) = g2d.rotate(theta, x, y)

    override fun drawLine(x1: Px, y1: Px, x2: Px, y2: Px) = g2d.drawLine(x1, y1, x2, y2)

    override fun copyArea(x: Px, y: Px, width: Px, height: Px, dx: Px, dy: Px)
            = g2d.copyArea(x, y, width, height, dx, dy)

    override fun draw(s: Shape?) = g2d.draw(s)

    override fun setStroke(s: Stroke?) = g2d.setStroke(s)

    override fun getComposite(): Composite = g2d.composite

    override fun fillArc(x: Px, y: Px, width: Px, height: Px, startAngle: Int, arcAngle: Int)
            = g2d.fillArc(x, y, width, height, startAngle, arcAngle)

    override fun fill(s: Shape?) = g2d.fill(s)

    override fun getDeviceConfiguration(): GraphicsConfiguration = g2d.deviceConfiguration

    override fun getBackground(): java.awt.Color = g2d.background

    override fun clip(s: Shape?) = g2d.clip(s)

    override fun setPaint(paint: Paint?) = g2d.setPaint(paint)

    override fun drawString(str: String?, x: Px, y: Px) = g2d.drawString(str, x, y)

    override fun drawString(str: String?, x: Float, y: Float) = g2d.drawString(str, x, y)

    fun drawString(str: String?, x: PxDouble, y: PxDouble) = this.drawString(str, x.toFloat(), y.toFloat())

    override fun drawString(iterator: AttributedCharacterIterator?, x: Px, y: Px)
            = g2d.drawString(iterator, x, y)

    override fun drawString(iterator: AttributedCharacterIterator?, x: Float, y: Float)
            = g2d.drawString(iterator, x, y)

    fun drawString(iterator: AttributedCharacterIterator?, x: PxDouble, y: PxDouble)
            = this.drawString(iterator, x.toFloat(), y.toFloat())

    override fun clipRect(x: Px, y: Px, width: Px, height: Px) = g2d.clipRect(x, y, width, height)

    override fun shear(shx: PxDouble, shy: PxDouble) = g2d.shear(shx, shy)

    override fun transform(Tx: AffineTransform?) = g2d.transform(Tx)

    override fun setPaintMode() = g2d.setPaintMode()

    override fun getColor(): java.awt.Color = g2d.color

    override fun scale(sx: PxDouble, sy: PxDouble) = g2d.scale(sx, sy)

    override fun drawImage(img: Image?, xform: AffineTransform?, obs: ImageObserver?): Boolean
            = g2d.drawImage(img, xform, obs)

    override fun drawImage(img: BufferedImage?, op: BufferedImageOp?, x: Px, y: Px)
            = g2d.drawImage(img, op, x, y)

    override fun drawImage(img: Image?, x: Px, y: Px, observer: ImageObserver?): Boolean
            = g2d.drawImage(img, x, y, observer)

    override fun drawImage(img: Image?, x: Px, y: Px, width: Px, height: Px, observer: ImageObserver?): Boolean
            = g2d.drawImage(img, x, y, width, height, observer)

    override fun drawImage(img: Image?, x: Px, y: Px, bgcolor: java.awt.Color?, observer: ImageObserver?): Boolean
            = g2d.drawImage(img, x, y, bgcolor, observer)

    override fun drawImage(
        img: Image?,
        x: Px,
        y: Px,
        width: Px,
        height: Px,
        bgcolor: java.awt.Color?,
        observer: ImageObserver?
    ): Boolean
            = g2d.drawImage(img, x, y, width, height, bgcolor, observer)

    override fun drawImage(
        img: Image?,
        dx1: Int,
        dy1: Int,
        dx2: Int,
        dy2: Int,
        sx1: Int,
        sy1: Int,
        sx2: Int,
        sy2: Int,
        observer: ImageObserver?
    ): Boolean
            = g2d.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, observer)

    override fun drawImage(
        img: Image?,
        dx1: Int,
        dy1: Int,
        dx2: Int,
        dy2: Int,
        sx1: Int,
        sy1: Int,
        sx2: Int,
        sy2: Int,
        bgcolor: java.awt.Color?,
        observer: ImageObserver?
    ): Boolean
            = g2d.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, bgcolor, observer)

    override fun getFontRenderContext(): FontRenderContext
            = g2d.fontRenderContext

    override fun setXORMode(c1: java.awt.Color?)
            = g2d.setXORMode(c1)

    override fun addRenderingHints(hints: MutableMap<*, *>?)
            = g2d.addRenderingHints(hints)

    override fun getRenderingHints(): RenderingHints
            = g2d.renderingHints

    override fun translate(x: Px, y: Px)
            = g2d.translate(x, y)

    override fun translate(tx: PxDouble, ty: PxDouble)
            = g2d.translate(tx, ty)

    override fun setFont(font: Font?)
            = g2d.setFont(font)

    override fun getFont(): Font
            = g2d.font

    override fun getStroke(): Stroke
            = g2d.stroke

    override fun fillOval(x: Px, y: Px, width: Px, height: Px)
            = g2d.fillOval(x, y, width, height)

    override fun getClip(): Shape
            = g2d.clip

    override fun drawRenderedImage(img: RenderedImage?, xform: AffineTransform?)
            = g2d.drawRenderedImage(img, xform)

    override fun dispose()
            = g2d.dispose()

    override fun setClip(x: Px, y: Px, width: Px, height: Px)
            = g2d.setClip(x, y, width, height)

    override fun setClip(clip: Shape?)
            = g2d.setClip(clip)

    override fun setRenderingHints(hints: MutableMap<*, *>?)
            = g2d.setRenderingHints(hints)

    override fun getTransform(): AffineTransform
            = g2d.transform

    override fun create(): Graphics
            = g2d.create()

    override fun drawOval(x: Px, y: Px, width: Px, height: Px)
            = g2d.drawOval(x, y, width, height)

    override fun drawRenderableImage(img: RenderableImage?, xform: AffineTransform?)
            = g2d.drawRenderableImage(img, xform)

    override fun setComposite(comp: Composite?)
            = g2d.setComposite(comp)

    override fun clearRect(x: Px, y: Px, width: Px, height: Px)
            = g2d.clearRect(x, y, width, height)

    override fun drawPolygon(xPoints: IntArray?, yPoints: IntArray?, nPoints: Int)
            = g2d.drawPolygon(xPoints, yPoints, nPoints)

    override fun setTransform(Tx: AffineTransform?)
            = g2d.setTransform(Tx)

    override fun getPaint(): Paint
            = g2d.paint

    override fun fillRect(x: Px, y: Px, width: Px, height: Px)
            = g2d.fillRect(x, y, width, height)

    override fun drawGlyphVector(g: GlyphVector?, x: Float, y: Float)
            = g2d.drawGlyphVector(g, x, y)

    fun drawGlyphVector(g: GlyphVector?, x: PxDouble, y: PxDouble)
            = this.drawGlyphVector(g, x.toFloat(), y.toFloat())

    override fun drawRoundRect(x: Px, y: Px, width: Px, height: Px, arcWidth: Px, arcHeight: Px)
            = g2d.drawRoundRect(x, y, width, height, arcWidth, arcHeight)

    override fun getFontMetrics(f: Font?): FontMetrics
            = g2d.getFontMetrics(f)

    override fun fillPolygon(xPoints: IntArray?, yPoints: IntArray?, nPoints: Int)
            = g2d.fillPolygon(xPoints, yPoints, nPoints)

    override fun setColor(c: java.awt.Color?)
            = g2d.setColor(c)

    override fun setRenderingHint(hintKey: RenderingHints.Key?, hintValue: Any?)
            = g2d.setRenderingHint(hintKey, hintValue)

    override fun fillRoundRect(x: Px, y: Px, width: Px, height: Px, arcWidth: Px, arcHeight: Px)
            = g2d.fillRoundRect(x, y, width, height, arcWidth, arcHeight)

    override fun drawArc(x: Px, y: Px, width: Px, height: Px, startAngle: Px, arcAngle: Px)
            = g2d.drawArc(x, y, width, height, startAngle, arcAngle)

    override fun getRenderingHint(hintKey: RenderingHints.Key?): Any
            = g2d.getRenderingHint(hintKey)

    override fun hit(rect: Rectangle?, s: Shape?, onStroke: Boolean): Boolean
            = g2d.hit(rect, s, onStroke)

    override fun setBackground(color: java.awt.Color?)
            = g2d.setBackground(color)

}