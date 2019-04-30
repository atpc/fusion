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

@file:JvmName("IOUtils")

package one.atpc.fusion.util

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

@Throws(IOException::class)
fun toString(inputStream: InputStream, encoding: Charset = Charsets.UTF_8): String {
    ByteArrayOutputStream().use { result ->
        val buffer = ByteArray(1024)

        // Read initial section
        var length: Int = inputStream.read(buffer)
        while (length != -1) {
            result.write(buffer, 0, length)
            // Read next part
            length = inputStream.read(buffer)
        }

        return result.toString(encoding.name())
    }
}
