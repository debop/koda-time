package com.github.debop.javatimes

import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.YearMonth
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoField
import java.time.temporal.ChronoUnit
import java.time.temporal.IsoFields
import java.time.temporal.TemporalAdjusters
import java.time.temporal.WeekFields

@JvmOverloads
fun zonedDateTimeOf(year: Int,
                    monthOfYear: Int = 1,
                    dayOfMonth: Int = 1,
                    hourOfDay: Int = 0,
                    minuteOfHour: Int = 0,
                    secondOfMinute: Int = 0,
                    nanoOfSecond: Int = 0,
                    zoneId: ZoneId = SystemZoneId): ZonedDateTime =
    ZonedDateTime.of(year, monthOfYear, dayOfMonth, hourOfDay, minuteOfHour, secondOfMinute, nanoOfSecond, zoneId)

@JvmOverloads
fun zonedDateTimeOf(localDate: LocalDate = LocalDate.ofEpochDay(0),
                    localTime: LocalTime = LocalTime.MIDNIGHT,
                    zoned: ZoneId = SystemZoneId): ZonedDateTime {
    return ZonedDateTime.of(localDate, localTime, zoned)
}

operator fun ZonedDateTime.rangeTo(endExclusive: ZonedDateTime): ReadableTemporalInterval = temporalIntervalOf(this, endExclusive)

val ZonedDateTime.weekyear: Int get() = this[WeekFields.ISO.weekBasedYear()]
val ZonedDateTime.weekOfWeekyear: Int get() = this[WeekFields.ISO.weekOfWeekBasedYear()]
val ZonedDateTime.weekOfMonth: Int get() = this[WeekFields.ISO.weekOfMonth()]

val ZonedDateTime.seondsOfDay: Int get() = this[ChronoField.SECOND_OF_DAY]
val ZonedDateTime.millisOfDay: Int get() = this[ChronoField.MILLI_OF_DAY]
val ZonedDateTime.nanoOfDay: Long get() = this.getLong(ChronoField.NANO_OF_DAY)

fun ZonedDateTime.toUtcInstant(): Instant = Instant.ofEpochSecond(this.toEpochSecond()) // toInstant().minusSeconds(offset.totalSeconds.toLong())

fun ZonedDateTime.startOfYear(): ZonedDateTime = zonedDateTimeOf(year, 1, 1)
fun ZonedDateTime.endOfYear(): ZonedDateTime = endOfYear(year)
fun ZonedDateTime.startOfQuarter(): ZonedDateTime = startOfQuarter(year, monthValue)
fun ZonedDateTime.endOfQuarter(): ZonedDateTime = endOfQuarter(year, monthValue)
fun ZonedDateTime.startOfMonth(): ZonedDateTime = zonedDateTimeOf(year, monthValue, 1)
fun ZonedDateTime.endOfMonth(): ZonedDateTime = endOfMonth(year, monthValue)
fun ZonedDateTime.startOfWeek(): ZonedDateTime = startOfWeek(year, monthValue, dayOfMonth)
fun ZonedDateTime.endOfWeek(): ZonedDateTime = endOfWeek(year, monthValue, dayOfMonth)
fun ZonedDateTime.startOfDay(): ZonedDateTime = truncatedTo(ChronoUnit.DAYS)
fun ZonedDateTime.endOfDay(): ZonedDateTime = startOfDay().plusDays(1).minusNanos(1)
fun ZonedDateTime.startOfHour(): ZonedDateTime = truncatedTo(ChronoUnit.HOURS)
fun ZonedDateTime.endOfHour(): ZonedDateTime = startOfHour().plusHours(1L).minusNanos(1)
fun ZonedDateTime.startOfMinute(): ZonedDateTime = truncatedTo(ChronoUnit.MINUTES)
fun ZonedDateTime.endOfMinute(): ZonedDateTime = startOfMinute().plusMinutes(1).minusNanos(1)
fun ZonedDateTime.startOfSecond(): ZonedDateTime = truncatedTo(ChronoUnit.SECONDS)
fun ZonedDateTime.endOfSeconds(): ZonedDateTime = startOfSecond().plusSeconds(1).minusNanos(1)

