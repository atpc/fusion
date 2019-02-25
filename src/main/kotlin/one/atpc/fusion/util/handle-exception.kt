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

import one.atpc.fusion.name
import one.atpc.fusion.qualifiedName
import one.atpc.fusion.ui.isHeadless
import javax.swing.JOptionPane

@JvmOverloads
fun <T : Exception> handleException(e: T, info: String? = null, warning: Boolean = false) {
    // Print additional info before the stack trace
    if (info != null)
        System.err.println(info)
    // Print the stack trace
    e.printStackTrace()

    if (!isHeadless()) {
        val message = getMessage(e)
        JOptionPane.showMessageDialog(
            null,
            wrapToHTML(
                (info?.split('\n')?.plus(" ") ?: emptyList()) +
                listOf(
                    message ?: "${e.name} occurred while executing the program.",
                    if (e.cause != null) "Caused by: ${e.cause!!.qualifiedName}" else ""
                )
            ),
            e.qualifiedName,
            if (warning) JOptionPane.WARNING_MESSAGE else JOptionPane.ERROR_MESSAGE
        )
    }
}


private fun getMessage(e: Throwable): String? {
    val localizedMessage: String? = e.localizedMessage
    val message: String? = if (localizedMessage.isNullOrBlank()) localizedMessage else e.message
    return if (message.isNullOrBlank()) {
        val cause = e.cause
        // If there's a cause, and the message is empty,
        // recurse and return the message of the cause
        if (cause != null)
            getMessage(cause)
        else
            null
    }
    else message
}

private fun wrapToHTML(lines: List<String>): String {
    val html = StringBuilder("<html>")
    for (line in lines) {
        if (line.isNotEmpty()) {
            html.append("<p>")
            html.append(line)
            html.append("</p>")
        }
    }
    html.append("</html>")
    return html.toString()
}