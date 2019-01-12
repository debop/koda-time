package com.github.debop.javatimes

import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAmount

//
// TemporalInterval
//

@JvmOverloads
fun temporalIntervalOf(start: Instant, end: Instant, zoneId: ZoneId = UtcZoneId): TemporalInterval {
    return when {
        start.isBefore(end) -> TemporalInterval(start, end, zoneId)
        else                -> TemporalInterval(end, start, zoneId)
    }
}

fun temporalIntervalOf(startDateTime: ZonedDateTime, endDateTime: ZonedDateTime): TemporalInterval =
    temporalIntervalOf(startDateTime.toInstant(), endDateTime.toInstant(), startDateTime.zone)

fun temporalIntervalOf(amount: TemporalAmount, endDateTime: ZonedDateTime): TemporalInterval =
    temporalIntervalOf(endDateTime - amount, endDateTime)

fun temporalIntervalOf(amount: TemporalAmount, end: Instant, zoneId: ZoneId = UtcZoneId): TemporalInterval =
    temporalIntervalOf(end - amount, end, zoneId)

fun temporalIntervalOf(startDateTime: ZonedDateTime, amount: TemporalAmount): TemporalInterval =
    temporalIntervalOf(startDateTime, startDateTime + amount)

fun temporalIntervalOf(start: Instant, duration: TemporalAmount, zoneId: ZoneId = UtcZoneId): TemporalInterval =
    temporalIntervalOf(start, start + duration, zoneId)

fun temporalIntervalOf(startEpochMillis: Long, endMillisEpoch: Long, zoneId: ZoneId = UtcZoneId): TemporalInterval =
    temporalIntervalOf(Instant.ofEpochMilli(startEpochMillis), Instant.ofEpochMilli(endMillisEpoch), zoneId)

fun temporalIntervalOf(startEpochMillis: Long, amount: TemporalAmount, zoneId: ZoneId = UtcZoneId): TemporalInterval =
    temporalIntervalOf(Instant.ofEpochMilli(startEpochMillis), amount, zoneId)

fun temporalIntervalOf(amount: TemporalAmount, endEpochMillis: Long, zoneId: ZoneId = UtcZoneId): TemporalInterval =
    temporalIntervalOf(amount, Instant.ofEpochMilli(endEpochMillis), zoneId)

//
// MutableTemporalInterval
//


@JvmOverloads
fun mutableTemporalIntervalOf(start: Instant, end: Instant, zoneId: ZoneId = UtcZoneId): MutableTemporalInterval {
    return when {
        start.isBefore(end) -> MutableTemporalInterval(start, end, zoneId)
        else                -> MutableTemporalInterval(end, start, zoneId)
    }
}

fun mutableTemporalIntervalOf(startDateTime: ZonedDateTime, endDateTime: ZonedDateTime): MutableTemporalInterval =
    mutableTemporalIntervalOf(startDateTime.toInstant(), endDateTime.toInstant(), startDateTime.zone)

fun mutableTemporalIntervalOf(amount: TemporalAmount, endDateTime: ZonedDateTime): MutableTemporalInterval =
    mutableTemporalIntervalOf(endDateTime - amount, endDateTime)

@JvmOverloads
fun mutableTemporalIntervalOf(amount: TemporalAmount, end: Instant, zoneId: ZoneId = UtcZoneId): MutableTemporalInterval =
    mutableTemporalIntervalOf(end - amount, end, zoneId)


fun mutableTemporalIntervalOf(startDateTime: ZonedDateTime, amount: TemporalAmount): MutableTemporalInterval =
    mutableTemporalIntervalOf(startDateTime, startDateTime + amount)

@JvmOverloads
fun mutableTemporalIntervalOf(start: Instant, amount: TemporalAmount, zoneId: ZoneId = UtcZoneId): MutableTemporalInterval =
    mutableTemporalIntervalOf(start, start + amount, zoneId)


@JvmOverloads
fun mutableTemporalIntervalOf(startEpochMillis: Long, endEpochMillis: Long, zoneId: ZoneId = UtcZoneId): MutableTemporalInterval =
    mutableTemporalIntervalOf(Instant.ofEpochMilli(startEpochMillis), Instant.ofEpochMilli(endEpochMillis), zoneId)