fun ZonedDateTime.startOfMillis(): ZonedDateTime = truncatedTo(ChronoUnit.MILLIS)
fun ZonedDateTime.endOfMillis(): ZonedDateTime = startOfMillis().plusNanos(1_000_000L - 1L)

fun startOfYear(year: Int): ZonedDateTime =
    zonedDateTimeOf(year, 1, 1)

fun endOfYear(year: Int): ZonedDateTime =
    startOfYear(year).plusYears(1).minusNanos(1)

fun startOfQuarter(year: Int, monthOfYear: Int): ZonedDateTime =
    startOfQuarter(year, Quarter.ofMonth(monthOfYear))

fun startOfQuarter(year: Int, quarter: Quarter): ZonedDateTime =
    zonedDateTimeOf(year, quarter.startMonth, 1)

fun endOfQuarter(year: Int, monthOfYear: Int): ZonedDateTime =
    endOfQuarter(year, Quarter.ofMonth(monthOfYear))

fun endOfQuarter(year: Int, quarter: Quarter): ZonedDateTime =
    zonedDateTimeOf(year, quarter.endMonth, 1).plusMonths(1).minusNanos(1)

fun startOfMonth(year: Int, monthOfYear: Int): ZonedDateTime =
    zonedDateTimeOf(year, monthOfYear, 1)

fun endOfMonth(year: Int, monthOfYear: Int): ZonedDateTime =
    startOfMonth(year, monthOfYear).plusMonths(1).minusNanos(1)

/** 해당 년/월의 Day 수 */
fun lengthOfMonth(year: Int, monthOfYear: Int): Int =
    YearMonth.of(year, monthOfYear).lengthOfMonth()

fun startOfWeek(year: Int, monthOfYear: Int, dayOfMonth: Int): ZonedDateTime {
    val date = zonedDateTimeOf(year, monthOfYear, dayOfMonth)
    return date - (date.dayOfWeek.value - DayOfWeek.MONDAY.value).days()
}

fun endOfWeek(year: Int, monthOfYear: Int, dayOfMonth: Int): ZonedDateTime =
    startOfWeek(year, monthOfYear, dayOfMonth).plusDays(DaysPerWeek.toLong()).minusNanos(1)

fun startOfWeekOfWeekyear(weekyear: Int, weekOfWeekyear: Int): ZonedDateTime {
    return ZonedDateTime.now()
        .with(IsoFields.WEEK_BASED_YEAR, weekyear.toLong())
        .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, weekOfWeekyear.toLong())
        .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
}

fun endOfWeekOfWeekyear(weekyear: Int, weekOfWeekyear: Int): ZonedDateTime {
    return startOfWeekOfWeekyear(weekyear, weekOfWeekyear).plusDays(DaysPerWeek.toLong()).minusNanos(1)
}


fun ZonedDateTime.nextDayOfWeek(): ZonedDateTime = this.plusWeeks(1)
fun ZonedDateTime.prevDayOfWeek(): ZonedDateTime = this.minusWeeks(1)

infix fun ZonedDateTime?.min(that: ZonedDateTime?): ZonedDateTime? = when {
    this == null -> that
    that == null -> this
    this < that  -> this
    else         -> that
}

infix fun ZonedDateTime?.max(that: ZonedDateTime?): ZonedDateTime? = when {
    this == null -> that
    that == null -> this
    this > that  -> this
    else         -> that
}

fun ZonedDateTime.equalTo(that: OffsetDateTime): Boolean = this == that.toZonedDateTime()

fun ZonedDateTime?.equalToSeconds(that: ZonedDateTime?): Boolean = when {
    this == null && that == null     -> true
    this === that                    -> true
    (this == null) != (that == null) -> false
    else                             -> this!!.truncatedTo(ChronoUnit.SECONDS) == that!!.truncatedTo(ChronoUnit.SECONDS)
}

fun ZonedDateTime?.equalToMillis(that: ZonedDateTime?): Boolean = when {
    this == null && that == null     -> true
    this === that                    -> true
    (this == null) != (that == null) -> false
    else                             -> this!!.truncatedTo(ChronoUnit.MILLIS) == that!!.truncatedTo(ChronoUnit.MILLIS)
}