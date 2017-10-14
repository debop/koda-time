package com.github.debop.javatimes

import java.time.Instant
import java.time.chrono.ChronoLocalDate
import java.time.chrono.ChronoLocalDateTime
import java.util.*

/**
 * A range of `java.util.ChronoLocalDate`
 */
class ChronoLocalDateRange<T : ChronoLocalDate>(start: T, endInclusive: T) : ClosedRange<T> {
  override val endInclusive: T
    get() = TODO("not implemented")
  override val start: T
    get() = TODO("not implemented")
}

/**
 * A range of `java.util.ChronoLocalDateTime`
 */
class ChronoLocalDateTimeRange<T : ChronoLocalDateTime<*>>(start: T, endInclusive: T) : ClosedRange<T> {

  override val start: T
    get() = TODO("not implemented")
  override val endInclusive: T
    get() = TODO("not implemented")

}

/**
 * A range of `java.util.Instant`
 */
class InstantRange(start: Instant, endInclusive: Instant) : ClosedRange<Instant> {
  override val start: Instant
    get() = TODO("not implemented")

  override val endInclusive: Instant
    get() = TODO("not implemented")
}

/**
 * A range of `java.util.Date`
 */
class DateRange(start: Date, endInclusive: Date) : ClosedRange<Date> {

  override val start: Date
    get() = TODO("not implemented")

  override val endInclusive: Date
    get() = TODO("not implemented")

}
