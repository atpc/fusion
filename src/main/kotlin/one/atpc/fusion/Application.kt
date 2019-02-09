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

package one.atpc.fusion

import one.atpc.fusion.event.GlobalEvent
import one.atpc.fusion.event.GlobalEventListener

abstract class Application(args: Array<String>) : Runnable {
    private val listeners = mutableListOf<GlobalEventListener<Application>>()

    @JvmField
    val resources: Resources = Resources(this.javaClass.`package`)


    /**
     * Determines whether this `Application` is a graphical application.
     * `true`, if the application employs a GUI (Graphical User Interface).
     */
    open val isGraphical: Boolean = false


    open fun addEventListener(listener: GlobalEventListener<Application>) {
        this.listeners.add(listener)
    }

    open fun removeEventListener(listener: GlobalEventListener<Application>) {
        this.listeners.remove(listener)
    }

    open fun onEventTriggered(action: (GlobalEvent<Application>) -> Unit)
            = this.addEventListener(object : GlobalEventListener<Application> {
        override fun eventTriggered(event: GlobalEvent<Application>) = action(event)
    })


    protected open fun fireEvent(descriptor: Any) {
        val event = GlobalEvent(this, descriptor)
        for (listener in listeners) {
            listener.eventTriggered(event)
        }
    }

}