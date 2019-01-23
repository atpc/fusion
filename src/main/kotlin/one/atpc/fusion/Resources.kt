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

@file:Suppress("MemberVisibilityCanBePrivate")

package one.atpc.fusion

import one.atpc.fusion.ui.Font

// TODO Add tests
// Resources for a package
open class Resources(resourceRoot: String) {

    constructor(pack: Package) : this("/${pack.name.replace('.', '/')}/")

    val resourceRoot: String = run {
        // Abort if the resource root is not identified from the package root
        if (!resourceRoot.startsWith('/'))
            throw IllegalArgumentException("Resource root path must be identified from the package root! (Missing '/' on start)")
        if (resourceRoot.contains('\\'))
            throw IllegalArgumentException("Resource root path must not contain backslashes ('\\')!")

        // If the resource root doesn't end in a /,
        // append it
        if (!resourceRoot.endsWith('/'))
            "$resourceRoot/"
        else
            resourceRoot
    }


    fun font(path: String, type: Int, size: Double): Font
            = Font(type, javaClass.getResourceAsStream(validateCombine(path)), size)


    /**
     * Checks if the given path is valid, appends the path to the [resourceRoot] and
     * returns the combined path.
     * Valid paths do not start with a '/' and do not include the '\' character.
     *
     * @param path The path to validate.
     * @return The combined path if the path is valid.
     * @throws IllegalArgumentException If the path is invalid.
     */
    private fun validateCombine(path: String): String {
        if (path.startsWith('/'))
            throw IllegalArgumentException("Resource path must not start with a '/'!")
        if (path.contains('\\'))
            throw IllegalArgumentException("Resource path must not include a '\' character!")

        return resourceRoot + path
    }

}