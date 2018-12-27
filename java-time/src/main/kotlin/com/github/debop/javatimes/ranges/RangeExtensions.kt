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


operator fun Year.rangeTo(endInclusive: Year): YearRange = YearRange(this, endInclusive)
operator fun YearMonth.rangeTo(endInclusive: YearMonth): YearMonthRange = YearMonthRange(this, endInclusive)

operator fun Date.rangeTo(endInclusive: Date): DateRange = DateRange(this, endInclusive)

operator fun LocalDate.rangeTo(endInclusive: LocalDate): LocalDateRange = LocalDateRange(this, endInclusive)
operator fun LocalTime.rangeTo(endInclusive: LocalTime): LocalTimeRange = LocalTimeRange(this, endInclusive)

operator fun LocalDateTime.rangeTo(endInclusive: LocalDateTime): LocalDateTimeRange =
    LocalDateTimeRange(this, endInclusive)

operator fun OffsetTime.rangeTo(endInclusive: OffsetTime): OffsetTimeRange =
    OffsetTimeRange(this, endInclusive)

operator fun OffsetDateTime.rangeTo(endInclusive: OffsetDateTime): OffsetDateTimeRange =
    OffsetDateTimeRange(this, endInclusive)

operator fun ZonedDateTime.rangeTo(endInclusive: ZonedDateTime): ZonedDateTimeRange =
    ZonedDateTimeRange(this, endInclusive)

operator fun Instant.rangeTo(endInclusive: Instant): InstantRange = InstantRange(this, endInclusive)


@Suppress("UNCHECKED_CAST")
fun <T> TemporalRange<T>.asSequence(step: Duration): Sequence<T> where T : Temporal, T : Comparable<T> {
    return sequence {
        var current = first
        while(current <= last) {
            yield(current)
            current = current.plus(step) as T
        }
    }
}

fun YearMonthRange.asSequence(): Sequence<YearMonth> = sequence {
    var current = first
    while(current <= last) {
        yield(current)
        current = current.plusMonths(1)
    }
}