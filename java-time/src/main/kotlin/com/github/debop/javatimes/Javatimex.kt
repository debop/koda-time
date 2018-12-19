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

//import org.joda.time.DateTime
//import org.joda.time.Interval
import java.time.Clock
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.Month
import java.time.MonthDay
import java.time.OffsetDateTime
import java.time.OffsetTime
import java.time.Period
import java.time.Year
import java.time.YearMonth
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoField
import java.time.temporal.ChronoUnit
import java.time.temporal.Temporal
import java.time.temporal.TemporalAccessor
import java.util.*

const val NANO_PER_MILLIS: Long = 1_000_000L
const val NANO_PER_SECOND: Long = 1_000_000_000L

@JvmField val MILLIS_IN_MINUTE = Duration.ofMinutes(1).toMillis()
@JvmField val MILLIS_IN_HOUR = Duration.ofHours(1).toMillis()
@JvmField val MILLIS_IN_DAY: Long = Duration.ofDays(1).toMillis()

@JvmField val NANOS_IN_SECOND = Duration.ofSeconds(1).inNanos()
@JvmField val NANOS_IN_MINUTER = Duration.ofMinutes(1).inNanos()
@JvmField val NANOS_IN_HOUR: Long = Duration.ofHours(1).inNanos()
@JvmField val NANOS_IN_DAY: Long = Duration.ofDays(1).inNanos()


/** Default DateTime format (ex: '2011-12-03T10:15:30+01:00') */
@JvmField
val DefaultDateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ISO_INSTANT

@JvmField
val UtcZoneId: ZoneId = ZoneOffset.UTC

/** UTC Zone */
@JvmField
val UTC: ZoneId = ZoneOffset.UTC

@JvmField
val UtcTimeZone: TimeZone = TimeZone.getTimeZone(UtcZoneId)

@JvmField
val UtcOffset: ZoneOffset = ZoneOffset.UTC

@JvmField
val SystemTimeZone: TimeZone = TimeZone.getDefault()

@JvmField
val SystemZoneId: ZoneId = ZoneId.systemDefault()

@JvmField
val SystemOffset: ZoneOffset = ZoneOffset.ofTotalSeconds(SystemTimeZone.rawOffset / 1000)

/**
 * 날짜를 ISO 형식의 문자열로 만듭니다.
 * 예: DateTime : '2011-12-03T10:15:30',
 *     OffsetDateTime:'2011-12-03T10:15:30+01:00',
 *     ZonedDateTime: '2011-12-03T10:15:30+01:00[Europe/Paris]'.
 */
fun TemporalAccessor.toIsoString(): String = DateTimeFormatter.ISO_DATE_TIME.format(this)

/**
 * 일자를 ISO 형식의 문자열로 만듭니다.
 * 예: Date: '2011-12-03', OffsetDate: '2011-12-03+01:00'.
 */
fun TemporalAccessor.toIsoDateString(): String = DateTimeFormatter.ISO_DATE.format(this)

/**
 * 시각을 ISO 형식의 문자열로 만듭니다.
 * 예: '10:15', '10:15:30', OffsetTime: '10:15:30+01:00'.
 */
fun TemporalAccessor.toIsoTimeString(): String = DateTimeFormatter.ISO_TIME.format(this)

/**
 * 날짜를 ISO 형식의 로컬형식으로 표현합니다.
 * 예: '2011-12-03T10:15:30'
 */
fun TemporalAccessor.toLocalIsoString(): String = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(this)

/**
 * 일자을 ISO 형식의 로컬형식으로 표현합니다.
 * 예: Date: '2011-12-03'
 */
fun TemporalAccessor.toIsoLocalDateString(): String = DateTimeFormatter.ISO_LOCAL_DATE.format(this)

/**
 * 시각을 ISO 형식의 로컬형식으로 표현합니다.
 * 예: '10:15', '10:15:30'
 */
fun TemporalAccessor.toIsoLocalTimeString(): String = DateTimeFormatter.ISO_LOCAL_TIME.format(this)


fun TemporalAccessor.toIsoOffsetDateTimeString(): String = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(this)

fun TemporalAccessor.toIsoOffsetDateString(): String = DateTimeFormatter.ISO_OFFSET_DATE.format(this)

