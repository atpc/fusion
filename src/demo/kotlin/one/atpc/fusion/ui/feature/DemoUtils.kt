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

import one.atpc.fusion.ui.XTextField
import one.atpc.fusion.ui.XFrame
import one.atpc.fusion.ui.toDimension2
import java.awt.FlowLayout
import javax.swing.WindowConstants

object DemoUtils {

    @JvmStatic
    internal fun makeTextFieldFrame(title: String): Pair<XFrame, XTextField> {
        val frame = XFrame(title)
        frame.layout = FlowLayout()
        val textField = XTextField(10)
        frame.add(textField)

        frame.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE

        frame.pack()

        val size = frame.size.toDimension2()
        frame.size = size + size    // TODO Dimension multiplication!
        frame.setLocationToCenter()

        return Pair(frame, textField)
    }

}