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

package one.atpc.fusion.ui.feature

import one.atpc.fusion.ui.XFeatureTextField
import one.atpc.fusion.ui.XFrame
import one.atpc.fusion.ui.toDimension2
import java.awt.FlowLayout
import javax.swing.WindowConstants

object ClearingFeatureDemo {
    @JvmStatic
    fun main(args: Array<String>) {
        val frame = XFrame(this::class.simpleName ?: "ClearingFeatureDemo")
        frame.layout = FlowLayout()
        val featureTextField = XFeatureTextField(10)
        featureTextField.addFeature(ClearingFeature())
        frame.add(featureTextField)
        frame.pack()
        val size = frame.size.toDimension2()
        frame.size = size + size    // TODO Dimension multiplication!
        frame.setLocationToCenter()
        frame.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        frame.isVisible = true
    }
}