@JvmOverloads
fun mutableTemporalIntervalOf(startEpochMillis: Long, amount: TemporalAmount, zoneId: ZoneId = UtcZoneId): MutableTemporalInterval =
    mutableTemporalIntervalOf(Instant.ofEpochMilli(startEpochMillis), amount, zoneId)


@JvmOverloads
fun mutableTemporalIntervalOf(amount: TemporalAmount, endEpochMillis: Long, zoneId: ZoneId = UtcZoneId): MutableTemporalInterval =
    mutableTemporalIntervalOf(amount, Instant.ofEpochMilli(endEpochMillis), zoneId)


//
// Sequence of Interval 
//

fun ReadableTemporalInterval.sequence(step: TemporalAmount): Sequence<ZonedDateTime> = sequence {
    var current = startDateTime
    while(current <= endDateTime) {
        yield(current)
        current += step
    }
}

@JvmOverloads
fun ReadableTemporalInterval.seconds(step: Int = 1): Sequence<ZonedDateTime> {
    require(step > 0) { "step must positive value [$step]" }
    return sequence(step.seconds())
}

/**
 * 기간을 Minute 단위로 열거합니다.
 */
@JvmOverloads
fun ReadableTemporalInterval.minutes(step: Int = 1): Sequence<ZonedDateTime> {
    require(step > 0) { "step must positive value [$step]" }
    return sequence(step.minutes())
}

/**
 * 기간을 Hour 단위로 열거합니다.
 */
@JvmOverloads
fun ReadableTemporalInterval.hours(stepHours: Int = 1): Sequence<ZonedDateTime> {
    require(stepHours > 0) { "stepHours must postive value [$stepHours]" }
    return sequence(stepHours.hours())
}

/**
 * 기간을 Day 단위로 열거합니다.
 */
@JvmOverloads
fun ReadableTemporalInterval.days(stepDays: Int = 1): Sequence<ZonedDateTime> {
    require(stepDays > 0) { "stepDays must postive value [$stepDays]" }
    return sequence(stepDays.days())
}

@JvmOverloads
fun ReadableTemporalInterval.weeks(stepWeeks: Int = 1): Sequence<ZonedDateTime> {
    require(stepWeeks > 0) { "stepWeeks must postive value [$stepWeeks]" }
    return sequence(stepWeeks.weeks())
}

@JvmOverloads
fun ReadableTemporalInterval.months(stepMonth: Int = 1): Sequence<ZonedDateTime> {
    require(stepMonth > 0) { "stepMonth must postive value [$stepMonth]" }
    return sequence(stepMonth.monthPeriod())
}

@JvmOverloads
fun ReadableTemporalInterval.years(stepYears: Int = 1): Sequence<ZonedDateTime> {
    require(stepYears > 0) { "stepYears must postive value [$stepYears]" }
    return sequence(stepYears.yearPeriod())
}

//
// Chunk
//

fun ReadableTemporalInterval.chunk(size: Int, unit: ChronoUnit): Sequence<List<ZonedDateTime>> {
    require(size > 0) { "chunk size must be positive value. [$size]" }

    return when(unit) {
        ChronoUnit.YEARS   -> chunkYear(size)
        ChronoUnit.MONTHS  -> chunkMonth(size)
        ChronoUnit.WEEKS   -> chunkWeek(size)
        ChronoUnit.DAYS    -> chunkDay(size)
        ChronoUnit.HOURS   -> chunkHour(size)
        ChronoUnit.MINUTES -> chunkMinute(size)
        ChronoUnit.SECONDS -> chunkSecond(size)
        ChronoUnit.MILLIS  -> chunkMilli(size)
        else               -> throw UnsupportedOperationException("Not supported chrono unit. [$unit]")
    }
}

fun ReadableTemporalInterval.chunkYear(size: Int): Sequence<List<ZonedDateTime>> {
    require(size > 0) { "chunk size must be positive value. [$size]" }

    return sequence {
        var current = startDateTime.startOfYear()
        val increment = size.yearPeriod()

        while(current < endDateTime) {
            yield(List(size) { current + it.yearPeriod() }.takeWhile { it < endDateTime })
            current += increment
        }
    }
}

