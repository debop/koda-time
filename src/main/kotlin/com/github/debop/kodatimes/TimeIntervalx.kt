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
@file:Suppress("unused")

package com.github.debop.kodatimes

import com.github.debop.kodatimes.PeriodUnit.*
import org.joda.time.*
import kotlin.coroutines.experimental.buildSequence


fun ReadableInterval.millis(): Long = this.toDurationMillis()

infix fun ReadableInterval.step(period: ReadablePeriod): Sequence<DateTime> {
  require(period.toPeriod().toStandardSeconds() > Period.seconds(0).toStandardSeconds()) {
    "period must have postive value [$period]"
  }

  return generateSequence(start) { it + period }.takeWhile { it <= end }
}

@JvmOverloads
suspend fun ReadableInterval.buildSequence(periodUnit: PeriodUnit = DAY, step: Int = 1): Sequence<DateTime> {
  require(step > 0) { "step must postive value [$step]" }

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


/** 기간을 초 단위로 열거합니다. */
@JvmOverloads
fun ReadableInterval.seconds(step: Int = 1): Sequence<DateTime> {
  require(step > 0) { "step must postive value [$step]" }
  return generateSequence(start) { it + step.seconds() }.takeWhile { it <= end }
}

@JvmOverloads
suspend fun ReadableInterval.buildSeconds(step: Int = 1): Sequence<DateTime> = buildSequence {
  require(step > 0) { "step must postive value [$step]" }

  var current = start
  val increment = step.seconds()
  while (current <= end) {
    yield(current)
    current += increment
  }
}

/** 기간을 분 단위로 열거합니다. */
@JvmOverloads
fun ReadableInterval.minutes(step: Int = 1): Sequence<DateTime> {
  require(step > 0) { "step must postive value [$step]" }

  return generateSequence(start) { it + step.minutes() }.takeWhile { it <= end }
}

@JvmOverloads
suspend fun ReadableInterval.buildMinutes(step: Int = 1): Sequence<DateTime> = buildSequence {
  require(step > 0) { "step must postive value [$step]" }

  var current = start
  val increment = step.minutes()
  while (current <= end) {
    yield(current)
    current += increment
  }
}

/** 기간을 시간 단위로 열거합니다. */
@JvmOverloads
fun ReadableInterval.hours(step: Int = 1): Sequence<DateTime> {
  require(step > 0) { "step must postive value [$step]" }
  return generateSequence(start) { it + step.hours() }.takeWhile { it <= end }
}

@JvmOverloads
suspend fun ReadableInterval.buildHours(step: Int = 1): Sequence<DateTime> = buildSequence {
  require(step > 0) { "step must postive value [$step]" }

  var current = start
  val increment = step.hours()
  while (current <= end) {
    yield(current)
    current += increment
  }
}

/** 기간을 일 단위로 열거합니다. */
@JvmOverloads
fun ReadableInterval.days(step: Int = 1): Sequence<DateTime> {
  require(step > 0) { "step must postive value [$step]" }
  return generateSequence(start.startOfDay()) { it + step.days() }.takeWhile { it <= end }
}

@JvmOverloads
suspend fun ReadableInterval.buildDays(step: Int = 1): Sequence<DateTime> = buildSequence {
  require(step > 0) { "step must postive value [$step]" }
  var current = start
  val increment = step.days()
  while (current <= end) {
    yield(current)
    current += increment
  }
}

/** 기간을 주 단위로 열거합니다. */
@JvmOverloads
fun ReadableInterval.weeks(step: Int = 1): Sequence<DateTime> {
  require(step > 0) { "step must postive value [$step]" }
  return generateSequence(start.startOfWeek()) { it + step.weeks() }.takeWhile { it <= end }
}

@JvmOverloads
suspend fun ReadableInterval.buildWeeks(step: Int = 1): Sequence<DateTime> = buildSequence {
  var current = start
  val increment = step.weeks()
  while (current <= end) {
    yield(current)
    current += increment
  }
}

/** 기간을 월 단위로 열거합니다. */
@JvmOverloads
fun ReadableInterval.months(step: Int = 1): Sequence<DateTime> {
  require(step > 0) { "step must postive value [$step]" }
  return generateSequence(start.startOfMonth()) { it + step.months() }.takeWhile { it <= end }
}

@JvmOverloads
suspend fun ReadableInterval.buildMonths(step: Int = 1): Sequence<DateTime> = buildSequence {
  require(step > 0) { "step must postive value [$step]" }

  var current = start
  val increment = step.months()
  while (current <= end) {
    yield(current)
    current += increment
  }
}

/** 기간을 년 단위로 열거합니다 */
@JvmOverloads
fun ReadableInterval.years(step: Int = 1): Sequence<DateTime> {
  require(step > 0) { "step must postive value [$step]" }
  return generateSequence(start.startOfYear()) { it + step.years() }.takeWhile { it <= end }
}

@JvmOverloads
suspend fun ReadableInterval.buildYears(step: Int = 1): Sequence<DateTime> = buildSequence {
  require(step > 0) { "step must postive value [$step]" }

  var current = start
  val increment = step.years()
  while (current <= end) {
    yield(current)
    current += increment
  }
}

//
// Adopted Kotlin 1.2
// https://github.com/Kotlin/KEEP/blob/master/proposals/stdlib/window-sliding.md
//

fun ReadableInterval.chunk(size: Int, periodUnit: PeriodUnit): Sequence<List<DateTime>> {
  require(size > 0) { "chunk size must postive value [$size]" }

  return when (periodUnit) {
    PeriodUnit.YEAR   -> chunkYear(size)
    PeriodUnit.MONTH  -> chunkMonth(size)
    PeriodUnit.WEEK   -> chunkWeek(size)
    PeriodUnit.DAY    -> chunkDay(size)
    PeriodUnit.HOUR   -> chunkHour(size)
    PeriodUnit.MINUTE -> chunkMinute(size)
    PeriodUnit.SECOND -> chunkSecond(size)
    else              -> throw UnsupportedOperationException("Unsupported period unit [$periodUnit]")
  }
}

/**
 * partitions source into blocks of the given size
 */
fun ReadableInterval.chunkYear(size: Int): Sequence<List<DateTime>> {

  require(size > 0) { "chunk size must postive value [$size]" }

  return buildSequence {
    var current = start.startOfYear()
    val increment = size.years()

    while (current < end) {
      yield(List(size) { current + it.years() }.takeWhile { it < end })
      current += increment
    }
  }
}

fun ReadableInterval.chunkMonth(size: Int): Sequence<List<DateTime>> {

  require(size > 0) { "chunk size must postive value [$size]" }

  return buildSequence {
    var current = start.startOfMonth()
    val increment = size.months()

    while (current < end) {
      yield(List(size) { current + it.months() }.takeWhile { it < end })
      current += increment
    }
  }
}

fun ReadableInterval.chunkWeek(size: Int): Sequence<List<DateTime>> {

  require(size > 0) { "chunk size must postive value [$size]" }

  return buildSequence {
    var current = start.startOfWeek()
    val increment = size.weeks()

    while (current < end) {
      yield(List(size) { current + it.weeks() }.takeWhile { it < end })
      current += increment
    }
  }
}

fun ReadableInterval.chunkDay(size: Int): Sequence<List<DateTime>> {

  require(size > 0) { "chunk size must postive value [$size]" }

  return buildSequence {
    var current = start.startOfDay()
    val increment = size.days()

    while (current < end) {
      yield(List(size) { current + it.days() }.takeWhile { it < end })
      current += increment
    }
  }
}

fun ReadableInterval.chunkHour(size: Int): Sequence<List<DateTime>> {

  require(size > 0) { "chunk size must postive value [$size]" }

  return buildSequence {
    var current = start.trimToHour()
    val increment = size.hours()

    while (current < end) {
      yield(List(size) { current + it.hours() }.takeWhile { it < end })
      current += increment
    }
  }
}

fun ReadableInterval.chunkMinute(size: Int): Sequence<List<DateTime>> {

  require(size > 0) { "chunk size must postive value [$size]" }

  return buildSequence {
    var current = start.trimToMinute()
    val increment = size.minutes()

    while (current < end) {
      yield(List(size) { current + it.minutes() }.takeWhile { it < end })
      current += increment
    }
  }
}

fun ReadableInterval.chunkSecond(size: Int): Sequence<List<DateTime>> {

  require(size > 0) { "chunk size must postive value [$size]" }

  return buildSequence {
    var current = start.trimToSecond()
    val increment = size.seconds()

    while (current < end) {
      yield(List(size) { current + it.seconds() }.takeWhile { it < end })
      current += increment
    }
  }
}

/**
 * takes a window of the given size and moves it along  with the given step (like Scala sliding)
 */
@JvmOverloads
fun ReadableInterval.windowedPeriod(size: Int, step: Int = 1, periodUnit: PeriodUnit = PeriodUnit.YEAR): Sequence<List<DateTime>> {
  check(step > 0) { "step must positive number. [$step] " }
  if (size <= 0) {
    return emptySequence()
  }

  return when (periodUnit) {
    PeriodUnit.YEAR   -> windowedYear(size, step)
    PeriodUnit.MONTH  -> windowedMonth(size, step)
    PeriodUnit.WEEK   -> windowedWeek(size, step)
    PeriodUnit.DAY    -> windowedDay(size, step)
    PeriodUnit.HOUR   -> windowedHour(size, step)
    PeriodUnit.MINUTE -> windowedMinute(size, step)
    PeriodUnit.SECOND -> windowedSecond(size, step)
    else              -> throw UnsupportedOperationException("Not supported period unit. [$periodUnit]")
  }
}

@JvmOverloads
fun ReadableInterval.windowedYear(size: Int, step: Int = 1): Sequence<List<DateTime>> = buildSequence {
  check(step > 0) { "step must positive number. [$step] " }
  if (size <= 0) {
    return@buildSequence
  }

  var current = start.startOfYear()
  val increment = step.years()

  // end is exclusive
  while (current < end) {
    yield(List(size) { current + it.years() }.takeWhile { it < end })
    current += increment
  }
}

@JvmOverloads
fun ReadableInterval.windowedMonth(size: Int, step: Int = 1): Sequence<List<DateTime>> = buildSequence {
  check(step > 0) { "step must positive number. [$step] " }
  if (size <= 0) {
    return@buildSequence
  }

  var current = start.startOfMonth()
  val increment = step.months()

  // end is exclusive
  while (current < end) {
    yield(List(size) { current + it.months() }.takeWhile { it < end })
    current += increment
  }
}

@JvmOverloads
fun ReadableInterval.windowedWeek(size: Int, step: Int = 1): Sequence<List<DateTime>> = buildSequence {
  check(step > 0) { "step must positive number. [$step] " }
  if (size <= 0) {
    return@buildSequence
  }

  var current = start.startOfWeek()
  val increment = step.weeks()

  // end is exclusive
  while (current < end) {
    yield(List(size) { current + it.weeks() }.takeWhile { it < end })
    current += increment
  }
}

@JvmOverloads
fun ReadableInterval.windowedDay(size: Int, step: Int = 1): Sequence<List<DateTime>> = buildSequence {
  check(step > 0) { "step must positive number. [$step] " }
  if (size <= 0) {
    return@buildSequence
  }
  var current = start.startOfDay()
  val increment = step.days()

  while (current < end) {
    yield(List(size) { current + it.days() }.takeWhile { it < end })
    current += increment
  }
}

@JvmOverloads
fun ReadableInterval.windowedHour(size: Int, step: Int = 1): Sequence<List<DateTime>> = buildSequence {
  check(step > 0) { "step must positive number. [$step] " }
  if (size <= 0) {
    return@buildSequence
  }
  var current = start.trimToHour()
  val increment = step.hours()

  while (current < end) {
    yield(List(size) { current + it.hours() }.takeWhile { it < end })
    current += increment
  }
}

@JvmOverloads
fun ReadableInterval.windowedMinute(size: Int, step: Int = 1): Sequence<List<DateTime>> = buildSequence {
  check(step > 0) { "step must positive number. [$step] " }
  if (size <= 0) {
    return@buildSequence
  }

  var current = start.trimToMinute()
  val increment = step.minutes()

  while (current < end) {
    yield(List(size) { current + it.minutes() }.takeWhile { it < end })
    current += increment
  }
}

@JvmOverloads
fun ReadableInterval.windowedSecond(size: Int, step: Int = 1): Sequence<List<DateTime>> = buildSequence {
  check(step > 0) { "step must positive number. [$step] " }
  if (size <= 0) {
    return@buildSequence
  }

  var current = start.trimToSecond()
  val increment = step.seconds()
  while (current < end) {
    yield(List(size) { current + it.seconds() }.takeWhile { it < end })
    current += increment
  }
}


fun ReadableInterval.zipWithNext(periodUnit: PeriodUnit): Sequence<Pair<DateTime, DateTime>> {
  return when (periodUnit) {
    PeriodUnit.YEAR   -> zipWithNextYear()
    PeriodUnit.MONTH  -> zipWithNextMonth()
    PeriodUnit.WEEK   -> zipWithNextWeek()
    PeriodUnit.DAY    -> zipWithNextDay()
    PeriodUnit.HOUR   -> zipWithNextHour()
    PeriodUnit.MINUTE -> zipWithNextMinute()
    PeriodUnit.SECOND -> zipWithNextSecond()
    else              -> throw UnsupportedOperationException("Not supported period unit. [$periodUnit]")
  }
}

/**
 * pairwise operation which applies the immediate transform on an each pair
 */
fun ReadableInterval.zipWithNextYear(): Sequence<Pair<DateTime, DateTime>> = buildSequence {
  var current = start.startOfYear()
  val increment = 1.years()
  val limit = end - increment

  while (current < limit) {
    yield(Pair(current, current + increment))
    current += increment
  }
}

fun ReadableInterval.zipWithNextMonth(): Sequence<Pair<DateTime, DateTime>> = buildSequence {
  var current = start.startOfMonth()
  val increment = 1.months()
  val limit = end - increment

  while (current < limit) {
    yield(Pair(current, current + increment))
    current += increment
  }
}

fun ReadableInterval.zipWithNextWeek(): Sequence<Pair<DateTime, DateTime>> = buildSequence {
  var current = start.startOfWeek()
  val increment = 1.weeks()
  val limit = end - increment

  while (current < limit) {
    yield(Pair(current, current + increment))
    current += increment
  }
}

fun ReadableInterval.zipWithNextDay(): Sequence<Pair<DateTime, DateTime>> = buildSequence {
  var current = start.startOfDay()
  val increment = 1.days()
  val limit = end - increment

  while (current < limit) {
    yield(Pair(current, current + increment))
    current += increment
  }
}


fun ReadableInterval.zipWithNextHour(): Sequence<Pair<DateTime, DateTime>> = buildSequence {
  var current = start.trimToHour()
  val increment = 1.hours()
  val limit = end - increment

  while (current < limit) {
    yield(Pair(current, current + increment))
    current += increment
  }
}

fun ReadableInterval.zipWithNextMinute(): Sequence<Pair<DateTime, DateTime>> = buildSequence {
  var current = start.trimToMinute()
  val increment = 1.minutes()
  val limit = end - increment

  while (current < limit) {
    yield(Pair(current, current + increment))
    current += increment
  }
}

fun ReadableInterval.zipWithNextSecond(): Sequence<Pair<DateTime, DateTime>> = buildSequence {
  var current = start.trimToSecond()
  val increment = 1.seconds()
  val limit = end - increment

  while (current < limit) {
    yield(Pair(current, current + increment))
    current += increment
  }
}