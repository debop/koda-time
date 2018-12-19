package com.github.debop.javatimes.ranges

import mu.KLogging
import java.io.Serializable
import java.time.Duration
import java.time.LocalDateTime
import java.time.temporal.Temporal

fun <T> temporalRangeOf(start: T, endInclusive: T): TemporalRange<T> where T : Temporal, T : Comparable<T> =
    TemporalRange.fromClosedRange(start, endInclusive)


fun <T> TemporalRange.Companion.fromClosedRange(start: T, endInclusive: T): TemporalRange<T> where T : Temporal, T : Comparable<T> {
    return TemporalRange(start, endInclusive)
}

/**
 * A range of [Temporal]
 *
 * @autor debop
 * @since 18. 4. 16
 */
class TemporalRange<T>(start: T, endInclusive: T)
    : TemporalProgression<T>(start, endInclusive, Duration.ofMillis(1L)), ClosedRange<T>, Serializable
where T : Temporal, T : Comparable<T> {

    companion object : KLogging() {
        @JvmField
        val EMPTY = TemporalRange<LocalDateTime>(LocalDateTime.MAX, LocalDateTime.MIN)

        //        @JvmStatic
        //        fun <T> fromClosedRange(start: T, endInclusive: T): TemporalRange<T> where T : Temporal, T : Comparable<T> =
        //            TemporalRange(start, endInclusive)
    }

    override val start: T get() = first

    override val endInclusive: T get() = last

    override fun contains(value: T): Boolean = value in first..last

    override fun isEmpty(): Boolean = first > last

    override fun toString(): String = "$first..$last"

}