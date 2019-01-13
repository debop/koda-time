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

import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.OffsetTime
import java.time.Year
import java.time.YearMonth
import java.time.ZonedDateTime
import java.time.temporal.Temporal
import java.util.Date

typealias YearRange = TemporalRange<Year>
typealias YearMonthRange = TemporalRange<YearMonth>

typealias LocalDateRange = TemporalRange<LocalDate>
typealias LocalTimeRange = TemporalRange<LocalTime>
typealias LocalDateTimeRange = TemporalRange<LocalDateTime>
typealias OffsetTimeRange = TemporalRange<OffsetTime>
typealias OffsetDateTimeRange = TemporalRange<OffsetDateTime>
typealias ZonedDateTimeRange = TemporalRange<ZonedDateTime>

typealias InstantRange = TemporalRange<Instant>


operator fun Year.rangeTo(endInclusive: Year): YearRange =
    YearRange.fromClosedRange(this, endInclusive)

operator fun YearMonth.rangeTo(endInclusive: YearMonth): YearMonthRange =
    YearMonthRange.fromClosedRange(this, endInclusive)

operator fun Date.rangeTo(endInclusive: Date): DateRange = DateRange.fromClosedRange(this, endInclusive)

operator fun LocalDate.rangeTo(endInclusive: LocalDate): LocalDateRange =
    LocalDateRange.fromClosedRange(this, endInclusive)

operator fun LocalTime.rangeTo(endInclusive: LocalTime): LocalTimeRange =
    LocalTimeRange.fromClosedRange(this, endInclusive)

operator fun LocalDateTime.rangeTo(endInclusive: LocalDateTime): LocalDateTimeRange =
    LocalDateTimeRange.fromClosedRange(this, endInclusive)

operator fun OffsetTime.rangeTo(endInclusive: OffsetTime): OffsetTimeRange =
    OffsetTimeRange.fromClosedRange(this, endInclusive)

operator fun OffsetDateTime.rangeTo(endInclusive: OffsetDateTime): OffsetDateTimeRange =
    OffsetDateTimeRange.fromClosedRange(this, endInclusive)

operator fun ZonedDateTime.rangeTo(endInclusive: ZonedDateTime): ZonedDateTimeRange =
    ZonedDateTimeRange.fromClosedRange(this, endInclusive)

operator fun Instant.rangeTo(endInclusive: Instant): InstantRange =
    InstantRange.fromClosedRange(this, endInclusive)


@Suppress("UNCHECKED_CAST")
fun <T> TemporalRange<T>.asSequence(step: Duration): Sequence<T> where T : Temporal, T : Comparable<T> {
    return sequence {
        var current = first
        while (current <= last) {
            yield(current)
            current = current.plus(step) as T
        }
    }
}

fun YearMonthRange.asSequence(): Sequence<YearMonth> = sequence {
    var current = first
    while (current <= last) {
        yield(current)
        current = current.plusMonths(1)
    }
}