fun ReadableTemporalInterval.chunkMonth(size: Int): Sequence<List<ZonedDateTime>> {
    require(size > 0) { "chunk size must be positive value. [$size]" }

    return sequence {
        var current = startDateTime.startOfMonth()
        val increment = size.monthPeriod()

        while(current < endDateTime) {
            yield(List(size) { current + it.monthPeriod() }.takeWhile { it < endDateTime })
            current += increment
        }
    }
}

fun ReadableTemporalInterval.chunkWeek(size: Int): Sequence<List<ZonedDateTime>> {
    require(size > 0) { "chunk size must be positive value. [$size]" }

    return sequence {
        var current = startDateTime.startOfWeek()
        val increment = size.weekPeriod()

        while(current < endDateTime) {
            yield(List(size) { current + it.weekPeriod() }.takeWhile { it < endDateTime })
            current += increment
        }
    }
}

fun ReadableTemporalInterval.chunkDay(size: Int): Sequence<List<ZonedDateTime>> {
    require(size > 0) { "chunk size must be positive value. [$size]" }

    return sequence {
        var current = startDateTime.startOfDay()
        val increment = size.dayPeriod()

        while(current < endDateTime) {
            yield(List(size) { current + it.dayPeriod() }.takeWhile { it < endDateTime })
            current += increment
        }
    }
}

fun ReadableTemporalInterval.chunkHour(size: Int): Sequence<List<ZonedDateTime>> {
    require(size > 0) { "chunk size must be positive value. [$size]" }

    return sequence {
        var current = startDateTime.truncatedTo(ChronoUnit.HOURS)
        val increment = size.hours()

        while(current < endDateTime) {
            yield(List(size) { current + it.hours() }.takeWhile { it < endDateTime })
            current += increment
        }
    }
}

fun ReadableTemporalInterval.chunkMinute(size: Int): Sequence<List<ZonedDateTime>> {
    require(size > 0) { "chunk size must be positive value. [$size]" }

    return sequence {
        var current = startDateTime.truncatedTo(ChronoUnit.MINUTES)
        val increment = size.minutes()

        while(current < endDateTime) {
            yield(List(size) { current + it.minutes() }.takeWhile { it < endDateTime })
            current += increment
        }
    }
}

fun ReadableTemporalInterval.chunkSecond(size: Int): Sequence<List<ZonedDateTime>> {
    require(size > 0) { "chunk size must be positive value. [$size]" }

    return sequence {
        var current = startDateTime.truncatedTo(ChronoUnit.SECONDS)
        val increment = size.seconds()

        while(current < endDateTime) {
            yield(List(size) { current + it.seconds() }.takeWhile { it < endDateTime })
            current += increment
        }
    }
}

fun ReadableTemporalInterval.chunkMilli(size: Int): Sequence<List<ZonedDateTime>> {
    require(size > 0) { "chunk size must be positive value. [$size]" }

    return sequence {
        var current = startDateTime.truncatedTo(ChronoUnit.MILLIS)
        val increment = size.millis()

        while(current < endDateTime) {
            yield(List(size) { current + it.millis() }.takeWhile { it < endDateTime })
            current += increment
        }
    }
}

//
// windowed
//

@JvmOverloads
fun ReadableTemporalInterval.windowed(size: Int,
                                      step: Int = 1,
                                      unit: ChronoUnit = ChronoUnit.YEARS): Sequence<List<ZonedDateTime>> {
    require(step > 0) { "step must positive number [$step]" }
    if(size <= 0) {
        return emptySequence()
    }
    return when(unit) {
        ChronoUnit.YEARS   -> windowedYear(size, step)
        ChronoUnit.MONTHS  -> windowedMonth(size, step)
        ChronoUnit.WEEKS   -> windowedWeek(size, step)
        ChronoUnit.DAYS    -> windowedDay(size, step)
        ChronoUnit.HOURS   -> windowedHour(size, step)
        ChronoUnit.MINUTES -> windowedMinute(size, step)
        ChronoUnit.SECONDS -> windowedSecond(size, step)
        ChronoUnit.MILLIS  -> windowedMilli(size, step)
        else               -> throw UnsupportedOperationException("Not supported chrono unit. [$unit]")
    }
}

