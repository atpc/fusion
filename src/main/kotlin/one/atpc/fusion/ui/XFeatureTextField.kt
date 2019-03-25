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
import kotlin.reflect.KClass

open class XFeatureTextField : XTextField, XEnhanceable<XFeatureTextField> {

    constructor() : super()

    constructor(promptText: String) : super(promptText)

    constructor(promptText: String, promptForeground: Color) : super(promptText, promptForeground)

    constructor(promptText: String, promptForeground: Color, promptBackground: Color) : super(promptText, promptForeground, promptBackground)

    constructor(columns: Int) : super(columns)

    constructor(columns: Int, promptText: String) : super(columns, promptText)


    private val enhancer: DefaultEnhancer<XFeatureTextField> = DefaultEnhancer(this)


    override fun addFeature(feature: Feature<in XFeatureTextField>)
            = enhancer.addFeature(feature)

    override fun removeFeature(feature: Feature<in XFeatureTextField>)
            = enhancer.removeFeature(feature)

    override fun hasFeature(featureClass: KClass<out Feature<in XFeatureTextField>>): Boolean
            = enhancer.hasFeature(featureClass)

    override fun hasFeature(featureInstance: Feature<in XFeatureTextField>): Boolean
            = enhancer.hasFeature(featureInstance)

    override fun draw(g: XGraphics) {
        super.draw(g)

        enhancer.draw(g)
    }

    override fun paint(g: Graphics) {
        super.paint(g)

        enhancer.paint(g)
    }

}