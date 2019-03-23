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

import kotlin.reflect.KClass

/**
 * An informative annotation used to indicate that a function
 * is intended as a constructor for a type.
 * A function annotated as `ConstructorFunction` should have the
 * same name as the type it's creating.
 *
 * Constructor functions are useful for abstract types
 * that can't have a constructor (e.g. interfaces) but could
 * be conveniently created by one, or for adding additional constructors
 * to classes without having to subclass them.
 *
 * **Example:**
 * ```
 * interface Life
 *
 * @ConstructorFunction(Life::class)
 * fun Life(dna: String) {
 *     // ...
 * }
 * ```
 *
 * @param type The type the annotated function is constructing.
 * @author Thomas Orlando
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
@MustBeDocumented
annotation class ConstructorFunction(val type: KClass<*>)