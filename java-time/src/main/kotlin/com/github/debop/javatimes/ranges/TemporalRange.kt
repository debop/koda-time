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

import mu.KLogging
import java.io.Serializable
import java.time.Duration
import java.time.LocalDateTime
import java.time.temporal.Temporal


fun <T> temporalRangeOf(start: T, endInclusive: T): TemporalRange<T>
where T : Temporal, T : Comparable<T> {
    return TemporalRange.fromClosedRange(start, endInclusive)
}

@Suppress("UNCHECKED_CAST")
fun <T> TemporalRange.Companion.fromClosedRange(start: T,
                                                endInclusive: T): TemporalRange<T>
where T : Temporal, T : Comparable<T> = when {
    start <= endInclusive -> TemporalRange(start, endInclusive)
    else                  -> TemporalRange(endInclusive, start)
}

/**
 * A range of [Temporal]
 *
 * @autor debop
 * @since 18. 4. 16
 */
class TemporalRange<T>(start: T, endInclusive: T)
    : TemporalProgression<T>(start, endInclusive, Duration.ofMillis(1L)), ClosedRange<T>, Serializable
where T : Temporal, T : Comparable<T> {

    companion object : KLogging() {
        @JvmField
        val EMPTY = TemporalRange<LocalDateTime>(LocalDateTime.MAX, LocalDateTime.MIN)
    }

    override val start: T get() = first

    override val endInclusive: T get() = last

    override fun contains(value: T): Boolean = value in first..last

    override fun isEmpty(): Boolean = first > last

    override fun toString(): String = "$first..$last"

}