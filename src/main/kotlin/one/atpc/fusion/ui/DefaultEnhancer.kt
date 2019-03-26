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
import java.awt.Component
import java.awt.Graphics
import java.awt.event.MouseListener
import java.awt.event.MouseMotionListener
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

open class DefaultEnhancer<T>(private val enhanced: T) : XEnhanceable<T> where T : Component, T : XEnhanceable<T> {
    private val features: MutableMap<KClass<out Feature<in T>>, Feature<in T>>
            = ConcurrentHashMap()

    override fun addFeature(feature: Feature<in T>) = when {
        // Reject if feature is already connected to another component
        feature.connectedComponent != null -> throw IllegalArgumentException(
            "Feature is already connected to a component!"
        )

        // Check if the the feature class is already connected
        // (Calling this' method and not enhanced's to ensure deterministic,
        //  boxed behaviour)
        this.hasFeature(feature::class) -> {
            // If the exact same feature instance is already connected, ...
            if (this.hasFeature(feature))
                // ... do nothing.
                Unit
            else
                // Otherwise throw an exception
                throw IllegalArgumentException(
                    "${feature::class.qualifiedName}: A feature of this type is already connected to this component!"
                )
        }

        else -> {
            feature.connectedComponent = enhanced

            if (feature is MouseListener)
                enhanced.addMouseListener(feature)
            if (feature is MouseMotionListener)
                enhanced.addMouseMotionListener(feature)

            features[feature::class] = feature
        }
    }

    override fun removeFeature(feature: Feature<in T>) {
        if (features.containsValue(feature)) {
            if (feature is MouseListener)
                enhanced.removeMouseListener(feature)
            if (feature is MouseMotionListener)
                enhanced.removeMouseMotionListener(feature)

            features.remove(feature::class)

            feature.connectedComponent = null
        }
    }

    override fun hasFeature(featureClass: KClass<out Feature<in T>>): Boolean
            = features.containsKey(featureClass)

    override fun hasFeature(featureInstance: Feature<in T>): Boolean
            = features.containsValue(featureInstance)


    open fun draw(g: XGraphics) {
        // Draw the features
        for (feature in features.values)
            // Do not draw lightweight if heavy drawing is enabled
            if (!feature.isDrawingHeavy)
                feature.draw(g)
    }

    open fun paint(g: Graphics) {
        val xg = g.toXGraphics()
        for (feature in features.values)
            // Only draw the features that have heavy drawing enabled
            if (feature.isDrawingHeavy)
                feature.draw(xg)
    }

}