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
 *
 */
@file:JvmName("TimeIntervalx")

package com.github.debop.kodatimes

import org.joda.time.*

fun ReadableInterval.millis(): Long = this.toDurationMillis()

infix fun ReadableInterval.step(period: ReadablePeriod): Sequence<DateTime> {
  return generateSequence(start) { it + period }.takeWhile { it <= end }
}

/** 기간을 초 단위로 열거합니다. */
@JvmOverloads
fun ReadableInterval.seconds(step: Int = 1): Sequence<DateTime> {
  return generateSequence(start) { it + step.seconds() }.takeWhile { it <= end }
}

/** 기간을 분 단위로 열거합니다. */
@JvmOverloads
fun ReadableInterval.minutes(step: Int = 1): Sequence<DateTime> {
  return generateSequence(start) { it + step.minutes() }.takeWhile { it <= end }
}

/** 기간을 시간 단위로 열거합니다. */
@JvmOverloads
fun ReadableInterval.hours(step: Int = 1): Sequence<DateTime> {
  return generateSequence(start) { it + step.hours() }.takeWhile { it <= end }
}

/** 기간을 일 단위로 열거합니다. */
@JvmOverloads
fun ReadableInterval.days(step: Int = 1): Sequence<DateTime> {
  return generateSequence(start.startOfDay()) { it + step.days() }.takeWhile { it <= end }
}

/** 기간을 주 단위로 열거합니다. */
@JvmOverloads
fun ReadableInterval.weeks(step: Int = 1): Sequence<DateTime> {
  return generateSequence(start.startOfWeek()) { it + step.weeks() }.takeWhile { it <= end }
}

/** 기간을 월 단위로 열거합니다. */
@JvmOverloads
fun ReadableInterval.months(step: Int = 1): Sequence<DateTime> {
  return generateSequence(start.startOfMonth()) { it + step.months() }.takeWhile { it <= end }
}

/** 기간을 년 단위로 열거합니다 */
@JvmOverloads
fun ReadableInterval.years(step: Int = 1): Sequence<DateTime> {
  return generateSequence(start.startOfYear()) { it + step.years() }.takeWhile { it <= end }
}