fun TemporalAccessor.toIsoOffsetTimeString(): String = DateTimeFormatter.ISO_OFFSET_TIME.format(this)

fun TemporalAccessor.toIsoZonedDateTimeString(): String = DateTimeFormatter.ISO_ZONED_DATE_TIME.format(this)

/**
 * [Temporal] 을 Epoch 이후의 milli seconds 단위로 표현한 값 (기존 Date#time, Timestamp 와 같은 값을 나타낸다)
 */
fun Temporal.toEpochMillis(): Long = when (this) {
    is Instant        -> toEpochMilli()
    is OffsetDateTime -> toInstant().toEpochMilli()
    is OffsetTime     -> toInstant().toEpochMilli()
    is ZonedDateTime  -> toInstant().toEpochMilli()
    else              -> {
        val days = try {
            getLong(ChronoField.EPOCH_DAY)
        } catch (e: Exception) {
            0L
        }
        val millis = getLong(ChronoField.MILLI_OF_DAY)

        days * MILLIS_IN_DAY + millis
    }
}

fun dateOf(epochMillis: Long): Date = Date(epochMillis)

operator fun Date.plus(that: Date): Date = Date(this.time + that.time)
operator fun Date.plus(millis: Long): Date = Date(this.time + millis)
operator fun Date.plus(duration: Duration): Date = Date(this.time + duration.toMillis())
operator fun Date.plus(period: Period): Date = Date(this.time + period.days * MILLIS_IN_DAY)

operator fun Date.minus(that: Date): Date = Date(this.time - that.time)
operator fun Date.minus(millis: Long): Date = Date(this.time - millis)
operator fun Date.minus(duration: Duration): Date = Date(this.time - duration.toMillis())
operator fun Date.minus(period: Period): Date = Date(this.time - period.days * MILLIS_IN_DAY)

fun nowInstant(zoneId: ZoneId = SystemZoneId): Instant = Instant.now(Clock.system(zoneId))
fun nowLocalTime(zoneId: ZoneId = SystemZoneId): LocalTime = LocalTime.now(zoneId)
fun nowLocalDate(zoneId: ZoneId = SystemZoneId): LocalDate = LocalDate.now(zoneId)
fun nowLocalDateTime(zoneId: ZoneId = SystemZoneId): LocalDateTime = LocalDateTime.now(zoneId)
fun nowOffsetDateTime(zoneId: ZoneId = SystemZoneId): OffsetDateTime = OffsetDateTime.now(zoneId)
fun nowZonedDateTime(zoneId: ZoneId = SystemZoneId): ZonedDateTime = ZonedDateTime.now(zoneId)

fun todayInstant(zonedId: ZoneId = SystemZoneId): Instant = nowInstant(zonedId).truncatedTo(ChronoUnit.DAYS)
fun todayLocalDateTime(zoneId: ZoneId = SystemZoneId): LocalDateTime = nowLocalDateTime(zoneId).truncatedTo(ChronoUnit.DAYS)
fun todayOffsetDateTime(zoneId: ZoneId = SystemZoneId): OffsetDateTime = nowOffsetDateTime(zoneId).truncatedTo(ChronoUnit.DAYS)
fun todayZonedDateTime(zoneId: ZoneId = SystemZoneId): ZonedDateTime = nowZonedDateTime(zoneId).truncatedTo(ChronoUnit.DAYS)

fun Int.nanos(): Duration = Duration.ofNanos(this.toLong())
fun Int.micros(): Duration = Duration.ofNanos(this.toLong() * 1000L)
fun Int.millis(): Duration = Duration.ofMillis(this.toLong())
@JvmOverloads
fun Int.seconds(nanoAdjustment: Long = 0L): Duration = Duration.ofSeconds(this.toLong(), nanoAdjustment)

fun Int.minutes(): Duration = Duration.ofMinutes(this.toLong())
fun Int.hours(): Duration = Duration.ofHours(this.toLong())
fun Int.days(): Duration = Duration.ofDays(this.toLong())
fun Int.weeks(): Duration = (this * DaysPerWeek).days()

