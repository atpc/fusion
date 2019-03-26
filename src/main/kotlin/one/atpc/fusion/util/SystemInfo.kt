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

package one.atpc.fusion.util

import java.io.File
import java.util.*

/**
 * Utility object to simplify accessing information about the system.
 *
 * @see System
 * @author Thomas Orlando
 */
object SystemInfo {
    // Constants for getting system properties
    // TODO Document
    const val JAVA_VERSION      = "java.version"
    const val JAVA_VENDOR       = "java.vendor"
    const val OS_VERSION        = "os.version"
    const val OS_ARCH           = "os.arch"
    const val ARCH_DATA_MODEL   = "sun.arch.data.model"
    const val USER_HOME         = "user.home"


    /**
     * The [OSType] for this system.
     */
    @JvmStatic
    val osType: OSType by lazy {
        val os = SystemInfo["os.name", "generic"].toLowerCase(Locale.ENGLISH)
        when {
            (os.indexOf("mac") >= 0) || (os.indexOf("darwin") >= 0) -> OSType.MacOS
            os.indexOf("win") >= 0 -> OSType.Windows
            os.indexOf("nux") >= 0 -> OSType.Linux
            else -> OSType.Other
        }
    }

    /**
     * The system architecture's data model.
     *
     * @throws NullPointerException If `SystemInfo[ARCH_DATA_MODEL]` is not defined.
     * @return The data model of this system's architecture
     *         (usually `32`- or `64`-Bit).
     */
    @JvmStatic
    val architectureDataModel: UInt = this[ARCH_DATA_MODEL]!!.toUInt()

    @JvmStatic
    val userDirectoryPath: String = this[USER_HOME]!!

    @JvmStatic
    val userDirectory: File = File(this.userDirectoryPath)


    /**
     * Gets the system property indicated by the specified [key].
     *
     * @param key The name of the system property.
     * @return The string value of the system property,
     *         or `null` if there is no property with that key.
     * @throws SecurityException If a [SecurityManager] exists and its
     *                           `checkPropertyAccess` method doesn't allow
     *                           access to the specified system property.
     * @throws NullPointerException If [key] is `null`.
     * @throws IllegalArgumentException If [key] is empty.
     * @see set
     * @see SecurityManager.checkPropertyAccess
     * @see System.getProperty
     */
    @JvmStatic
    operator fun get(key: String): String? = System.getProperty(key)

    /**
     * Gets the system property indicated by the specified [key],
     * or the default value.
     *
     * @param key The name of the system property.
     * @param defValue The default (fallback) value.
     * @return The string value of the system property,
     *         or `null` if there is no property with that key.
     * @throws SecurityException If a [SecurityManager] exists and its
     *                           `checkPropertyAccess` method doesn't allow
     *                           access to the specified system property.
     * @throws NullPointerException If [key] is `null`.
     * @throws IllegalArgumentException If [key] is empty.
     * @see set
     * @see SecurityManager.checkPropertyAccess
     * @see System.getProperty
     */
    @JvmStatic
    operator fun get(key: String, defValue: String): String = System.getProperty(key, defValue)

    /**
     * Sets the system property indicated by the specified [key].
     * <br>
     * If no exception is thrown, the specified property is set
     * to the given [value].
     *
     * @param key The name of the system property.
     * @param value The new value of the system property.
     * @return The previous value of the system property,
     *         or `null` if it did not have one.
     * @throws SecurityException If a [SecurityManager] exists and its
     *                           `checkPropertyAccess` method doesn't allow
     *                           setting of the specified system property.
     * @throws NullPointerException If [key] or [value] is `null`.
     * @throws IllegalArgumentException If [key] is empty.
     * @see get
     * @see System.setProperty
     */
    @JvmStatic
    operator fun set(key: String, value: String): String? = System.setProperty(key, value)

}