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

package com.github.debop.kodatimes.ranges

import com.github.debop.kodatimes.JodaTimeIterator
import org.joda.time.*
import java.util.*
import kotlin.NoSuchElementException

// a mod b (in arithmetical sense)
private fun mod(a: Int, b: Int): Int {
    val mod = a % b
    return if (mod >= 0) mod else mod + b
}

private fun mod(a: Long, b: Long): Long {
    val mod = a % b
    return if (mod >= 0) mod else mod + b
}

// (a - b) mod c
private fun differenceModulo(a: Int, b: Int, c: Int): Int {
    return mod(mod(a, c) - mod(b, c), c)
}

private fun differenceModulo(a: Long, b: Long, c: Long): Long {
    return mod(mod(a, c) - mod(b, c), c)
}

/**
 * Calculates the final element of a bounded arithmetic progression, i.e. the last element of the progression which is in the range
 * from [start] to [end] in case of a positive [step], or from [end] to [start] in case of a negative
 * [step].
 *
 * No validation on passed parameters is performed. The given parameters should satisfy the condition: either
 * `step > 0` and `start >= end`, or `step < 0` and`start >= end`.
 * @param start first element of the progression
 * @param end ending bound for the progression
 * @param step increment, or difference of successive elements in the progression
 * @return the final element of the progression
 * @suppress
 */
internal fun getProgressionLastElement(start: DateTime, end: DateTime, step: ReadableDuration): DateTime {
    return when {
        step.millis > 0 -> end - differenceModulo(end.millis, start.millis, step.millis)
        step.millis < 0 -> end + differenceModulo(start.millis, end.millis, -step.millis)
        else -> throw IllegalArgumentException("Step is zero.")
    }
}

/**
 * Calculates the final element of a bounded arithmetic progression, i.e. the last element of the progression which is in the range
 * from [start] to [end] in case of a positive [step], or from [end] to [start] in case of a negative
 * [step].
 *
 * No validation on passed parameters is performed. The given parameters should satisfy the condition: either
 * `step > 0` and `start >= end`, or `step < 0` and`start >= end`.
 * @param start first element of the progression
 * @param end ending bound for the progression
 * @param step increment, or difference of successive elements in the progression
 * @return the final element of the progression
 * @suppress
 */
internal fun getProgressionLastElement(start: Instant, end: Instant, step: ReadableDuration): Instant {
    return when {
        step.millis > 0 -> end - differenceModulo(end.millis, start.millis, step.millis)
        step.millis < 0 -> end + differenceModulo(start.millis, end.millis, -step.millis)
        else -> throw IllegalArgumentException("Step is zero.")
    }
}

/**
 * A progression of value of `ReadableInstant`
 *
 * @property first first value of progression
 * @property last  last value of progression
 * @property step  progression step
 */
abstract class JodaTimeProgression<out T : ReadableInstant>(start: T, endInclusive: T, val step: ReadableDuration) : Iterable<T> {
    init {
        require(step.millis != 0L) { "step must be non-zero" }
    }

    val first: T = start
    abstract val last: T

    open fun isEmpty(): Boolean = if (step.millis > 0) first > last else first < last

    override fun equals(other: Any?): Boolean =
        other is JodaTimeProgression<*> &&
        ((isEmpty() && other.isEmpty()) ||
         (first == other.first && last == other.last && step == other.step))

    override fun hashCode(): Int =
        if (isEmpty()) -1
        else Objects.hash(first, last, step)

    override fun toString(): String =
        if (step.millis > 0) "$first..$last step $step"
        else "$first downTo $last step ${step.toDuration().negated()}"
}


/**
 * A progression of value of [DateTime] type
 */
open class DateTimeProgression internal constructor(start: DateTime, endInclusive: DateTime, step: Duration)
    : JodaTimeProgression<DateTime>(start, endInclusive, step), Iterable<DateTime> {

    override val last: DateTime = getProgressionLastElement(start, endInclusive, step)

    override fun iterator(): Iterator<DateTime> = DateTimeProgressionIterator(first, last, step)

    companion object {
        @JvmStatic
        fun fromClosedRange(rangeStart: DateTime, rangeEnd: DateTime, step: Duration): DateTimeProgression =
            DateTimeProgression(rangeStart, rangeEnd, step)
    }
}

/**
 * A progression of value of [Instant] type
 */
open class InstantProgression internal constructor(start: Instant, endInclusive: Instant, step: Duration)
    : JodaTimeProgression<Instant>(start, endInclusive, step), Iterable<Instant> {

    override val last: Instant = getProgressionLastElement(start, endInclusive, step)

    override fun iterator(): Iterator<Instant> = InstantProgressionIterator(first, last, step)

    companion object {
        @JvmStatic
        fun fromClosedRange(rangeStart: Instant, rangeEnd: Instant, step: Duration): InstantProgression =
            InstantProgression(rangeStart, rangeEnd, step)
    }
}


/**
 * An iterator over a progression of values of type [DateTime]
 *
 * @property step the number by which the value is incremented on each step.
 */
internal class DateTimeProgressionIterator(first: DateTime, last: DateTime, val step: ReadableDuration) : JodaTimeIterator<DateTime>() {

    private val finalElement: DateTime = last
    private var hasNext: Boolean = if (step.millis > 0) first <= last else first >= last
    private var next: DateTime = if (hasNext) first else finalElement

    override fun hasNext(): Boolean = hasNext

    override fun nextJodaTime(): DateTime {
        val value = next
        if (value == finalElement) {
            if (!hasNext) throw NoSuchElementException()
            hasNext = false
        } else {
            next += step
        }
        return value
    }
}

/**
 * An iterator over a progression of values of type [Instant]
 *
 * @property step the number by which the value is incremented on each step.
 */
internal class InstantProgressionIterator(first: Instant, last: Instant, val step: ReadableDuration) : JodaTimeIterator<Instant>() {

    private val finalElement: Instant = last
    private var hasNext: Boolean = if (step.millis > 0) first <= last else first >= last
    private var next: Instant = if (hasNext) first else finalElement

    override fun hasNext(): Boolean = hasNext

    override fun nextJodaTime(): Instant {
        val value = next
        if (value == finalElement) {
            if (!hasNext) throw NoSuchElementException()
            hasNext = false
        } else {
            next += step
        }
        return value
    }
}


