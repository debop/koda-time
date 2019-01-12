@file:Suppress("UNCHECKED_CAST")

package com.github.debop.javatimes.ranges

import com.github.debop.javatimes.days
import com.github.debop.javatimes.hours
import com.github.debop.javatimes.minutes
import com.github.debop.javatimes.monthPeriod
import com.github.debop.javatimes.seconds
import com.github.debop.javatimes.weekPeriod
import com.github.debop.javatimes.yearPeriod
import java.time.temporal.Temporal

fun <T> TemporalRange<T>.chunkYear(size: Int): Sequence<List<T>> where T : Temporal, T : Comparable<T> {
    require(size > 0) { "size must be positive number. [$size]" }

    return sequence {
        var current = start
        val increment = size.yearPeriod()
        while(current <= endInclusive) {
            yield(List(size) { (current + it.yearPeriod()) as T }.takeWhile { it <= endInclusive })
            current = (current + increment) as T
        }
    }
}

fun <T> TemporalRange<T>.chunkMonth(size: Int): Sequence<List<T>> where T : Temporal, T : Comparable<T> {
    require(size > 0) { "size must be positive number. [$size]" }

    return sequence {
        var current = start
        val increment = size.monthPeriod()
        while(current <= endInclusive) {
            yield(List(size) { (current + it.monthPeriod()) as T }.takeWhile { it <= endInclusive })
            current = (current + increment) as T
        }
    }
}

fun <T> TemporalRange<T>.chunkWeek(size: Int): Sequence<List<T>> where T : Temporal, T : Comparable<T> {
    require(size > 0) { "size must be positive number. [$size]" }

    return sequence {
        var current = start
        val increment = size.weekPeriod()
        while(current <= endInclusive) {
            yield(List(size) { (current + it.weekPeriod()) as T }.takeWhile { it <= endInclusive })
            current = (current + increment) as T
        }
    }
}


fun <T> TemporalRange<T>.chunkDay(size: Int): Sequence<List<T>> where T : Temporal, T : Comparable<T> {
    require(size > 0) { "size must be positive number. [$size]" }

    return sequence {
        var current = start
        val increment = size.days()
        while(current <= endInclusive) {
            yield(List(size) { (current + it.days()) as T }.takeWhile { it <= endInclusive })
            current = (current + increment) as T
        }
    }
}

fun <T> TemporalRange<T>.chunkHour(size: Int): Sequence<List<T>> where T : Temporal, T : Comparable<T> {
    require(size > 0) { "size must be positive number. [$size]" }

    return sequence {
        var current = start
        val increment = size.hours()
        while(current <= endInclusive) {
            yield(List(size) { (current + it.hours()) as T }.takeWhile { it <= endInclusive })
            current = (current + increment) as T
        }
    }
}

fun <T> TemporalRange<T>.chunkMinute(size: Int): Sequence<List<T>> where T : Temporal, T : Comparable<T> {
    require(size > 0) { "size must be positive number. [$size]" }

    return sequence {
        var current = start
        val increment = size.minutes()
        while(current <= endInclusive) {
            yield(List(size) { (current + it.minutes()) as T }.takeWhile { it <= endInclusive })
            current = (current + increment) as T
        }
    }
}


fun <T> TemporalRange<T>.chunkSecond(size: Int): Sequence<List<T>> where T : Temporal, T : Comparable<T> {
    require(size > 0) { "size must be positive number. [$size]" }

    return sequence {
        var current = start
        val increment = size.seconds()
        while(current <= endInclusive) {
            yield(List(size) { (current + it.seconds()) as T }.takeWhile { it <= endInclusive })
            current = (current + increment) as T
        }
    }
}