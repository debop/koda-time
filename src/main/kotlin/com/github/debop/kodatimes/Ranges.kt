package com.github.debop.kodatimes

import com.github.debop.javatimes.instantOf
import com.github.debop.javatimes.toLocalDateTime
import org.joda.time.DateTime
import org.joda.time.Duration
import org.joda.time.Instant
import org.joda.time.ReadableInstant
import java.util.*

/**
 *  `org.joda.time.DateTime` range
 *
 *  @property start Start of range
 *  @property endInclusive end of range
 */
class DateTimeRange(start: DateTime, endInclusive: DateTime)
  : DateTimeProgression(start, endInclusive, 1.seconds()), ClosedRange<DateTime> {

  override val start: DateTime get() = first
  override val endInclusive: DateTime get() = last

  override fun contains(value: DateTime): Boolean = first <= value && value <= last

  override fun isEmpty(): Boolean = first > last

  override fun equals(other: Any?): Boolean =
      when (other) {
        is DateTimeRange -> isEmpty() && other.isEmpty() || (first == other.first && last == other.last)
        else             -> false
      }

  override fun hashCode(): Int = Objects.hash(first, last)

  override fun toString(): String = "$first..$last"

  companion object {
    @JvmField val EMPTY: DateTimeRange = DateTimeRange(dateTimeOf(1, 1, 1), dateTimeOf(0, 1, 1))
  }
}


/**
 * `org.joda.time.Instant` range
 *
 *  @property start Start of range
 *  @property endInclusive end of range
 */
class InstantRange(start: Instant, endInclusive: Instant)
  : InstantProgression(start, endInclusive, 1.seconds()), ClosedRange<Instant> {

  override val start: Instant get() = first
  override val endInclusive: Instant get() = last

  override fun contains(value: Instant): Boolean = first <= value && value <= last

  override fun isEmpty(): Boolean = first > last

  override fun equals(other: Any?): Boolean =
      when (other) {
        is InstantRange -> isEmpty() && other.isEmpty() || (first == other.first && last == other.last)
        else            -> false
      }

  override fun hashCode(): Int = Objects.hash(first, last)

  override fun toString(): String = "$first..$last"

  companion object {
    @JvmField val EMPTY: InstantRange = InstantRange(Instant(1), Instant(0))
  }
}
