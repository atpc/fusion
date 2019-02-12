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

import one.atpc.fusion.Application
import one.atpc.fusion.FatalException
import one.atpc.fusion.name
import one.atpc.fusion.util.OSType
import one.atpc.fusion.util.SystemInfo
import java.awt.Container
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.nio.file.Files
import java.nio.file.LinkOption
import java.util.*
import javax.swing.LookAndFeel
import javax.swing.SwingUtilities
import javax.swing.UIManager
import javax.swing.WindowConstants

abstract class XApplication(args: Array<String>) : Application(args) {
    private var container: XApplicationContainer? = null

    private var _title: String = this::class.simpleName ?: "???"
    var title: String
        get() = this._title
        set(value) {
            this._title = value
            container?.title = value
        }

    val isVisible: Boolean get() = container?.isVisible ?: false

    var uiConfig = UIConfiguration()

    private var _contentPane: XPanel = run {
        val pane = XPanel()
        pane.background = Color.white
        pane
    }
    var contentPane: XPanel
        get() = container?.fusionContentPane ?: _contentPane
        set(pane) {
            this._contentPane = pane
            container?.fusionContentPane = pane
        }


    override fun run() {
        SwingUtilities.invokeLater {
            synchronized(this) {
                // Create container
                val container = XApplicationContainer(this, this::onApplicationClosing)
                // Set container
                this.container = container
            }
            // Show container
            this.container!!.isVisible = true
        }
    }


    // Event method, to be overridden by children
    protected fun onApplicationClosing() {
        // Handler actions here...
    }


    // XApplication is graphical
    override val isGraphical: Boolean = true


    data class UIConfiguration(var defaultSize: Dimension2.Int = Dimension2(800, 500),
                               var rememberBounds: Boolean = true,
                               var defaultCloseOperation: Int = WindowConstants.EXIT_ON_CLOSE,
                               var lookAndFeel: LookAndFeel? = UIConfiguration.DEFAULT_LAF) {
        companion object {
            @JvmField
            val DEFAULT_LAF: LookAndFeel? = null
        }
    }

}



private val fusionDirectory: File = SystemInfo.userDirectory.resolve(".fusion/")

// TODO Perform the check automatically
fun checkFusionDirectory() {
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
}



private class XApplicationContainer(application: XApplication, closingEventHandler: () -> Unit) : XFrame() {

    var fusionContentPane: XPanel
        get() = super.getContentPane() as XPanel
        set(pane) = super.setContentPane(pane)


