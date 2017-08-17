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
@file:JvmName("TimeIntervalx")

package com.github.debop.kodatimes

import com.github.debop.kodatimes.PeriodUnit.*
import org.joda.time.DateTime
import org.joda.time.ReadableInterval
import org.joda.time.ReadablePeriod
import kotlin.coroutines.experimental.buildSequence

fun ReadableInterval.millis(): Long = this.toDurationMillis()

infix fun ReadableInterval.step(period: ReadablePeriod): Sequence<DateTime> {
  return generateSequence(start) { it + period }.takeWhile { it <= end }
}

/** 기간을 초 단위로 열거합니다. */
@JvmOverloads
fun ReadableInterval.seconds(step: Int = 1): Sequence<DateTime> {
  return generateSequence(start) { it + step.seconds() }.takeWhile { it <= end }
}

@JvmOverloads
suspend fun ReadableInterval.buildSeconds(step: Int = 1): Sequence<DateTime> = buildSequence {
  var current = start
  while (current <= end) {
    yield(current)
    current += step.seconds()
  }
}

/** 기간을 분 단위로 열거합니다. */
@JvmOverloads
fun ReadableInterval.minutes(step: Int = 1): Sequence<DateTime> {
  return generateSequence(start) { it + step.minutes() }.takeWhile { it <= end }
}

@JvmOverloads
suspend fun ReadableInterval.buildMinutes(step: Int = 1): Sequence<DateTime> = buildSequence {
  var current = start
  while (current <= end) {
    yield(current)
    current += step.minutes()
  }
}

/** 기간을 시간 단위로 열거합니다. */
@JvmOverloads
fun ReadableInterval.hours(step: Int = 1): Sequence<DateTime> {
  return generateSequence(start) { it + step.hours() }.takeWhile { it <= end }
}

@JvmOverloads
suspend fun ReadableInterval.buildHours(step: Int = 1): Sequence<DateTime> = buildSequence {
  var current = start
  while (current <= end) {
    yield(current)
    current += step.hours()
  }
}

/** 기간을 일 단위로 열거합니다. */
@JvmOverloads
fun ReadableInterval.days(step: Int = 1): Sequence<DateTime> {
  return generateSequence(start.startOfDay()) { it + step.days() }.takeWhile { it <= end }
}

@JvmOverloads
suspend fun ReadableInterval.buildDays(step: Int = 1): Sequence<DateTime> = buildSequence {
  var current = start
  while (current <= end) {
    yield(current)
    current += step.days()
  }
}

/** 기간을 주 단위로 열거합니다. */
@JvmOverloads
fun ReadableInterval.weeks(step: Int = 1): Sequence<DateTime> {
  return generateSequence(start.startOfWeek()) { it + step.weeks() }.takeWhile { it <= end }
}

@JvmOverloads
suspend fun ReadableInterval.buildWeeks(step: Int = 1): Sequence<DateTime> = buildSequence {
  var current = start
  while (current <= end) {
    yield(current)
    current += step.weeks()
  }
}

/** 기간을 월 단위로 열거합니다. */
@JvmOverloads
fun ReadableInterval.months(step: Int = 1): Sequence<DateTime> {
  return generateSequence(start.startOfMonth()) { it + step.months() }.takeWhile { it <= end }
}

@JvmOverloads
suspend fun ReadableInterval.buildMonths(step: Int = 1): Sequence<DateTime> = buildSequence {
  var current = start
  while (current <= end) {
    yield(current)
    current += step.months()
  }
}

/** 기간을 년 단위로 열거합니다 */
@JvmOverloads
fun ReadableInterval.years(step: Int = 1): Sequence<DateTime> {
  return generateSequence(start.startOfYear()) { it + step.years() }.takeWhile { it <= end }
}

@JvmOverloads
suspend fun ReadableInterval.buildYears(step: Int = 1): Sequence<DateTime> = buildSequence {
  var current = start
  while (current <= end) {
    yield(current)
    current += step.years()
  }
}

@JvmOverloads
suspend fun ReadableInterval.buildSequence(periodUnit: PeriodUnit = DAY, step: Int = 1): Sequence<DateTime> {
  return when (periodUnit) {
    SECOND -> buildSeconds(step)
    MINUTE -> buildMinutes(step)
    HOUR   -> buildHours(step)
    DAY    -> buildDays(step)
    WEEK   -> buildWeeks(step)
    MONTH  -> buildMonths(step)
    YEAR   -> buildYears(step)
    else   -> throw UnsupportedOperationException("Not supported period unit. periodUnit=$periodUnit")
  }
}


//
// Adopted Kotlin 1.2
// https://github.com/Kotlin/KEEP/blob/master/proposals/stdlib/window-sliding.md
//

/**
 * partitions source into blocks of the given size
 */
fun ReadableInterval.chunkYear(size: Int): Sequence<List<DateTime>> = buildSequence {
  val startYear = start.startOfYear()
  var current = start.year
  val limit = end.year

  while (current < limit) {
    yield(List(Math.min(size, limit - current)) { dateTimeOf(current + it, 1, 1) })
    current += size
  }
}

inline fun <R> ReadableInterval.chunkYear(size: Int, crossinline transform: (List<DateTime>) -> R): Sequence<R> =
    chunkYear(size).map { transform(it) }


/**
 * takes a window of the given size and moves it along  with the given step (like Scala sliding)
 */
fun ReadableInterval.windowedYear(size: Int, step: Int = 1): Sequence<List<DateTime>> = buildSequence {
  val startYear = start.startOfYear()
  var current = start.year
  val limit = end.year - size + 1

  while (current <= limit) {
    yield(List(size) { dateTimeOf(current + it, 1, 1) })
    current += step
  }
}

inline fun <R> ReadableInterval.windowedYear(size: Int, step: Int = 1, crossinline transform: (List<DateTime>) -> R): Sequence<R> =
    windowedYear(size, step).map { transform(it) }

/**
 * pairwise operation which applies the immediate transform on an each pair
 */
fun ReadableInterval.zipWithNext(): Sequence<Pair<DateTime, DateTime>> = buildSequence {
  val startYear = start.startOfYear()
  var current = start.year
  val limit = end.year

  while (current < limit) {
    yield(Pair(dateTimeOf(current), dateTimeOf(current + 1)))
    current++
  }
}

inline fun <R> ReadableInterval.zipWithNext(crossinline transform: (DateTime, DateTime) -> R): Sequence<R> =
    zipWithNext().map { (a, b) -> transform(a, b) }

