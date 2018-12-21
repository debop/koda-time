package com.github.debop.javatimes.ranges

import mu.KLogging
import java.time.Duration
import java.util.*

/**
 * A range of `java.util.Date`
 *
 * @autor debop
 * @since 18. 4. 16
 */
open class DateRange(start: Date, endInclusive: Date)
    : DateProgression<Date>(start, endInclusive, Duration.ofDays(1)), ClosedRange<Date> {

    companion object : KLogging() {
        @JvmField
        val EMPTY: DateRange = DateRange(Date(1L), Date(0L))

        fun fromClosedRange(start: Date, end: Date): DateRange =
            DateRange(start, end)
    }

    override val start: Date get() = first
    override val endInclusive: Date get() = last

    override fun contains(value: Date): Boolean = first <= value && value <= last

    override fun isEmpty(): Boolean = first > last

    override fun toString(): String = "$first..$last"
}