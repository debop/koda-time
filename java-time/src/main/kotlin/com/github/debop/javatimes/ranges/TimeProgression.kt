package com.github.debop.javatimes.ranges

import java.sql.Time
import java.time.Duration

/**
 * A Progression of value of [Time]
 *
 * @autor debop
 * @since 18. 4. 16
 */
class TimeProgression(start: Time, endInclusive: Time, step: Duration) : DateProgression<Time>(start, endInclusive, step) {}

fun timeProgressionOf(start: Time, endInclusive: Time, step: Duration): TimeProgression =
    TimeProgression(start, endInclusive, step)