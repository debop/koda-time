package com.github.debop.javatimes.ranges

import mu.KLogging
import java.sql.Time

/**
 * A range of `java.sql.Time`
 *
 * @autor debop
 * @since 18. 4. 16
 */
class TimeRange(start: Time, endInclusive: Time) : DateRange(start, endInclusive) {

    companion object : KLogging() {
        @JvmField
        val EMPTY: TimeRange = TimeRange(Time(1L), Time(0L))

        @JvmStatic
        fun fromClosedRange(start: Time, end: Time): TimeRange = TimeRange(start, end)
    }
}