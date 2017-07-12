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

package com.github.debop.javatimes

import org.joda.time.DateTime
import org.joda.time.Interval
import java.sql.Timestamp
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoField
import java.time.temporal.TemporalAccessor
import java.util.*
import kotlin.coroutines.experimental.buildSequence

/** Default DateTime format (ex: '2011-12-03T10:15:30+01:00') */
@JvmField val DefaultDateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ISO_INSTANT

/** UTC Zone */
@JvmField val UTC: ZoneId = ZoneId.of("UTC")

/** '2011-12-03T10:15:30+01:00' */
fun TemporalAccessor.toIsoString(): String = DateTimeFormatter.ISO_DATE_TIME.format(this)

/** 2011-12-03 */
fun TemporalAccessor.toIsoDateString(): String = DateTimeFormatter.ISO_DATE.format(this)

/** 10:15:30+01:00 */
fun TemporalAccessor.toIsoTimeString(): String = DateTimeFormatter.ISO_TIME.format(this)

fun TemporalAccessor.toLocalIsoString(): String = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(this)

@JvmOverloads
fun Instant.toLocalDateTime(zoneId: ZoneId = UTC): LocalDateTime = LocalDateTime.ofInstant(this, zoneId)

fun Instant?.toDateTime(): DateTime? = this?.let { DateTime(toEpochMilli()) }
fun Instant?.toDate(): Date? = this?.let { Date.from(this) }

fun Date?.toDateTime(): DateTime? = this?.let { DateTime(time) }
fun Timestamp?.toDateTome(): DateTime? = this?.let { DateTime(time) }

fun instantOf(epochMillis: Long = 0): Instant = Instant.ofEpochMilli(epochMillis)
fun nowInstant(): Instant = Instant.now()
val EPOCH: Instant get() = Instant.EPOCH

val NowLocalDate: LocalDate get() = LocalDate.now()
val NowLocalTime: LocalTime get() = LocalTime.now()
val NowLocalDateTime: LocalDateTime get() = LocalDateTime.now()

val Int.nanoseconds: Duration get() = Duration.ofNanos(this.toLong())
val Int.microseconds: Duration get() = Duration.ofNanos(this.toLong() * 1000L)
val Int.millis: Duration get() = Duration.ofMillis(this.toLong())
val Int.seconds: Duration get() = Duration.ofSeconds(this.toLong())
fun Int.seconds(nanoAdjustment: Long): Duration = Duration.ofSeconds(this.toLong(), nanoAdjustment)
val Int.minutes: Duration get() = Duration.ofMinutes(this.toLong())
val Int.hours: Duration get() = Duration.ofHours(this.toLong())

val Int.days: Period get() = Period.ofDays(this)
val Int.weeks: Period get() = Period.ofWeeks(this)
val Int.months: Period get() = Period.ofMonths(this)
val Int.years: Period get() = Period.ofYears(this)

operator fun Int.times(duration: Duration): Duration = duration.multipliedBy(this.toLong())
operator fun Int.times(period: Period): Period = period.multipliedBy(this)
operator fun Duration.times(scalar: Int): Duration = multipliedBy(scalar.toLong())
operator fun Period.times(scalar: Int): Period = multipliedBy(scalar)

val Int.instant: Instant get() = Instant.ofEpochMilli(this.toLong())
fun Int.toLocalDateTime(zoneId: ZoneId = UTC): LocalDateTime = LocalDateTime.ofInstant(instant, zoneId)


val Long.nanoseconds: Duration get() = Duration.ofNanos(this)
val Long.microseconds: Duration get() = Duration.ofNanos(this * 1000L)
val Long.millis: Duration get() = Duration.ofMillis(this)
val Long.seconds: Duration get() = Duration.ofSeconds(this)
fun Long.seconds(nanoAdjustment: Long): Duration = Duration.ofSeconds(this, nanoAdjustment)
val Long.minutes: Duration get() = Duration.ofMinutes(this)
val Long.hours: Duration get() = Duration.ofHours(this)

val Long.days: Period get() = Period.ofDays(this.toInt())
val Long.weeks: Period get() = Period.ofWeeks(this.toInt())
val Long.months: Period get() = Period.ofMonths(this.toInt())
val Long.years: Period get() = Period.ofYears(this.toInt())

