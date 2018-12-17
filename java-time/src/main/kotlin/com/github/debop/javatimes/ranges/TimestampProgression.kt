package com.github.debop.javatimes.ranges

import java.sql.Timestamp
import java.time.Duration

/**
 * A Progression of value of [java.time.temporal.Temporal]
 *
 * @autor debop
 * @since 18. 4. 16
 */
open class TimestampProgression(start: Timestamp, endInclusive: Timestamp, step: Duration)
    : DateProgression<Timestamp>(start, endInclusive, step) {
}

fun timestampProgressionOf(start: Timestamp, endInclusive: Timestamp, step: Duration): TimestampProgression =
    TimestampProgression(start, endInclusive, step)