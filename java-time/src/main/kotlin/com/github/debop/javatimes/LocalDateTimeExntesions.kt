package com.github.debop.javatimes

import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.Month
import java.time.OffsetDateTime
import java.time.OffsetTime
import java.time.Period
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import java.util.Date

@JvmOverloads
fun localDateTimeOf(year: Int,
                    monthOfYear: Int = 1,
                    dayOfMonth: Int = 1,
                    hourOfDay: Int = 0,
                    minuteOfHour: Int = 0,
                    secondOfMinute: Int = 0,
                    milliOfSecond: Int = 0): LocalDateTime =
    LocalDateTime.of(year, monthOfYear, dayOfMonth, hourOfDay, minuteOfHour, secondOfMinute, milliOfSecond.millisToNanos())

fun LocalDateTime.toDate(): Date = Date.from(toInstant())
fun LocalDateTime.toInstant(): Instant = toOffsetDateTime().toInstant()
fun LocalDateTime.toUtcInstant(): Instant = toOffsetDateTime().toUtcInstant()


@JvmOverloads
fun LocalDateTime.toOffsetDateTime(offset: ZoneOffset = SystemOffset): OffsetDateTime =
    OffsetDateTime.of(this, offset)

fun LocalDateTime.toZonedDateTime(offset: ZoneOffset = SystemOffset): ZonedDateTime =
    ZonedDateTime.of(this, offset)

fun LocalDateTime.startOfYear(): LocalDateTime = this.withDayOfYear(1).truncatedTo(ChronoUnit.DAYS)
fun LocalDateTime.startOfMonth(): LocalDateTime = this.withDayOfMonth(1).truncatedTo(ChronoUnit.DAYS)
fun LocalDateTime.startOfWeek(): LocalDateTime = startOfDay() - (dayOfWeek.value - DayOfWeek.MONDAY.value).days()
fun LocalDateTime.startOfDay(): LocalDateTime = this.truncatedTo(ChronoUnit.DAYS)


@JvmOverloads
fun localDateOf(year: Int,
                monthOfYear: Int = 1,
                dayOfMonth: Int = 1): LocalDate = LocalDate.of(year, monthOfYear, dayOfMonth)

@JvmOverloads
fun localDateOf(year: Int,
                month: Month,
                dayOfMonth: Int = 1): LocalDate = LocalDate.of(year, month, dayOfMonth)

fun LocalDate.toDate(): Date = Date.from(toInstant())
fun LocalDate.toInstant(): Instant = Instant.from(this)
fun LocalDate.startOfWeek(): LocalDate = this - (dayOfWeek.value - DayOfWeek.MONDAY.value).days()
fun LocalDate.startOfMonth(): LocalDate = withDayOfMonth(1)
fun LocalDate.startOfYear(): LocalDate = withDayOfYear(1)

fun LocalDate.between(endExclusive: LocalDate): Period = Period.between(this, endExclusive)

@JvmOverloads
fun localTimeOf(hourOfDay: Int,
                minuteOfHour: Int = 0,
                secondOfMinute: Int = 0,
                milliOfSecond: Int): LocalTime =
    LocalTime.of(hourOfDay, minuteOfHour, secondOfMinute, milliOfSecond.millisToNanos())

fun LocalTime.toInstant(): Instant = Instant.from(this)

@JvmOverloads
fun offsetDateTimeOf(year: Int,
                     monthOfYear: Int = 1,
                     dayOfMonth: Int = 1,
                     hourOfDay: Int = 0,
                     minuteOfHour: Int = 0,
                     secondOfMinute: Int = 0,
                     milliOfSecond: Int = 0,
                     offset: ZoneOffset = SystemOffset): OffsetDateTime =
    OffsetDateTime.of(year, monthOfYear, dayOfMonth, hourOfDay, minuteOfHour, secondOfMinute, milliOfSecond.millisToNanos(), offset)

@JvmOverloads
fun offsetDateTimeOf(localDate: LocalDate = LocalDate.ofEpochDay(0),
                     localTime: LocalTime = LocalTime.ofSecondOfDay(0),
                     offset: ZoneOffset = SystemOffset): OffsetDateTime =
    OffsetDateTime.of(localDate, localTime, offset)

fun OffsetDateTime.toUtcInstant(): Instant = Instant.ofEpochSecond(this.toEpochSecond())

fun OffsetDateTime.startOfDay(): OffsetDateTime = this.truncatedTo(ChronoUnit.DAYS)
fun OffsetDateTime.startOfWeek(): OffsetDateTime = startOfDay() - (dayOfWeek.value - DayOfWeek.MONDAY.value).days()
fun OffsetDateTime.startOfMonth(): OffsetDateTime = withDayOfMonth(1)
fun OffsetDateTime.startOfYear(): OffsetDateTime = withDayOfYear(1)

fun OffsetTime.toInstant(): Instant = Instant.from(this)


