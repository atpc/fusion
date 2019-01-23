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

import javafx.application.Platform
import javafx.embed.swing.JFXPanel
import javafx.scene.Scene
import javafx.scene.web.WebView
import java.awt.Dimension
import java.net.URL
import java.util.concurrent.CompletableFuture


typealias JavaFXWebEngine = javafx.scene.web.WebEngine

fun JavaFXWebEngine.load(url: URL) = this.load(url.toString())


open class XWebView : XComponent() {
    private lateinit var webView: WebView
    private lateinit var webEngine: JavaFXWebEngine

    init {
        this.layout = FrameLayout()

        val jfxPanel = JFXPanel()

        this.add(jfxPanel)

        // Run the init code on a JavaFX thread
        Platform.runLater {
            this.webView = WebView()
            // Obtain the view's web engine
            this.webEngine = webView.engine

            jfxPanel.scene = Scene(webView)
        }
    }


    // Connect the size setters/getters to the underlying WebView

    override fun setMinimumSize(minimumSize: Dimension?) {
        super.setMinimumSize(minimumSize)
        if (minimumSize != null)
            this.webView.setMinSize(minimumSize.getWidth(), minimumSize.getHeight())
    }

    override fun setMaximumSize(maximumSize: Dimension?) {
        super.setMaximumSize(maximumSize)
        if (maximumSize != null)
            this.webView.setMaxSize(maximumSize.getWidth(), maximumSize.getHeight())
    }

    override fun setPreferredSize(preferredSize: Dimension?) {
        super.setPreferredSize(preferredSize)
        if (preferredSize != null)
            this.webView.setPrefSize(preferredSize.getWidth(), preferredSize.getHeight())
    }


    // Implemented like this because the JavaFX Platform runs on a different thread system
    fun letEngine(operation: (JavaFXWebEngine) -> Unit) {
        Platform.runLater {
            operation(this.webEngine)
        }
    }

    fun <T> letEngine(operation: (JavaFXWebEngine) -> T): CompletableFuture<T> {
        val future = CompletableFuture<T>()
        Platform.runLater {
            val result = operation(this.webEngine)
            future.complete(result) // Complete the future
        }
        return future
    }

}
