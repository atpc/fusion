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

import java.awt.GraphicsEnvironment


typealias WindowTranslucency = java.awt.GraphicsDevice.WindowTranslucency

// TODO Add XWindow.setOpacity(), XFrame and XDialog to @see
/**
 * Determines whether the system supports the specified window [translucency] capabilities.
 * It is best practice to test the system's support for window translucency before
 * attempting to set a window's opacity.
 *
 * **See also:** [Oracle Docs > How to Create Translucent and Shaped Windows](https://docs.oracle.com/javase/tutorial/uiswing/misc/trans_shaped_windows.html)
 *
 * @param translucency The translucency capabilities to test for.
 *                     Either `PERPIXEL_TRANSPARENT`, `TRANSLUCENT` or `PERPIXEL_TRANSLUCENT`.
 * @return `true`, if the systems supports the specified window translucency capabilities,
 *              `false` otherwise.
 * @author Thomas Orlando
 */
fun isWindowTranslucencySupported(translucency: WindowTranslucency): Boolean
        = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .defaultScreenDevice.isWindowTranslucencySupported(translucency)