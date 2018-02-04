package com.github.debop.javatimes.ranges

import com.github.debop.javatimes.*
import java.sql.Time
import java.sql.Timestamp
import java.time.*
import java.time.temporal.ChronoUnit
import java.time.temporal.Temporal
import java.util.*
import kotlin.NoSuchElementException

typealias LocalDateProgression = TemporalProgression<LocalDate>
typealias LocalTimeProgression = TemporalProgression<LocalTime>
typealias LocalDateTimeProgression = TemporalProgression<LocalDateTime>
typealias OffsetDateTimeProgression = TemporalProgression<OffsetDateTime>
typealias ZonedDateTimeProgression = TemporalProgression<ZonedDateTime>

/**
 * A progression of value of `java.util.Date` subclass
 *
 * @property first first value of progression
 * @property last  last value of progression
 * @property step  progression step
 */
open class DateProgression<T : Date> internal constructor(start: T, endInclusive: T, val step: Duration) : Iterable<T> {
  init {
    require(step != Duration.ZERO) { "step must be non-zero" }
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

  companion object {
    @JvmStatic
    @JvmOverloads
    fun <T : Date> fromClosedRange(start: T, endInclusive: T, step: Duration = Duration.ofMillis(1L)): DateProgression<T> {
      return DateProgression(start, endInclusive, step)
    }
  }
}

open class TimeProgression(start: Time, endInclusive: Time, step: Duration) : DateProgression<Time>(start, endInclusive, step) {
  companion object {
    @JvmStatic
    fun fromClosed(start: Time, endInclusive: Time, step: Duration): TimeProgression =
        TimeProgression(start, endInclusive, step)
  }
}

open class TimestampProgression(start: Timestamp, endInclusive: Timestamp, step: Duration)
  : DateProgression<Timestamp>(start, endInclusive, step) {
  companion object {
    @JvmStatic
    fun fromClosed(start: Timestamp, endInclusive: Timestamp, step: Duration): TimestampProgression =
        TimestampProgression(start, endInclusive, step)
  }
}

/**
 * An iterator over a progression of values of type [java.util.Date]
 *
 * @property step the number by which the value is incremented on each step.
 */
internal class DateProgressionIterator<T : Date>(first: T, last: T, val step: Duration) : DateIterator<T>() {

  private val stepMillis: Long = step.toMillis()
  private val finalElement: T = last
  private var hasNext: Boolean = if (step > Duration.ZERO) first <= last else first >= last
  private var next: T = if (hasNext) first else finalElement

  override fun hasNext(): Boolean = hasNext

