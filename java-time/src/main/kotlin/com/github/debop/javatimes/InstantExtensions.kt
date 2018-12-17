package com.github.debop.javatimes

import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.temporal.ChronoField
import java.time.temporal.ChronoUnit
import java.util.*

fun instantOf(epochMillis: Long): Instant = Instant.ofEpochMilli(epochMillis)

@JvmField
val EPOCH: Instant = Instant.EPOCH

operator fun Instant.rangeTo(end: Instant) = temporalIntervalOf(this, end)

operator fun Instant.rangeTo(duration: Duration) = temporalIntervalOf(this, duration)

@JvmOverloads
fun Instant.toLocalDate(zoneId: ZoneId = SystemZoneId): LocalDate = toLocalDateTime(zoneId).toLocalDate()

@JvmOverloads
fun Instant.toLocalDateTime(zoneId: ZoneId = SystemZoneId): LocalDateTime = LocalDateTime.ofInstant(this, zoneId)

@JvmOverloads
fun Instant.toOffsetDateTime(zoneId: ZoneId = SystemZoneId): OffsetDateTime = OffsetDateTime.ofInstant(this, zoneId)

@JvmOverloads
fun Instant.toZonedDateTime(zoneId: ZoneId = SystemZoneId): ZonedDateTime = ZonedDateTime.ofInstant(this, zoneId)

fun Instant.toDate(): Date = Date.from(this)

fun Instant.toCalendar(timeZone: TimeZone = TimeZone.getTimeZone(UtcZoneId)): Calendar =
    Calendar.Builder()
        .setInstant(this.toEpochMilli())
        .setTimeZone(timeZone)
        .build()

@JvmOverloads
fun Instant.with(year: Int,
                 monthOfYear: Int = 1,
                 dayOfMonth: Int = 1,
                 hourOfDay: Int = 0,
                 minuteOfHour: Int = 0,
                 secondOfMinute: Int = 0,
                 millisOfSecond: Int = 0,
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

fun Instant.startOfYear(): Instant = toLocalDateTime(UtcZoneId).startOfYear().toInstant(UtcOffset)
fun Instant.startOfMonth(): Instant = toLocalDateTime(UtcZoneId).startOfMonth().toInstant(UtcOffset)
fun Instant.startOfWeek(): Instant = toLocalDateTime(UtcZoneId).startOfWeek().toInstant(UtcOffset)
fun Instant.startOfDay(): Instant = truncatedTo(ChronoUnit.DAYS)
fun Instant.startOfHour(): Instant = truncatedTo(ChronoUnit.HOURS)
fun Instant.startOfMinute(): Instant = truncatedTo(ChronoUnit.MINUTES)
fun Instant.startOfSecond(): Instant = truncatedTo(ChronoUnit.SECONDS)
fun Instant.startOfMillis(): Instant = truncatedTo(ChronoUnit.MILLIS)

operator fun Instant.plus(millis: Long): Instant = plusMillis(millis)
operator fun Instant.minus(millis: Long): Instant = minusMillis(millis)

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

val Instant.yearInterval: ReadableTemporalInterval
    get() {
        val start = startOfYear()
        return start..(start + 1.yearPeriod())
    }

val Instant.monthInterval: ReadableTemporalInterval
    get() {
        val start = startOfMonth()
        return start..(start + 1.monthPeriod())
    }

val Instant.dayInterval: ReadableTemporalInterval
    get() {
        val start = this.startOfDay()
        return start..(start + 1.days())
    }