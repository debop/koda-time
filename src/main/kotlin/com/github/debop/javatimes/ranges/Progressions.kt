package com.github.debop.javatimes.ranges

import com.github.debop.javatimes.JavaDateIterator
import com.github.debop.javatimes.JavaInstantIterator
import com.github.debop.javatimes.minus
import com.github.debop.javatimes.plus
import java.time.Duration
import java.time.Instant
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

/**
 * A progression of value of `java.util.Date` subclass
 *
 * @property first first value of progression
 * @property last  last value of progression
 * @property step  progression step
 */
abstract class JavaDateProgression<T : Date>(start: T, endInclusive: T, val step: Duration) : Iterable<T> {
  init {
    require(step != Duration.ZERO) { "step must be non-zero" }
  }

  val first: T = start
  abstract val last: T

  open fun isEmpty(): Boolean = if (step > Duration.ZERO) first > last else first < last

  override fun equals(other: Any?): Boolean =
      other is JavaDateProgression<*> &&
      ((isEmpty() && other.isEmpty()) ||
       (first == other.first && last == other.last && step == other.step))

  override fun hashCode(): Int =
      if (isEmpty()) -1
      else Objects.hash(first, last, step)

  override fun toString(): String =
      if (step > Duration.ZERO) "$first..$last step $step"
      else "$first downTo $last step ${step.negated()}"
}

/**
 * A Progression of value of `java.util.Date`
 */
open class DateProgression internal constructor(start: Date, endInclusive: Date, step: Duration) :
    JavaDateProgression<Date>(start, endInclusive, step), Iterable<Date> {

  override val last: Date = getProgressionLastElement(start, endInclusive, step)

  override fun iterator(): Iterator<Date> = DateProgressionIterator(first, last, step)

  override fun equals(other: Any?): Boolean =
      other is DateProgression &&
      ((isEmpty() && other.isEmpty()) ||
       (first == other.first && last == other.last && step == other.step))

  companion object {
    @JvmStatic
    fun fromClosedRange(start: Date, endInclusive: Date, step: Duration): DateProgression =
        DateProgression(start, endInclusive, step)
  }
}

/**
 * An iterator over a progression of values of type `java.util.Date`.
 *
 * @property step the number by which the value is incremented on each step.
 */
internal class DateProgressionIterator(first: Date, last: Date, val step: Duration) : JavaDateIterator<Date>() {

  private val stepMillis: Long = step.toMillis()
  private val finalElement: Date = last
  private var hasNext: Boolean = if (step > Duration.ZERO) first <= last else first >= last
  private var next: Date = if (hasNext) first else finalElement


  override fun hasNext(): Boolean = hasNext

  override fun nextJavaDate(): Date {
    val value = next
    if (value == finalElement) {
      if (!hasNext) throw NoSuchElementException()
      hasNext = false
    } else {
      next += stepMillis
    }
    return value
  }
}

/**
 * Progression of `java.util.Instant`
 */
open class JavaInstantProgression internal constructor(start: Instant, endInclusive: Instant, val step: Duration) : Iterable<Instant> {

  val first: Instant = start
  val last: Instant = getProgressionLastElement(start, endInclusive, step)

  override fun iterator(): Iterator<Instant> = JavaInstantProgressionIterator(first, last, step)

  open fun isEmpty(): Boolean = if (step > Duration.ZERO) first > last else first < last

  override fun equals(other: Any?): Boolean =
      other is JavaInstantProgression &&
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
    fun fromClosedRange(start: Instant, endInclusive: Instant, step: Duration): JavaInstantProgression =
        JavaInstantProgression(start, endInclusive, step)
  }

}

internal class JavaInstantProgressionIterator(first: Instant, last: Instant, val step: Duration) : JavaInstantIterator() {

  private val finalElement: Instant = last
  private var hasNext: Boolean = if (step > Duration.ZERO) first <= last else first >= last
  private var next: Instant = if (hasNext) first else finalElement

  override fun hasNext(): Boolean = hasNext

  override fun nextInstant(): Instant {
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
