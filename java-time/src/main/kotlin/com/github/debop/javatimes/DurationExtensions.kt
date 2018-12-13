package com.github.debop.javatimes

import java.time.Duration
import java.time.temporal.Temporal


operator fun Duration.unaryMinus(): Duration = this.negated()

val Duration.isPositive: Boolean get() = this > Duration.ZERO

val Duration.isNotNegative: Boolean get() = this >= Duration.ZERO

val Duration.millis: Long get() = seconds * 1000L + nano / 1_000_000L
fun Duration.inNanos(): Long = seconds * NANO_PER_SECOND + nano

fun durationOf(startInclusive: Temporal, endExclusive: Temporal): Duration =
    Duration.between(startInclusive, endExclusive)

fun durationOfYear(year: Int): Duration =
    durationOf(zonedDateTimeOf(year), zonedDateTimeOf(year + 1))

fun durationOfQuarter(year: Int, quarter: Quarter): Duration {
    val startInclusive = startOfQuarter(year, quarter)
    val endExclusive = startInclusive.plusMonths(MonthsPerQuarter.toLong())
    return durationOf(startInclusive, endExclusive)
}

fun durationOfMonth(year: Int, monthOfYear: Int): Duration {
    val startInclusive = startOfMonth(year, monthOfYear)
    val endExclusive = startInclusive.plusMonths(1)
    return durationOf(startInclusive, endExclusive)
}

fun durationOfWeek(week: Int): Duration = if(week == 0) Duration.ZERO else durationOfDay(week * DaysPerWeek)

@JvmOverloads
fun durationOfDay(days: Int,
                  hours: Int = 0,
                  minutes: Int = 0,
                  seconds: Int = 0,
                  nanos: Int = 0): Duration {
    var duration = days.days()

    if(hours != 0)
        duration += hours.hours()
    if(minutes != 0)
        duration += minutes.minutes()
    if(seconds != 0)
        duration += seconds.seconds()
    if(nanos != 0)
        duration += nanos.nanos()

    return duration
}

fun durationOfHour(hours: Int,
                   minutes: Int = 0,
                   seconds: Int = 0,
                   nanos: Int = 0): Duration {
    var duration = hours.hours()

    if(minutes != 0)
        duration += minutes.minutes()
    if(seconds != 0)
        duration += seconds.seconds()
    if(nanos != 0)
        duration += nanos.nanos()

    return duration
}

fun durationOfMinute(minutes: Int,
                     seconds: Int = 0,
                     nanos: Int = 0): Duration {
    var duration = minutes.minutes()

    if(seconds != 0)
        duration += seconds.seconds()
    if(nanos != 0)
        duration += nanos.nanos()

    return duration
}


fun durationOfSecond(seconds: Int,
                     nanos: Int = 0): Duration {
    var duration = seconds.seconds()

    if(nanos != 0)
        duration += nanos.nanos()

    return duration
}

fun durationOfNano(nanos: Long): Duration = Duration.ofNanos(nanos)