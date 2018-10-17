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

@file:Suppress("ConvertTwoComparisonsToRangeCheck")

package com.github.debop.kodatimes.ranges

import com.github.debop.kodatimes.dateTimeOf
import com.github.debop.kodatimes.milliDurationOf
import org.joda.time.DateTime
import org.joda.time.Instant
import java.util.*


/**
 * Build [[this], [endInclusive]] range
 */
operator fun DateTime.rangeTo(endInclusive: DateTime): DateTimeRange = DateTimeRange(this, endInclusive)

/**
 * Build [[this], [endInclusive]] range
 */
operator fun Instant.rangeTo(endInclusive: Instant): InstantRange = InstantRange(this, endInclusive)

/**
 *  [DateTime] range
 *
 *  @property start Start of range
 *  @property endInclusive end of range
 */
class DateTimeRange(start: DateTime, endInclusive: DateTime)
    : DateTimeProgression(start, endInclusive, milliDurationOf(1L)), ClosedRange<DateTime> {

    override val start: DateTime get() = first
    override val endInclusive: DateTime get() = last

    override fun contains(value: DateTime): Boolean = first <= value && value <= last

    override fun isEmpty(): Boolean = first > last

    override fun equals(other: Any?): Boolean {
        if(other == null) return false
        if(other === this) return true

        return when(other) {
            is DateTimeRange -> isEmpty() && other.isEmpty() || (first == other.first && last == other.last)
            else -> false
        }
    }

    override fun hashCode(): Int = Objects.hash(first, last)

    override fun toString(): String = "$first..$last"

    companion object {
        /** Empty [DateTimeRange] */
        @JvmField val EMPTY: DateTimeRange = DateTimeRange(dateTimeOf(1, 1, 1), dateTimeOf(0, 1, 1))
    }
}


/**
 * [org.joda.time.Instant] range
 *
 *  @property start Start of range
 *  @property endInclusive end of range
 */
class InstantRange(start: Instant, endInclusive: Instant)
    : InstantProgression(start, endInclusive, milliDurationOf(1L)), ClosedRange<Instant> {

    override val start: Instant get() = first
    override val endInclusive: Instant get() = last

    override fun contains(value: Instant): Boolean = first <= value && value <= last

    override fun isEmpty(): Boolean = first > last

    override fun equals(other: Any?): Boolean {
        if(other == null) return false
        if(other === this) return true

        return when(other) {
            is InstantRange -> isEmpty() && other.isEmpty() || (first == other.first && last == other.last)
            else -> false
        }
    }

    override fun hashCode(): Int = Objects.hash(first, last)

    override fun toString(): String = "$first..$last"

    companion object {
        /** Empty [Instant] */
        @JvmField val EMPTY: InstantRange = InstantRange(Instant(1), Instant(0))
    }
}