@JvmOverloads
fun ReadableTemporalInterval.windowedYear(size: Int, step: Int = 1): Sequence<List<ZonedDateTime>> {
    require(size > 0) { "size must positive number [$size]" }
    require(step > 0) { "step must positive number [$step]" }

    return sequence {
        var current = startDateTime.startOfYear()
        val increment = step.yearPeriod()

        while(current < endDateTime) {
            yield(List(size) { current + it.yearPeriod() }.takeWhile { it < endDateTime })
            current += increment
        }
    }
}

@JvmOverloads
fun ReadableTemporalInterval.windowedMonth(size: Int, step: Int = 1): Sequence<List<ZonedDateTime>> {
    require(size > 0) { "windowed size must be positive value. [$size]" }
    require(step > 0) { "step must positive number [$step]" }

    return sequence {
        var current = startDateTime.startOfMonth()
        val increment = step.monthPeriod()

        while(current < endDateTime) {
            yield(List(size) { current + it.monthPeriod() }.takeWhile { it < endDateTime })
            current += increment
        }
    }
}

@JvmOverloads
fun ReadableTemporalInterval.windowedWeek(size: Int, step: Int = 1): Sequence<List<ZonedDateTime>> {
    require(size > 0) { "windowed size must be positive value. [$size]" }
    require(step > 0) { "step must positive number [$step]" }

    return sequence {
        var current = startDateTime.startOfWeek()
        val increment = step.weekPeriod()

        while(current < endDateTime) {
            yield(List(size) { current + it.weekPeriod() }.takeWhile { it < endDateTime })
            current += increment
        }
    }
}

@JvmOverloads
fun ReadableTemporalInterval.windowedDay(size: Int, step: Int = 1): Sequence<List<ZonedDateTime>> {
    require(size > 0) { "windowed size must be positive value. [$size]" }
    require(step > 0) { "step must positive number [$step]" }

    return sequence {
        var current = startDateTime.startOfDay()
        val increment = step.dayPeriod()

        while(current < endDateTime) {
            yield(List(size) { current + it.dayPeriod() }.takeWhile { it < endDateTime })
            current += increment
        }
    }
}

@JvmOverloads
fun ReadableTemporalInterval.windowedHour(size: Int, step: Int = 1): Sequence<List<ZonedDateTime>> {
    require(size > 0) { "windowed size must be positive value. [$size]" }
    require(step > 0) { "step must positive number [$step]" }

    return sequence {
        var current = startDateTime.truncatedTo(ChronoUnit.HOURS)
        val increment = step.hours()

        while(current < endDateTime) {
            yield(List(size) { current + it.hours() }.takeWhile { it < endDateTime })
            current += increment
        }
    }
}

@JvmOverloads
fun ReadableTemporalInterval.windowedMinute(size: Int, step: Int = 1): Sequence<List<ZonedDateTime>> {
    require(size > 0) { "windowed size must be positive value. [$size]" }
    require(step > 0) { "step must positive number [$step]" }

    return sequence {
        var current = startDateTime.truncatedTo(ChronoUnit.MINUTES)
        val increment = step.minutes()

        while(current < endDateTime) {
            yield(List(size) { current + it.minutes() }.takeWhile { it < endDateTime })
            current += increment
        }
    }
}

@JvmOverloads
fun ReadableTemporalInterval.windowedSecond(size: Int, step: Int = 1): Sequence<List<ZonedDateTime>> {
    require(size > 0) { "windowed size must be positive value. [$size]" }
    require(step > 0) { "step must positive number [$step]" }

    return sequence {
        var current = startDateTime.truncatedTo(ChronoUnit.SECONDS)
        val increment = step.seconds()

        while(current < endDateTime) {
            yield(List(size) { current + it.seconds() }.takeWhile { it < endDateTime })
            current += increment
        }
    }
}

@JvmOverloads
fun ReadableTemporalInterval.windowedMilli(size: Int, step: Int = 1): Sequence<List<ZonedDateTime>> {
    require(size > 0) { "windowed size must be positive value. [$size]" }
    require(step > 0) { "step must positive number [$step]" }

    return sequence {
        var current = startDateTime.truncatedTo(ChronoUnit.MILLIS)
        val increment = step.millis()

        while(current < endDateTime) {
            yield(List(size) { current + it.millis() }.takeWhile { it < endDateTime })
            current += increment
        }
    }
}

