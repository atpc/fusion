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

package one.atpc.fusion.ui.style

import io.kotlintest.be
import io.kotlintest.should
import io.kotlintest.shouldThrow
import io.kotlintest.specs.FreeSpec
import one.atpc.fusion.ui.style.MeasureUnit.*

class MeasureUnitTests : FreeSpec({
    "test valueOfSymbol()" {
        mapOf(
            ""    to NUMERIC,

            "px"  to PX,
            "q"   to Q,
            "mm"  to MM,
            "cm"  to CM,
            "in"  to IN,
            "pt"  to PT,
            "pc"  to PC,

            "em"  to EM,
            "ex"  to EX,
            "ch"  to CH,
            "rem" to REM,
            "vw"  to VW,
            "vh"  to VH,

            "%"   to PERCENT
        ).forEach { (k, v) ->
            MeasureUnit.valueOfSymbol(k) should be(v)
        }

        listOf("percent", "numeric").forEach { e ->
            shouldThrow<IllegalArgumentException> {
                MeasureUnit.valueOfSymbol(e)
            }
        }
    }
})