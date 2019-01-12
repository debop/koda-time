package com.github.debop.javatimes

import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDate
import java.time.Year
import java.time.ZonedDateTime

const val MonthsPerYear = 12
const val HalfyearsPerYear = 2
const val QuartersPerYear = 4
const val QuartersPerHalfyear = 2
const val MonthsPerHalfyear = 6
const val MonthsPerQuarter = 3
const val MaxWeeksPerYear = 54
const val MaxDaysPerMonth = 31
const val DaysPerWeek = 7
const val HoursPerDay = 24
const val MinutesPerHour = 60
const val SecondsPerMinute = 60

const val MillisPerSecond = 1000L
const val MillisPerMinute: Long = MillisPerSecond * SecondsPerMinute
const val MillisPerHour: Long = MillisPerMinute * MinutesPerHour
const val MillisPerDay: Long = MillisPerHour * HoursPerDay

const val MicrosPerSecond = 1000L * MillisPerSecond
const val MicrosPerMinute: Long = MicrosPerSecond * SecondsPerMinute
const val MicrosPerHour: Long = MicrosPerMinute * MinutesPerHour
const val MicrosPerDay: Long = MicrosPerHour * HoursPerDay

const val NanosPerSecond = 1000L * MicrosPerSecond
const val NanosPerMinute: Long = NanosPerSecond * SecondsPerMinute
const val NanosPerHour: Long = NanosPerMinute * MinutesPerHour
const val NanosPerDay: Long = NanosPerHour * HoursPerDay

const val TicksPerMillisecond = 10000L
const val TicksPerSecond = TicksPerMillisecond * MillisPerSecond
const val TicksPerMinute = TicksPerSecond * SecondsPerMinute
const val TicksPerHour = TicksPerMinute * MinutesPerHour
const val TicksPerDay = TicksPerHour * HoursPerDay


@JvmField val Weekdays = arrayOf(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY)
@JvmField val Weekends = arrayOf(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY)

fun DayOfWeek.isWeekend(): Boolean = Weekends.contains(this)

@JvmField val FirstDayOfWeek: DayOfWeek = DayOfWeek.MONDAY

@JvmField val FirstHalfyearMonths = intArrayOf(1, 2, 3, 4, 5, 6)
@JvmField val SecondHalfyearMonths = intArrayOf(7, 8, 9, 10, 11, 12)


@JvmField val FirstQuarterMonths = intArrayOf(1, 2, 3)
@JvmField val SecondQuarterMonths = intArrayOf(4, 5, 6)
@JvmField val ThirdQuarterMonths = intArrayOf(7, 8, 9)
@JvmField val FourthQuarterMonths = intArrayOf(10, 11, 12)

@JvmField val EmptyDuration: Duration = Duration.ZERO
@JvmField val MinDuration: Duration = 0.nanos()
@JvmField val MaxDuration: Duration = Long.MAX_VALUE.seconds()
@JvmField val MinPositiveDuration: Duration = 1.nanos()
@JvmField val MinNegativeDuration: Duration = (-1).nanos()

@JvmField val MinPeriodTime: ZonedDateTime = zonedDateTimeOf(LocalDate.MIN)
@JvmField val MaxPeriodTime: ZonedDateTime = zonedDateTimeOf(Year.MAX_VALUE - 1, 12, 31)

@JvmField val DefaultStartOffset: Duration = EmptyDuration
@JvmField val DefaultEndOffset: Duration = MinNegativeDuration