//
// Zip with Next
//
fun ReadableTemporalInterval.zipWithNext(unit: ChronoUnit): Sequence<Pair<ZonedDateTime, ZonedDateTime>> {
    return when(unit) {
        ChronoUnit.YEARS   -> zipWithNextYear()
        ChronoUnit.MONTHS  -> zipWithNextMonth()
        ChronoUnit.WEEKS   -> zipWithNextWeek()
        ChronoUnit.DAYS    -> zipWithNextDay()
        ChronoUnit.HOURS   -> zipWithNextHour()
        ChronoUnit.MINUTES -> zipWithNextMinute()
        ChronoUnit.SECONDS -> zipWithNextSecond()
        ChronoUnit.MILLIS  -> zipWithNextMilli()
        else               -> throw UnsupportedOperationException("Not supported chrono unit. [$unit]")
    }
}

fun ReadableTemporalInterval.zipWithNextYear(): Sequence<Pair<ZonedDateTime, ZonedDateTime>> =
    sequence {
        var current = startDateTime.startOfYear()
        val increment = 1.yearPeriod()
        val limit = endDateTime - increment

        while(current < limit) {
            yield(Pair(current, current + increment))
            current += increment
        }
    }

fun ReadableTemporalInterval.zipWithNextMonth(): Sequence<Pair<ZonedDateTime, ZonedDateTime>> =
    sequence {
        var current = startDateTime.startOfMonth()
        val increment = 1.monthPeriod()
        val limit = endDateTime - increment

        while(current < limit) {
            yield(Pair(current, current + increment))
            current += increment
        }
    }

fun ReadableTemporalInterval.zipWithNextWeek(): Sequence<Pair<ZonedDateTime, ZonedDateTime>> =
    sequence {
        var current = startDateTime.startOfWeek()
        val increment = 1.weekPeriod()
        val limit = endDateTime - increment

        while(current < limit) {
            yield(Pair(current, current + increment))
            current += increment
        }
    }

fun ReadableTemporalInterval.zipWithNextDay(): Sequence<Pair<ZonedDateTime, ZonedDateTime>> =
    sequence {
        var current = startDateTime.startOfDay()
        val increment = 1.dayPeriod()
        val limit = endDateTime - increment

        while(current < limit) {
            yield(Pair(current, current + increment))
            current += increment
        }
    }

fun ReadableTemporalInterval.zipWithNextHour(): Sequence<Pair<ZonedDateTime, ZonedDateTime>> =
    sequence {
        var current = startDateTime.truncatedTo(ChronoUnit.HOURS)
        val increment = 1.hours()
        val limit = endDateTime - increment

        while(current < limit) {
            yield(Pair(current, current + increment))
            current += increment
        }
    }

fun ReadableTemporalInterval.zipWithNextMinute(): Sequence<Pair<ZonedDateTime, ZonedDateTime>> =
    sequence {
        var current = startDateTime.truncatedTo(ChronoUnit.MINUTES)
        val increment = 1.minutes()
        val limit = endDateTime - increment

        while(current < limit) {
            yield(Pair(current, current + increment))
            current += increment
        }
    }

fun ReadableTemporalInterval.zipWithNextSecond(): Sequence<Pair<ZonedDateTime, ZonedDateTime>> =
    sequence {
        var current = startDateTime.truncatedTo(ChronoUnit.SECONDS)
        val increment = 1.seconds()
        val limit = endDateTime - increment

        while(current < limit) {
            yield(Pair(current, current + increment))
            current += increment
        }
    }

fun ReadableTemporalInterval.zipWithNextMilli(): Sequence<Pair<ZonedDateTime, ZonedDateTime>> =
    sequence {
        var current = startDateTime.truncatedTo(ChronoUnit.MILLIS)
        val increment = 1.millis()
        val limit = endDateTime - increment

        while(current < limit) {
            yield(Pair(current, current + increment))
            current += increment
        }
    }