package com.github.debop.javatimes.ranges

import mu.KLogging
import java.sql.Timestamp

/**
 * A range of `java.sql.Timestamp`
 *
 * @autor debop
 * @since 18. 4. 16
 */
class TimestampRange(start: Timestamp, endInclusive: Timestamp) : DateRange(start, endInclusive) {

    companion object : KLogging() {
        @JvmField
        val EMPTY: TimestampRange = TimestampRange(Timestamp(1L), Timestamp(0L))

        @JvmStatic
        fun fromClosedRange(start: Timestamp, end: Timestamp): TimestampRange = TimestampRange(start, end)

    }
}