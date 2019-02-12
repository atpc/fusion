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

import one.atpc.fusion.util.OSType
import one.atpc.fusion.util.SystemInfo
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.LinkOption

private val fusionDirectory = SystemInfo.userDirectory.resolve(".fusion/")

/**
 * Fusion's place for storing library-related information in the user's home directory.
 */
val directory: File get() {
    when {
        !fusionDirectory.exists() -> {
            // Create directory
            if (!fusionDirectory.mkdirs())
                throw IOException("Fusion: Could not create directories: '$fusionDirectory'!")
        }
        !fusionDirectory.isDirectory -> {
            // Throw exception
            throw IOException("Fusion: Directory '$fusionDirectory' is not a directory!")
        }
    }
    // If fusion directory is not hidden and we're on windows
    if (!fusionDirectory.isHidden && SystemInfo.osType == OSType.Windows) {
        // Hide it, windows style
        Files.setAttribute(fusionDirectory.toPath(), "dos:hidden", true, LinkOption.NOFOLLOW_LINKS)
    }

    return fusionDirectory
}
