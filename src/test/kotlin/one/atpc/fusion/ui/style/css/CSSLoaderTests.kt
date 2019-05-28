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

package one.atpc.fusion.ui.style.css

import io.kotlintest.matchers.maps.shouldContainKey
import io.kotlintest.matchers.types.beNull
import io.kotlintest.shouldNot
import io.kotlintest.shouldThrow
import io.kotlintest.specs.FreeSpec
import one.atpc.fusion.ui.style.Style

class CSSLoaderTests : FreeSpec({
    var testStyle: Style? = null

    "test loader function" {
        // Should load without problems
        testStyle = CSSLoader.load(this::class.java.getResourceAsStream("/one/atpc/fusion/ui/style/css/test-style.css"))
    }
    "test pseudo-class parsing" {
        testStyle shouldNot beNull()
        testStyle!!.shouldContainKey(".meta-button:hover")
    }

    // Ensure erroneous CSS produces errors
    "test bad number values" {
        listOf(
            "+-25px",   // More than one plus and minus
            "++2em",
            "--3",
            "23.12.80", // Too many decimals
            "12."       // Number followed by a dot
        ).forEach { e ->
            shouldThrow<ParserException> {
                CSSLoader.load("selector { value: $e; }")
            }
        }
    }
})
