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

import one.atpc.fusion.ui.feature.Feature
import java.awt.Graphics
import java.awt.event.MouseListener
import java.awt.event.MouseMotionListener
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

open class XFeatureTextField : XTextField {

    constructor() : super()

    constructor(promptText: String) : super(promptText)

    constructor(promptText: String, promptForeground: Color) : super(promptText, promptForeground)

    constructor(promptText: String, promptForeground: Color, promptBackground: Color) : super(promptText, promptForeground, promptBackground)

    constructor(columns: Int) : super(columns)

    constructor(columns: Int, promptText: String) : super(columns, promptText)


    private val features: MutableMap<KClass<out Feature>, Feature> = ConcurrentHashMap()

    open fun addFeature(feature: Feature) = when {
        // Reject if feature is already connected to another component
        feature.connectedComponent != null -> throw IllegalArgumentException(
            "Feature is already connected to a component!"
        )

        // Check if the the feature class is already connected
        this.hasFeature(feature::class) -> {
            // If the
            if (features.containsValue(feature))
                // Do nothing
                Unit
            else
                throw IllegalArgumentException(
                    "${feature::class.qualifiedName}: A feature of this type is already connected to this component!"
                )
        }

        else -> {
            feature.connectedComponent = this

            if (feature is MouseListener)
                this.addMouseListener(feature)
            if (feature is MouseMotionListener)
                this.addMouseMotionListener(feature)

            features[feature::class] = feature
        }
    }

    open fun removeFeature(feature: Feature) {
        if (features.containsValue(feature)) {
            if (feature is MouseListener)
                this.removeMouseListener(feature)
            if (feature is MouseMotionListener)
                this.removeMouseMotionListener(feature)

            features.remove(feature::class)

            feature.connectedComponent = null
        }
    }

    open fun hasFeature(featureClass: KClass<out Feature>) = features.containsKey(featureClass)


    override fun draw(g: XGraphics) {
        super.draw(g)
        // Draw the features
        for (feature in features.values)
            if (!feature.isDrawingHeavy)
                feature.draw(g)
    }

    override fun paint(g: Graphics) {
        super.paint(g)

        val xg = g.toXGraphics()
        for (feature in features.values)
            if (feature.isDrawingHeavy)
                feature.draw(xg)
    }

}