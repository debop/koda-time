/*
 * Copyright (c) 2016. Sunghyouk Bae <sunghyouk.bae@gmail.com>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.debop.jodatimes

import org.joda.time.DateTime
import org.joda.time.Interval
import java.sql.Timestamp

fun DateTime.startOfDay(): DateTime = this.withTimeAtStartOfDay()

operator fun DateTime.minus(builder: DurationBuilder): DateTime = this.minus(builder.self)
operator fun DateTime.plus(builder: DurationBuilder): DateTime = this.plus(builder.self)

fun DateTime.tomorrow(): DateTime = this.nextDay()
fun DateTime.yesterday(): DateTime = this.lastDay()

fun DateTime.nextSecond(): DateTime = this.plusSeconds(1)
fun DateTime.nextMinute(): DateTime = this.plusMinutes(1)
fun DateTime.nextHour(): DateTime = this.plusHours(1)
fun DateTime.nextDay(): DateTime = this.plusDays(1)
fun DateTime.nextWeek(): DateTime = this.plusWeeks(1)
fun DateTime.nextMonth(): DateTime = this.plusMonths(1)
fun DateTime.nextYear(): DateTime = this.plusYears(1)

fun DateTime.lastSecond(): DateTime = this.minusSeconds(1)
fun DateTime.lastMinute(): DateTime = this.minusMinutes(1)
fun DateTime.lastHour(): DateTime = this.minusHours(1)
fun DateTime.lastDay(): DateTime = this.minusDays(1)
fun DateTime.lastWeek(): DateTime = this.minusWeeks(1)
fun DateTime.lastMonth(): DateTime = this.minusMonths(1)
fun DateTime.lastYear(): DateTime = this.minusYears(1)

fun DateTime.fromJson(json: String): DateTime = DateTime(json)

fun DateTime.toTimestamp(): Timestamp = Timestamp(this.millis)

infix fun DateTime.min(that: DateTime): DateTime {
  return if (this.compareTo(that) < 0) this else that
}

infix fun DateTime.max(that: DateTime): DateTime {
  return if (this.compareTo(that) > 0) this else that
}

fun DateTime.monthInterval(): Interval {
  val start = this.withDayOfMonth(1).withTimeAtStartOfDay()
  return Interval(start, start + 1.months())
}

fun DateTime.dayInterval(): Interval {
  val start = this.startOfDay()
  return Interval(start, start + 1.days())
}