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

data class MeasuredNumber(@JvmField val value: Number, @JvmField val unit: MeasureUnit) {

    override fun toString(): String = "$value${unit.symbol}"

}


@JvmOverloads
fun Number.toMeasured(unit: MeasureUnit = MeasureUnit.NUMERIC) = MeasuredNumber(this, unit)

val Number.px: MeasuredNumber get() = this.toMeasured(MeasureUnit.PX)

val Number.em: MeasuredNumber get() = this.toMeasured(MeasureUnit.EM)
val Number.vw: MeasuredNumber get() = this.toMeasured(MeasureUnit.VW)
val Number.vh: MeasuredNumber get() = this.toMeasured(MeasureUnit.VH)
val Number.percent: MeasuredNumber get() = this.toMeasured(MeasureUnit.PERCENT)