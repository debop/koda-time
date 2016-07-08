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

import org.joda.time.*
import org.joda.time.base.AbstractInstant
import java.sql.Timestamp
import java.util.*


fun Date.toDateTime(): DateTime = DateTime(this.time)
fun Timestamp.toDateTime(): DateTime = DateTime(this)

fun AbstractInstant.dateTimeUTC(): DateTime = this.toDateTime(DateTimeZone.UTC)
fun AbstractInstant.mutableDateTimeUTC(): MutableDateTime = this.toMutableDateTime(DateTimeZone.UTC)


/**
 * Int extensions
 * <code>DateTime.now() + 15.seconds()</code>
 */
fun Int.millis(): DurationBuilder = DurationBuilder(Period.millis(this))

fun Int.seconds(): DurationBuilder = DurationBuilder(Period.seconds(this))
fun Int.minutes(): DurationBuilder = DurationBuilder(Period.minutes(this))
fun Int.hours(): DurationBuilder = DurationBuilder(Period.hours(this))

fun Int.days(): Period = Period.days(this)
fun Int.weeks(): Period = Period.weeks(this)
fun Int.months(): Period = Period.months(this)
fun Int.years(): Period = Period.years(this)

/**
 * Long extensions
 * <code>DateTime.now() + 15L.seconds()</code>
 */
fun Long.millis(): DurationBuilder = DurationBuilder(Period.millis(this.toInt()))

fun Long.seconds(): DurationBuilder = DurationBuilder(Period.seconds(this.toInt()))
fun Long.minutes(): DurationBuilder = DurationBuilder(Period.minutes(this.toInt()))
fun Long.hours(): DurationBuilder = DurationBuilder(Period.hours(this.toInt()))

fun Long.days(): Period = Period.days(this.toInt())
fun Long.weeks(): Period = Period.weeks(this.toInt())
fun Long.months(): Period = Period.months(this.toInt())
fun Long.years(): Period = Period.years(this.toInt())

/**
 * [DateTime] extensions
 */
fun dateTimeFromJson(json: String): DateTime = DateTime(json)

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

fun now(): DateTime = DateTime.now()
fun tomorrow(): DateTime = now().tomorrow()
fun yesterday(): DateTime = now().yesterday()

fun nextSecond(): DateTime = now().plusSeconds(1)
fun nextMinute(): DateTime = now().plusMinutes(1)
fun nextHour(): DateTime = now().plusHours(1)
fun nextDay(): DateTime = now().plusDays(1)
fun nextWeek(): DateTime = now().plusWeeks(1)
fun nextMonth(): DateTime = now().plusMonths(1)
fun nextYear(): DateTime = now().plusYears(1)

fun lastSecond(): DateTime = now().minusSeconds(1)
fun lastMinute(): DateTime = now().minusMinutes(1)
fun lastHour(): DateTime = now().minusHours(1)
fun lastDay(): DateTime = now().minusDays(1)
fun lastWeek(): DateTime = now().minusWeeks(1)
fun lastMonth(): DateTime = now().minusMonths(1)
fun lastYear(): DateTime = now().minusYears(1)

/**
 * [Duration] extensions
 */
val emptyDuration: Duration = Duration.ZERO

fun standardDays(days: Long): Duration = Duration.standardDays(days)
fun standardHours(hours: Long): Duration = Duration.standardHours(hours)
fun standardMinutes(minutes: Long): Duration = Duration.standardMinutes(minutes)
fun standardSeconds(seconds: Long): Duration = Duration.standardSeconds(seconds)

fun Duration.days(): Long = this.standardDays
fun Duration.hours(): Long = this.standardHours
fun Duration.minutes(): Long = this.standardMinutes
fun Duration.seconds(): Long = this.standardSeconds

//operator fun Duration.minus(millis: Long): Duration = this.minus(millis)
//operator fun Duration.minus(duration: ReadableDuration): Duration = this.minus(duration)
//
//operator fun Duration.plus(millis: Long): Duration = this.plus(millis)
//operator fun Duration.plus(duration: ReadableDuration): Duration = this.plus(duration)

fun Duration.isZero(): Boolean = this.millis == 0L

infix fun Duration.min(that: Duration): Duration {
  return if (this.compareTo(that) < 0) this else that
}

infix fun Duration.max(that: Duration): Duration {
  return if (this.compareTo(that) > 0) this else that
}


/**
 * [Period] extensions
 */
fun Period.ago(): DateTime = DateTime.now() - this

fun Period.later(): DateTime = DateTime.now() + this
fun Period.from(moment: DateTime): DateTime = moment + this
fun Period.before(moment: DateTime): DateTime = moment - this
fun Period.standardDuration(): Duration = this.toStandardDuration()

fun periodOfYears(y: Int): Period = Period.years(y)
fun periodOfMonths(m: Int): Period = Period.months(m)
fun periodOfWeek(w: Int): Period = Period.weeks(w)
fun periodOfDay(d: Int): Period = Period.days(d)
fun periodOfHours(h: Int): Period = Period.hours(h)
fun periodOfMinutes(m: Int): Period = Period.minutes(m)
fun periodOfSeconds(s: Int): Period = Period.seconds(s)
fun periodOfMillis(m: Int): Period = Period.millis(m)

/**
 * [Instant] extensions
 */
operator fun Instant.minus(builder: DurationBuilder): Instant = this.minus(builder.self.toStandardDuration())

operator fun Instant.plus(builder: DurationBuilder): Instant = this.plus(builder.self.toStandardDuration())

/**
 * [Interval] extensions
 */
fun thisSecond(): Interval = now().secondOfMinute().toInterval()

fun thisMinute(): Interval = now().minuteOfHour().toInterval()
fun thisHour(): Interval = now().hourOfDay().toInterval()


/**
 * [ReadableInstant] .. [ReadableInstant] => [Interval]
 *
 */
operator fun ReadableInstant.rangeTo(other: ReadableInstant): Interval = Interval(this, other)


/**
 * [ReadableInterval] extensions
 */
fun ReadableInterval.millis(): Long = this.toDurationMillis()

fun ReadableInterval.days(): List<DateTime> {

  tailrec fun recur(acc: MutableList<DateTime>, curr: DateTime, target: DateTime): MutableList<DateTime> {
    if (curr.startOfDay() == target.startOfDay()) {
      return acc
    } else {
      acc += curr
      return recur(acc, curr.nextDay(), target)
    }
  }
  return recur(mutableListOf(), start, end)
}