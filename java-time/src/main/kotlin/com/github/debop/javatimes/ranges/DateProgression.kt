/*
 * Copyright (c) 2016. Sunghyouk Bae <sunghyouk.bae@gmail.com>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.debop.javatimes.ranges

import com.github.debop.javatimes.DateIterator
import com.github.debop.javatimes.isPositive
import com.github.debop.javatimes.plus
import java.time.Duration
import java.util.Date
import java.util.NoSuchElementException
import java.util.Objects

/**
 * Create [DateProgression] instance
 */
@JvmOverloads
fun <T : Date> dateProgressionOf(start: T, endInclusive: T, step: Duration = Duration.ofMillis(1)): DateProgression<T> =
    DateProgression.fromClosedRange(start, endInclusive, step)

/**
 * A progression of value of `java.util.Date` subclass
 *
 * @property first first value of progression
 * @property last  last value of progression
 * @property step  progression step
 */
open class DateProgression<out T : Date> internal constructor(start: T, endInclusive: T, val step: Duration) : Iterable<T> {
    init {
        require(step != Duration.ZERO) { "step must be non-zero" }
        if (step.isPositive) {
            require(start <= endInclusive) {
                "When step[$step] is positive, start[$start] must be less than or equal endInclusive[$endInclusive]"
            }
        } else {
            require(start >= endInclusive) {
                "When step[$step] is negative, start[$start] must be greater than or equal endInclusive[$endInclusive]"
            }
        }
    }

    companion object {
        @JvmStatic
        @JvmOverloads
        fun <T : Date> fromClosedRange(start: T, endInclusive: T, step: Duration = Duration.ofMillis(1L)): DateProgression<T> {
            require(step != Duration.ZERO) { "step must be non-zero" }
            return DateProgression(start, endInclusive, step)
        }
    }

    val first: T = start

    @Suppress("UNCHECKED_CAST")
    val last: T = getProgressionLastElement(start, endInclusive, step) as T

    open fun isEmpty(): Boolean = if (step > Duration.ZERO) first > last else first < last

    override fun iterator(): Iterator<T> = DateProgressionIterator(first, last, step)

    override fun equals(other: Any?): Boolean =
        other is DateProgression<*> &&
        ((isEmpty() && other.isEmpty()) ||
         (first == other.first && last == other.last && step == other.step))

    override fun hashCode(): Int =
        if (isEmpty()) -1
        else Objects.hash(first, last, step)

    override fun toString(): String =
        if (step > Duration.ZERO) "$first..$last step $step"
        else "$first downTo $last step ${step.negated()}"

    /**
     * An iterator over a progression of values of type [java.util.Date]
     *
     * @property step the number by which the value is incremented on each step.
     */
    internal class DateProgressionIterator<T : Date>(first: T, last: T, val step: Duration) : DateIterator<T>() {

        private val stepMillis = step.toMillis()
        private val _finalElement: T = last
        private var _hasNext: Boolean = if (step.isPositive) first <= last else first >= last
        private var _next: T = if (_hasNext) first else _finalElement

        override fun hasNext(): Boolean = _hasNext

        override fun nextDate(): T {
            val value = _next
            if (value == _finalElement) {
                if (!_hasNext)
                    throw NoSuchElementException()
                _hasNext = false
            } else {
                @Suppress("UNCHECKED_CAST")
                _next = _next.plus(stepMillis) as T
            }
            return value
        }
    }
}