fun Int.dayPeriod(): Period = Period.ofDays(this)
fun Int.weekPeriod(): Period = Period.ofWeeks(this)
fun Int.monthPeriod(): Period = Period.ofMonths(this)
fun Int.quarterPeriod(): Period = Period.ofMonths(this * MonthsPerQuarter)
fun Int.yearPeriod(): Period = Period.ofYears(this)

fun Int.millisToNanos(): Int = (this * 1e6).toInt()

fun Int.isLeapYear(): Boolean = Year.of(this).isLeap

operator fun Int.times(duration: Duration): Duration = duration.multipliedBy(this.toLong())
operator fun Int.times(period: Period): Period = period.multipliedBy(this)
operator fun Duration.times(scalar: Int): Duration = multipliedBy(scalar.toLong())
operator fun Period.times(scalar: Int): Period = multipliedBy(scalar)

operator fun Duration.div(scalar: Int): Duration = dividedBy(scalar.toLong())
operator fun Period.div(scalar: Int): Period = multipliedBy(scalar)

fun Int.toInstant(): Instant = Instant.ofEpochMilli(this.toLong())
fun Int.toLocalDateTime(zoneId: ZoneId = UtcZoneId): LocalDateTime = LocalDateTime.ofInstant(toInstant(), zoneId)


fun Long.nanos(): Duration = Duration.ofNanos(this)
fun Long.micros(): Duration = Duration.ofNanos(this * 1000L)
fun Long.millis(): Duration = Duration.ofMillis(this)
@JvmOverloads
fun Long.seconds(nanoAdjustment: Long = 0L): Duration = Duration.ofSeconds(this, nanoAdjustment)

fun Long.minutes(): Duration = Duration.ofMinutes(this)
fun Long.hours(): Duration = Duration.ofHours(this)
fun Long.days(): Duration = Duration.ofDays(this)
fun Long.weeks(): Duration = (this * 7L).days()

fun Long.dayPeriod(): Period = Period.ofDays(this.toInt())
fun Long.weekPeriod(): Period = Period.ofWeeks(this.toInt())
fun Long.monthPeriod(): Period = Period.ofMonths(this.toInt())
fun Long.quarterPeriod(): Period = Period.ofMonths(this.toInt() * MonthsPerQuarter)
fun Long.yearPeriod(): Period = Period.ofYears(this.toInt())

fun Long.millisToNanos(): Int = (this * 1e6).toInt()

fun Long.isLeapYear(): Boolean = Year.of(this.toInt()).isLeap

operator fun Long.times(duration: Duration): Duration = duration.multipliedBy(this)
operator fun Long.times(period: Period): Period = period.multipliedBy(this.toInt())
operator fun Duration.times(scalar: Long): Duration = multipliedBy(scalar)
operator fun Period.times(scalar: Long): Period = multipliedBy(scalar.toInt())

operator fun Duration.div(scalar: Long): Duration = dividedBy(scalar)
operator fun Period.div(scalar: Long): Period = multipliedBy(scalar.toInt())

fun Long.toInstant(): Instant = Instant.ofEpochMilli(this)
fun Long.toLocalDateTime(zoneId: ZoneId = SystemZoneId): LocalDateTime = LocalDateTime.ofInstant(toInstant(), zoneId)

fun yearMonthOf(year: Int, month: Month): YearMonth = YearMonth.of(year, month)
fun yearMonthOf(year: Int, monthOfYear: Int): YearMonth = YearMonth.of(year, monthOfYear)

fun monthDayOf(monthOfYear: Int, dayOfMonth: Int): MonthDay = MonthDay.of(monthOfYear, dayOfMonth)
fun monthDayOf(month: Month, dayOfMonth: Int): MonthDay = MonthDay.of(month, dayOfMonth)

val NowLocalDate: LocalDate get() = LocalDate.now()
val NowLocalTime: LocalTime get() = LocalTime.now()
val NowLocalDateTime: LocalDateTime get() = LocalDateTime.now()

val Int.days: Period get() = Period.ofDays(this)
val Int.weeks: Period get() = Period.ofWeeks(this)
val Int.months: Period get() = Period.ofMonths(this)
val Int.years: Period get() = Period.ofYears(this)