    init {
        this.title = application.title
        this.fusionContentPane = application.contentPane

        // Set default close operation
        this.defaultCloseOperation = application.uiConfig.defaultCloseOperation

        // Reacts to window closing
        this.addWindowListener(object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent?) {
                try {
                    // Call closing listener in XApplication (Higher priority)
                    closingEventHandler()
                    // Save container properties (Lower priority)
                    storeContainerProperties(application = application, container = this@XApplicationContainer)
                }
                catch (e: Exception) {
                    // We catch exceptions to ensure the program gets closed
                    System.err.println("Problem occurred during application closing.")
                    e.printStackTrace()
                }
                catch (e: Throwable) {
                    // We also catch any other throwables
                    System.err.println("Error while closing application!")
                    System.err.println("Abort program.")
                    System.err.println()
                    e.printStackTrace()
                    System.exit(1)
                }
            }
        })

        // Set look and feel, before packing
        if (application.uiConfig.lookAndFeel != XApplication.UIConfiguration.DEFAULT_LAF) {
            UIManager.setLookAndFeel(application.uiConfig.lookAndFeel)
            SwingUtilities.updateComponentTreeUI(this)
        }

        // Pack
        this.pack()

        // Set default bounds first
        this.size = application.uiConfig.defaultSize
        this.setLocationToCenter()  // Default location is center

        if (application.uiConfig.rememberBounds) {
            // Load container properties
            val containerProperties: Properties? = loadContainerProperties(application)
            // Properties must not be null and have at least four values
            if (! (containerProperties == null || containerProperties.size < 4)) {
                // Use the saved bounds
                val bounds: List<Int?> = listOf(
                    PROPERTY_X,
                    PROPERTY_Y,
                    PROPERTY_WIDTH,
                    PROPERTY_HEIGHT
                ).map {
                    containerProperties.getProperty(it)?.toInt()
                }
                // Only set if all conversions were successful
                // (And all values set)
                if (null !in bounds) {
                    this.setLocation(bounds[0]!!, bounds[1]!!)
                    this.setSize(bounds[2]!!, bounds[3]!!)
                }
            }
        }
    }


    companion object {
        private const val PROPERTY_X = "x"
        private const val PROPERTY_Y = "y"
        private const val PROPERTY_WIDTH = "width"
        private const val PROPERTY_HEIGHT = "height"

        @JvmStatic
        private fun loadContainerProperties(application: XApplication): Properties? {
            val containerPropsFile = getContainerPropertiesFile(application.javaClass).second

            return if (containerPropsFile.exists()) {
                // Ensure the properties file is not a directory
                if (containerPropsFile.isDirectory) {
                    // If the properties file is a directory, clearly there's something going wrong
                    // Throw an exception to avoid negative results
                    throw IOException("Fusion: Internal configuration error: '$containerPropsFile' is a directory!")
                }

                // The properties to load into
                val props = Properties()
                try {
                    // Load the properties (with auto-closing)
                    containerPropsFile.inputStream().use(props::load)
                    props
                }
                catch (e: IOException) {
                    // Print the error message, but proceed as usual
                    System.err.println("Fusion: Could not read container properties!")
                    System.err.println(
                        "\tFile${if (e is FileNotFoundException) " not found" else ""}: $containerPropsFile"
                    )
                    e.printStackTrace()
                    null
                }
            }
            else
                null
        }

        @JvmStatic
        private fun storeContainerProperties(application: XApplication, container: XApplicationContainer) {
            val containerPropertiesFileResults = getContainerPropertiesFile(application.javaClass)
            val saveDirectory: File = containerPropertiesFileResults.first
            val containerPropsFile: File = containerPropertiesFileResults.second

            // Check fusion directory
            checkFusionDirectory()
            // Check save directory
            if (!saveDirectory.exists()) {
                if (!saveDirectory.mkdirs())
                    throw IOException("Fusion: Could not create directories: '$saveDirectory'!")
            }
            // Get the bounds
            val containerProps = Properties()
            containerProps[PROPERTY_X] = container.x.toString()
            containerProps[PROPERTY_Y] = container.y.toString()
            containerProps[PROPERTY_WIDTH] = container.width.toString()
            containerProps[PROPERTY_HEIGHT] = container.height.toString()

            // Save the properties (auto-closing)
            try {
                containerPropsFile.outputStream().use { out -> containerProps.store(out, null) }
            }
            catch (e: IOException) {
                System.err.println("Fusion: Error while writing container properties!")
                System.err.println("\tFile: $containerPropsFile")
                e.printStackTrace()
            }
            catch (e: ClassCastException) {
                throw FatalException("${e.name} should not occur here!", e)
            }
        }

        @JvmStatic
        private fun getContainerPropertiesFile(applicationClass: Class<XApplication>): Pair<File, File> {
            val saveDirectory = fusionDirectory.resolve(
                // Example: .fusion/org/generic/app
                applicationClass.`package`.name.replace('.', '/')
            )
            // Example: .fusion/org/generic/app/GenericApplication-container.properties
            val containerPropsFile = saveDirectory.resolve(
                "${applicationClass.simpleName ?: "unknown-name"}-container.properties"
            )
            return Pair(saveDirectory, containerPropsFile)
        }

    }


    // Deprecate the old contentPane

    @Deprecated(message = "Container as contentPane is unsupported.", level = DeprecationLevel.ERROR, replaceWith = ReplaceWith("fusionContentPane"))
    override fun getContentPane(): Container = super.getContentPane()

    @Deprecated(message = "Container as contentPane is unsupported.", level = DeprecationLevel.ERROR, replaceWith = ReplaceWith("fusionContentPane = contentPane"))
    override fun setContentPane(contentPane: Container?): Unit = throw XException(
        UnsupportedOperationException("Content pane in XApplicationContainer can not be java.awt.Container!")
    )

}
