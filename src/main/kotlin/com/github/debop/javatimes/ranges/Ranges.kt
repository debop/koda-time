@file:Suppress("ConvertTwoComparisonsToRangeCheck")

package com.github.debop.javatimes.ranges

import com.github.debop.javatimes.dateOf
import java.sql.Time
import java.sql.Timestamp
import java.time.*
import java.time.temporal.Temporal
import java.util.*

typealias LocalDateRange = TemporalRange<LocalDate>
typealias LocalTimeRange = TemporalRange<LocalTime>
typealias LocalDateTimeRange = TemporalRange<LocalDateTime>
typealias OffsetDateTimeRange = TemporalRange<OffsetDateTime>
typealias ZonedDateTimeRange = TemporalRange<ZonedDateTime>


operator fun Date.rangeTo(endInclusive: Date): DateRange = DateRange(this, endInclusive)

operator fun Instant.rangeTo(endInclusive: Instant): InstantRange = InstantRange(this, endInclusive)

operator fun LocalDateTime.rangeTo(endInclusive: LocalDateTime): LocalDateTimeRange = LocalDateTimeRange(this, endInclusive)

operator fun OffsetDateTime.rangeTo(endInclusive: OffsetDateTime): OffsetDateTimeRange = OffsetDateTimeRange(this, endInclusive)

operator fun ZonedDateTime.rangeTo(endInclusive: ZonedDateTime): ZonedDateTimeRange = ZonedDateTimeRange(this, endInclusive)

operator fun LocalDate.rangeTo(endInclusive: LocalDate): LocalDateRange = TemporalRange.of(this, endInclusive)
operator fun LocalTime.rangeTo(endInclusive: LocalTime): LocalTimeRange = TemporalRange.of(this, endInclusive)


/**
 * A range of `java.util.Date`
 */
open class DateRange(start: Date, endInclusive: Date)
  : DateProgression<Date>(start, endInclusive, Duration.ofMillis(1L)), ClosedRange<Date> {

  /** start date of range */
  override val start: Date get() = first

  /** end date of range */
  override val endInclusive: Date get() = last

  override fun contains(value: Date): Boolean = first <= value && value <= last

  override fun isEmpty(): Boolean = first > last

  override fun toString(): String = "$first..$last"

  companion object {
    @JvmField val EMPTY: DateRange = DateRange(dateOf(1L), dateOf(0L))
  }
}

class TimeRange(start: Time, endInclusive: Time) : DateRange(start, endInclusive) {
  companion object {
    @JvmField val EMPTY: TimeRange = TimeRange(Time(1), Time(0))
  }
}

class TimestampRange(start: Timestamp, endInclusive: Timestamp) : DateRange(start, endInclusive) {
  companion object {
    @JvmField val EMPTY: TimestampRange = TimestampRange(Timestamp(1), Timestamp(0))
  }
}

/**
 * A range of [java.time.Instant]
 */
class InstantRange(start: Instant, endInclusive: Instant) : InstantProgression(start, endInclusive), ClosedRange<Instant> {

  override val start: Instant get() = first

  override val endInclusive: Instant = last

  override fun contains(value: Instant): Boolean = first <= value && value <= last

  override fun isEmpty(): Boolean = first > last

  override fun toString(): String = "$first..$last"

  companion object {
    @JvmField val EMPTY = InstantRange(Instant.ofEpochMilli(1), Instant.ofEpochMilli(0))
  }
}

/**
 * A range of Temporal
 */
open class TemporalRange<T>(start: T, end: T)
  : TemporalProgression<T>(start, end, Duration.ofMillis(1L)), ClosedRange<T> where T : Temporal, T : Comparable<T> {

  override val start: T get() = first

  override val endInclusive: T = last

  override fun contains(value: T): Boolean = first <= value && value <= last

  override fun isEmpty(): Boolean = first > last

  override fun toString(): String = "$first..$last"

  companion object {

    @JvmField val EMPTY = of<LocalDateTime>(LocalDateTime.MAX, LocalDateTime.MIN)

    @JvmStatic
    fun <T> of(start: T, end: T): TemporalRange<T> where T : Temporal, T : Comparable<T> =
        TemporalRange<T>(start, end)
  }
}



