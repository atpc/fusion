# Fusion
[![Jitpack](https://jitpack.io/v/atpc/fusion.svg?style=flat-square)](https://jitpack.io/#atpc/fusion)
[![License: LGPL-3.0](https://img.shields.io/github/license/atpc/fusion.svg?style=flat-square)][license]

## Code examples

Writing a simple application:
```kotlin
class ExampleApplication(args: Array<String>) : XApplication(args) {
    
    init {
        this.title = "Example"
        
        // Add a simple text
        this.contentPane.text("Hello, Fusion!")
        
        // Let's change the LookAndFeel
        this.uiConfig.lookAndFeel = NimbusLookAndFeel()
    }
    
    companion object {
    
        @JvmStatic
        fun main(args: Array<String>) {
            // No SwingUtilities.invokeLater() required!
            val app = ExampleApplication(args)
            app.run()
        }
    
    }
}
```

## License
Copyright © 2019 &nbsp; Thomas Orlando, ATPC  
Licensed under GNU Lesser General Public License, Version 3  
  
For the full license text, see [`LICENSE.txt`][license] or <https://www.gnu.org/licenses/>.

[license]: https://github.com/atpc/fusion/blob/master/LICENSE.txt "LGPL-3.0"