operator fun Long.times(duration: Duration): Duration = duration.multipliedBy(this)
operator fun Long.times(period: Period): Period = period.multipliedBy(this.toInt())
//operator fun Duration.times(scalar: Long): Duration = multipliedBy(scalar.toInt())
//operator fun Period.times(scalar: Long): Period = multipliedBy(scalar.toInt())

val Long.instant: Instant get() = Instant.ofEpochMilli(this)
fun Long.toLocalDateTime(zoneId: ZoneId = UTC): LocalDateTime = LocalDateTime.ofInstant(instant, zoneId)

@JvmOverloads
fun localDateOf(year: Int, monthOfYear: Int = 1, dayOfMonth: Int = 1): LocalDate = LocalDate.of(year, monthOfYear, dayOfMonth)

@JvmOverloads
fun localDateOf(year: Int, month: Month, dayOfMonth: Int = 1): LocalDate = LocalDate.of(year, month, dayOfMonth)

@JvmOverloads
fun monthDayOf(monthOfYear: Int = 1, dayOfMonth: Int = 1): MonthDay = MonthDay.of(monthOfYear, dayOfMonth)

@JvmOverloads
fun monthDayOf(month: Month, dayOfMonth: Int = 1): MonthDay = MonthDay.of(month, dayOfMonth)

@JvmOverloads
fun localTimeOf(hourOfDay: Int = 0, minuteOfHour: Int = 0, secondOfMinute: Int = 0, nanoOfSecond: Int = 0): LocalTime =
    LocalTime.of(hourOfDay, minuteOfHour, secondOfMinute, nanoOfSecond)

@JvmOverloads
fun Instant.with(year: Int, monthOfYear: Int = 1, dayOfMonth: Int = 1,
                 hourOfDay: Int = 0, minuteOfHour: Int = 0, secondOfMinute: Int = 0, millisOfSecond: Int = 0,
                 zoneOffset: ZoneOffset = ZoneOffset.UTC): Instant =
    this.toLocalDateTime()
        .withYear(year)
        .withMonth(monthOfYear)
        .withDayOfMonth(dayOfMonth)
        .withHour(hourOfDay)
        .withMinute(minuteOfHour)
        .withSecond(secondOfMinute)
        .with(ChronoField.MILLI_OF_SECOND, millisOfSecond.toLong())
        .toInstant(zoneOffset)


val Instant.startOfDay: Instant get() = this.with(ChronoField.MILLI_OF_DAY, 0)
val Instant.startOfMonth: Instant get() = this.with(ChronoField.DAY_OF_MONTH, 1).startOfDay
val Instant.startOfYear: Instant get() = this.with(ChronoField.MONTH_OF_YEAR, 1).startOfMonth

infix fun Instant?.min(that: Instant?): Instant? = when {
    this == null -> that
    that == null -> this
    this > that  -> that
    else         -> this
}

infix fun Instant?.max(that: Instant?): Instant? = when {
    this == null -> that
    that == null -> this
    this < that  -> that
    else         -> this
}

operator fun Instant.rangeTo(endExlusive: Instant): Interval = Interval(this.toEpochMilli(), endExlusive.toEpochMilli())

/**
 * Year Interval at specified instatnt included
 */
val Instant.yearInterval: Interval
    get() {
        val start = this.startOfYear
        return start .. (start + 1.years)
    }

/**
 * Month [Interval] at specified instant included
 */
val Instant.monthInterval: Interval
    get() {
        val start = this.startOfMonth
        return start .. (start + 1.months)
    }

val Instant.dayInterval: Interval
    get() {
        val start = this.startOfDay
        return start .. (start + 1.days)
    }

operator fun Period.unaryMinus(): Period = this.negated()

suspend fun Period.yearSequence(): Sequence<Int> = buildSequence<Int> {
    var year = 0
    val years = this@yearSequence.years
    if (years > 0) {
        while (year < years) {
            yield(year++)
        }
    } else {
        while (year > years) {
            yield(year--)
        }
    }
}

suspend fun Period.monthSequence(): Sequence<Int> = buildSequence<Int> {
    var month = 0
    val months = this@monthSequence.months
    if (months > 0) {
        while (month < months) {
            yield(month++)
        }
    } else {
        while (month > months) {
            yield(month--)
        }
    }
}

suspend fun Period.daySequence(): Sequence<Int> = buildSequence<Int> {
    var day = 0
    val days = this@daySequence.days
    if (days > 0) {
        while (day < days) {
            yield(day++)
        }
    } else {
        while (day > days) {
            yield(day--)
        }
    }
}