  @Suppress("UNCHECKED_CAST")
  override fun nextDate(): T {
    val value = next
    if (value == finalElement) {
      if (!hasNext) throw NoSuchElementException()
      hasNext = false
    } else {
      next = next.plus(stepMillis) as T
    }
    return value
  }
}

/**
 * A Progression of value of [java.time.temporal.Temporal]
 */
open class InstantProgression internal constructor(start: Instant,
                                                   endInclusive: Instant,
                                                   val step: Duration = Duration.ofMillis(1L)) : Iterable<Instant> {

  init {
    require(step != Duration.ZERO) { "step should not be ZERO" }
  }

  val first = start
  val last = getProgressionLastElement(start, endInclusive, step)

  override fun iterator(): Iterator<Instant> = InstantProgressionIterator(first, last, step)

  open fun isEmpty(): Boolean = if (step > Duration.ZERO) first > last else first < last

  override fun equals(other: Any?): Boolean {
    return if (other !is InstantProgression) false
    else (isEmpty() && other.isEmpty()) || (first == other.first && last == other.last && step == other.step)
  }

  override fun hashCode(): Int = if (isEmpty()) -1 else Objects.hash(first, last, step)

  override fun toString(): String = when {
    step == Duration.ZERO -> "$first..$last"
    step > Duration.ZERO  -> "$first..$last step $step"
    else                  -> "$first downTo $last step ${step.negated()}"
  }

  companion object {
    @JvmStatic
    @JvmOverloads
    fun fromClosedRange(start: Instant, endInclusive: Instant, step: Duration = Duration.ofMillis(1L)) =
        InstantProgression(start, endInclusive, step)
  }
}


/**
 * An iterator over a progression of values of type [java.time.temporal.Temporal].
 *
 * @property step the number by which the value is incremented on each step.
 */
internal class InstantProgressionIterator(first: Instant, last: Instant, val step: Duration = Duration.ofMillis(1L))
  : TemporalIterator<Instant>() {

  private val _finalElement = last
  private var _hasNext = if (step > Duration.ZERO) first <= last else first >= last
  private var _next = if (_hasNext) first else _finalElement

  override fun hasNext(): Boolean = _hasNext

  override fun nextTemporal(): Instant {
    val value = _next
    if (value == _finalElement) {
      if (!_hasNext) throw NoSuchElementException()
      _hasNext = false
    } else {
      _next = _next.plus(step)
    }
    return value
  }
}

/**
 * A Progression of value of [java.time.temporal.Temporal]
 */
open class TemporalProgression<T> internal constructor(start: T, endInclusive: T, val step: Duration = Duration.ofMillis(1L))
  : Iterable<T> where T : Temporal, T : Comparable<T> {

  init {
    require(step != Duration.ZERO) { "step should not be ZERO" }
  }

  val first = start
  val last = getProgressionLastElement(start, endInclusive, step)

  override fun iterator(): Iterator<T> = TemporalProgressionIterator(first, last, step)

  open fun isEmpty(): Boolean = if (step > Duration.ZERO) first > last else first < last

  override fun equals(other: Any?): Boolean {
    return if (other !is TemporalProgression<*>) false
    else (isEmpty() && other.isEmpty()) || (first == other.first && last == other.last && step == other.step)
  }

  override fun hashCode(): Int = if (isEmpty()) -1 else Objects.hash(first, last, step)

  override fun toString(): String = when {
    step == Duration.ZERO -> "$first..$last"
    step > Duration.ZERO  -> "$first..$last step $step"
    else                  -> "$first downTo $last step ${step.negated()}"
  }

  companion object {
    @JvmStatic
    @JvmOverloads
    fun <T> fromClosedRange(start: T, endInclusive: T, step: Duration = Duration.ofMillis(1L))
        : TemporalProgression<T> where T : Temporal, T : Comparable<T> {
      return TemporalProgression(start, endInclusive, step)
    }
  }
}


/**
 * An iterator over a progression of values of type [java.time.temporal.Temporal].
 *
 * @property step the number by which the value is incremented on each step.
 */
internal class TemporalProgressionIterator<T>(first: T, last: T, val step: Duration = Duration.ofMillis(1L))
  : TemporalIterator<T>() where T : Temporal, T : Comparable<T> {

  private val _finalElement = last
  private var _hasNext = if (step > Duration.ZERO) first <= last else first >= last
  private var _next = if (_hasNext) first else _finalElement

  override fun hasNext(): Boolean = _hasNext

  @Suppress("UNCHECKED_CAST")
  override fun nextTemporal(): T {
    val value = _next
    if (value == _finalElement) {
      if (!_hasNext) throw NoSuchElementException()
      _hasNext = false
    } else {
      _next = _next.plus(step) as T
    }
    return value
  }
}

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

internal fun getProgressionLastElement(start: Instant, end: Instant, step: Duration): Instant =
    when {
      step > Duration.ZERO -> end.minusMillis(differenceModulo(end.toEpochMilli(), start.toEpochMilli(), step.toMillis()))
      step < Duration.ZERO -> end.plusMillis(differenceModulo(start.toEpochMilli(), end.toEpochMilli(), -step.toMillis()))
      else                 -> throw IllegalArgumentException("step is zero.")
    }

internal fun getProgressionLastElement(start: Date, end: Date, step: Duration): Date =
    when {
      step > Duration.ZERO -> end - differenceModulo(end.time, start.time, step.toMillis())
      step < Duration.ZERO -> end + differenceModulo(start.time, end.time, -step.toMillis())
      else                 -> throw IllegalArgumentException("step is zero.")
    }


@Suppress("UNCHECKED_CAST")
internal fun <T> getProgressionLastElement(start: T, end: T, step: Duration): T where T : Temporal, T : Comparable<T> =
    when {
      step > Duration.ZERO -> end.minus(differenceModulo(end.toEhpochMillis(), start.toEhpochMillis(), step.toMillis()), ChronoUnit.MILLIS) as T
      step < Duration.ZERO -> end.plus(differenceModulo(start.toEhpochMillis(), end.toEhpochMillis(), -step.toMillis()), ChronoUnit.MILLIS) as T
      else                 -> throw IllegalArgumentException("step is zero")
    }
