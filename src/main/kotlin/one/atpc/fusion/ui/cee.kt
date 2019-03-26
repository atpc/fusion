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

import one.atpc.fusion.ui.event.FocusListener
import one.atpc.fusion.ui.event.KeyListener
import one.atpc.fusion.ui.event.MouseListener
import one.atpc.fusion.ui.event.MouseMotionListener
import java.awt.Component
import java.awt.event.*
import javax.swing.JComboBox
import javax.swing.JTextField

// Component Event Extensions

fun <T : Component> T.onFocusGained(action: (e: FocusEvent?) -> Unit)
        = this.addFocusListener(FocusListener.FocusGained(action))

fun <T : Component> T.onFocusLost(action: (e: FocusEvent?) -> Unit)
        = this.addFocusListener(FocusListener.FocusLost(action))


fun <T : Component> T.onMouseMoved(action: (e: MouseEvent?) -> Unit)
        = this.addMouseMotionListener(MouseMotionListener.MouseMoved(action))

fun <T : Component> T.onMouseDragged(action: (e: MouseEvent?) -> Unit)
        = this.addMouseMotionListener(MouseMotionListener.MouseDragged(action))


fun <T : Component> T.onMouseEntered(action: (e: MouseEvent?) -> Unit)
        = this.addMouseListener(MouseListener.MouseEntered(action))

fun <T : Component> T.onMouseExited(action: (e: MouseEvent?) -> Unit)
        = this.addMouseListener(MouseListener.MouseExited(action))

fun <T : Component> T.onMousePressed(action: (e: MouseEvent?) -> Unit)
        = this.addMouseListener(MouseListener.MousePressed(action))

fun <T : Component> T.onMouseReleased(action: (e: MouseEvent?) -> Unit)
        = this.addMouseListener(MouseListener.MouseReleased(action))

fun <T : Component> T.onMouseClicked(action: (e: MouseEvent?) -> Unit)
        = this.addMouseListener(MouseListener.MouseClicked(action))


fun <T : Component> T.onMouseWheelMoved(action: (e: MouseWheelEvent?) -> Unit)
        = this.addMouseWheelListener(action)


fun <T : Component> T.onKeyPressed(action: (e: KeyEvent?) -> Unit)
        = this.addKeyListener(KeyListener.KeyPressed(action))

fun <T : Component> T.onKeyReleased(action: (e: KeyEvent?) -> Unit)
        = this.addKeyListener(KeyListener.KeyReleased(action))

fun <T : Component> T.onKeyTyped(action: (e: KeyEvent?) -> Unit)
        = this.addKeyListener(KeyListener.KeyTyped(action))


fun <T : AbstractButton> T.onActionPerformed(action: (e: ActionEvent?) -> Unit)
        = this.addActionListener(action)

fun <T : JTextField> T.onActionPerformed(action: (e: ActionEvent?) -> Unit)
        = this.addActionListener(action)

fun <E, T : JComboBox<E>> T.onActionPerformed(action: (e: ActionEvent?) -> Unit)
        = this.addActionListener(action)
