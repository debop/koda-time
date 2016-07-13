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
@file:JvmName("jodatimeExtensions")

package com.github.debop.kodatime

import org.joda.time.*
import org.joda.time.base.AbstractInstant
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.ISODateTimeFormat
import java.sql.Timestamp
import java.util.*


fun Date.toDateTime(): DateTime = DateTime(this.time)
fun Date.toLocalDateTime(): LocalDateTime = LocalDateTime.fromDateFields(this)
fun Date.toLocalDate(): LocalDate = LocalDate.fromDateFields(this)
fun Date.toLocalTime(): LocalTime = LocalTime.fromDateFields(this)

fun AbstractInstant.dateTimeUTC(): DateTime = this.toDateTime(DateTimeZone.UTC)
fun AbstractInstant.mutableDateTimeUTC(): MutableDateTime = this.toMutableDateTime(DateTimeZone.UTC)

fun Int.millis(): DurationBuilder = DurationBuilder(Period.millis(this))
fun Int.seconds(): DurationBuilder = DurationBuilder(Period.seconds(this))
fun Int.minutes(): DurationBuilder = DurationBuilder(Period.minutes(this))
fun Int.hours(): DurationBuilder = DurationBuilder(Period.hours(this))

fun Int.days(): Period = Period.days(this)
fun Int.weeks(): Period = Period.weeks(this)
fun Int.months(): Period = Period.months(this)
fun Int.years(): Period = Period.years(this)

fun Int.times(builder: DurationBuilder): DurationBuilder = DurationBuilder(builder.period.multipliedBy(this))
fun Int.times(period: Period): Period = period.multipliedBy(this)

fun Long.millis(): DurationBuilder = DurationBuilder(Period.millis(this.toInt()))
fun Long.seconds(): DurationBuilder = DurationBuilder(Period.seconds(this.toInt()))
fun Long.minutes(): DurationBuilder = DurationBuilder(Period.minutes(this.toInt()))
fun Long.hours(): DurationBuilder = DurationBuilder(Period.hours(this.toInt()))

fun Long.days(): Period = Period.days(this.toInt())
fun Long.weeks(): Period = Period.weeks(this.toInt())
fun Long.months(): Period = Period.months(this.toInt())
fun Long.years(): Period = Period.years(this.toInt())

fun Long.times(builder: DurationBuilder): DurationBuilder = DurationBuilder(builder.period.multipliedBy(this.toInt()))
fun Long.times(period: Period): Period = period.multipliedBy(this.toInt())

/**
 * String extensions
 */
fun dateTimeFormat(pattern: String) = DateTimeFormat.forPattern(pattern)

fun String.toDateTime(pattern: String? = null): DateTime? = try {
  if (pattern.isNullOrBlank()) DateTime(this)
  else DateTime.parse(this, dateTimeFormat(pattern!!))
} catch(ignored: Exception) {
  null
}

fun String.toInterval(): Interval? = try {
  Interval.parse(this)
} catch(ignored: Exception) {
  null
}

fun String.toLocalDate(pattern: String? = null): LocalDate? = try {
  if (pattern.isNullOrBlank()) LocalDate(this)
  else LocalDate.parse(this, dateTimeFormat(pattern!!))
} catch(ignored: Exception) {
  null
}

fun String.toLocalTime(pattern: String? = null): LocalTime? = try {
  if (pattern.isNullOrBlank()) LocalTime(this) else LocalTime.parse(this, dateTimeFormat(pattern!!))
} catch(ignored: Exception) {
  null
}

fun dateTimeFromJson(json: String): DateTime = DateTime(json)
fun dateTimeOf(year: Int, month: Int, day: Int): DateTime = DateTime(year, month, day, 0, 0)

fun DateTime.startOfDay(): DateTime = this.withTimeAtStartOfDay()
fun DateTime.startOfMonth(): DateTime = dateTimeOf(this.year, this.monthOfYear, 1)
fun DateTime.startOfYear(): DateTime = dateTimeOf(this.year, 1, 1)

operator fun DateTime.minus(builder: DurationBuilder): DateTime = this.minus(builder.period)
operator fun DateTime.plus(builder: DurationBuilder): DateTime = this.plus(builder.period)

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
fun DateTime.asUtc(): DateTime = this.toDateTime(DateTimeZone.UTC)
fun DateTime.asLocal(tz: DateTimeZone = DateTimeZone.getDefault()): DateTime = this.toDateTime(tz)

fun DateTime.toIsoFormatString(): String = ISODateTimeFormat.dateTime().print(this)
fun DateTime.toIsoFormatDateString(): String = ISODateTimeFormat.date().print(this)
fun DateTime.toIsoFormatTimeString(): String = ISODateTimeFormat.time().print(this)
fun DateTime.toIsoFormatTimeNoMillisString(): String = ISODateTimeFormat.timeNoMillis().print(this)
fun DateTime.toIsoFormatHMSString(): String = ISODateTimeFormat.dateHourMinuteSecond().print(this)

fun DateTime.toTimestampZoneText(): TimestampZoneText = TimestampZoneText(this)

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


operator fun LocalDateTime.minus(builder: DurationBuilder): LocalDateTime = this.minus(builder.period)
operator fun LocalDateTime.plus(builder: DurationBuilder): LocalDateTime = this.plus(builder.period)

operator fun LocalDate.minus(builder: DurationBuilder): LocalDate = this.minus(builder.period)
operator fun LocalDate.plus(builder: DurationBuilder): LocalDate = this.plus(builder.period)

operator fun LocalTime.minus(builder: DurationBuilder): LocalTime = this.minus(builder.period)
operator fun LocalTime.plus(builder: DurationBuilder): LocalTime = this.plus(builder.period)


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

fun Duration.abs(): Duration = if (this < emptyDuration) -this else this
fun Duration.fromNow(): DateTime = now() + this
fun Duration.agoNow(): DateTime = now() - this
fun Duration.afterEpoch(): DateTime = DateTime(0) + this
fun Duration.diff(other: Duration): Duration = this - other

operator fun Duration.unaryMinus(): Duration = this.negated()
operator fun Duration.div(divisor: Long): Duration = this.dividedBy(divisor)
operator fun Duration.times(multiplicand: Long): Duration = this.multipliedBy(multiplicand)

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

operator fun Instant.minus(builder: DurationBuilder): Instant = this.minus(builder.period.toStandardDuration())
operator fun Instant.plus(builder: DurationBuilder): Instant = this.plus(builder.period.toStandardDuration())

fun thisSecond(): Interval = now().secondOfMinute().toInterval()
fun thisMinute(): Interval = now().minuteOfHour().toInterval()
fun thisHour(): Interval = now().hourOfDay().toInterval()

//
// [ReadableInstant] .. [ReadableInstant] => [Interval]
//
operator fun ReadableInstant.rangeTo(other: ReadableInstant): Interval = Interval(this, other)

fun ReadableInterval.millis(): Long = this.toDurationMillis()

fun ReadableInterval.days(): List<DateTime> {

  tailrec fun recur(acc: MutableList<DateTime>, curr: DateTime, target: DateTime): MutableList<DateTime> {
    if (curr.startOfDay() > target.startOfDay()) {
      return acc
    } else {
      acc += curr
      return recur(acc, curr.nextDay(), target)
    }
  }
  return recur(mutableListOf(), start, end)
}

infix fun ReadableInterval.step(instant: ReadablePeriod): List<DateTime> {
  val acc = mutableListOf(start)
  var current = start + instant
  while (current <= end) {
    acc += current
    current += instant
  }
  return acc
}