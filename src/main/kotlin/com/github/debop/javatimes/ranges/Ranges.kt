@file:Suppress("ConvertTwoComparisonsToRangeCheck")

package com.github.debop.javatimes.ranges

import com.github.debop.javatimes.dateOf
import java.time.Duration
import java.time.Instant
import java.util.*

operator fun Date.rangeTo(endInclusive: Date): DateRange = DateRange(this, endInclusive)

operator fun Instant.rangeTo(endInclusive: Instant): JavaInstantRange = JavaInstantRange(this, endInclusive)

/**
 * A range of `java.util.Date`
 */
class DateRange(start: Date, endInclusive: Date)
  : DateProgression(start, endInclusive, Duration.ofMillis(1L)), ClosedRange<Date> {

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


/**
 * A range of `java.util.Instant`
 */
class JavaInstantRange(start: Instant, endInclusive: Instant)
  : JavaInstantProgression(start, endInclusive, Duration.ofMillis(1)), ClosedRange<Instant> {

  /** start date of range */
  override val start: Instant get() = first

  /** end date of range */
  override val endInclusive: Instant get() = last

  override fun contains(value: Instant): Boolean = first <= value && value <= last

  override fun isEmpty(): Boolean = first > last

  override fun toString(): String = "$first..$last"

  companion object {
    @JvmField val EMPTY: JavaInstantRange = JavaInstantRange(Instant.ofEpochMilli(1L), Instant.ofEpochMilli(0))
  }
}

