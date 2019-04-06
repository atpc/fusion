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

class Style
@JvmOverloads constructor(
    private val parent: Style? = null,
    /**
     * **See also:**
     *
     * [CSS selectors | MDN](https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Selectors)
     * [CSS Selectors Reference](https://www.w3schools.com/cssref/css_selectors.asp)
     */
    @JvmField val selectors: List<String>,
    rules: Map<String, String?>
) {

    private val ruleMap: Map<String, String?> = rules.toMap()

    operator fun get(rule: String): String = ruleMap[rule] ?: if (parent != null)
        parent[rule]
    else
        throw UndefinedRuleException(rule, this)

}
