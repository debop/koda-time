package com.github.debop.javatimes.ranges

import com.github.debop.javatimes.minus
import com.github.debop.javatimes.plus
import com.github.debop.javatimes.toEpochMillis
import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.time.temporal.Temporal
import java.util.Date


private fun mod(a: Int, b: Int): Int {
    val mod = a % b
    return if (mod >= 0) mod else mod + b
}

private fun mod(a: Long, b: Long): Long {
    val mod = a % b
    return if (mod >= 0) mod else mod + b
}

private fun differenceModulo(a: Int, b: Int, c: Int): Int =
    mod(mod(a, c) - mod(b, c), c)

private fun differenceModulo(a: Long, b: Long, c: Long): Long =
    mod(mod(a, c) - mod(b, c), c)

internal fun getProgressionLastElement(start: Instant, end: Instant, step: Duration): Instant = when {
    step > Duration.ZERO -> end.minusMillis(differenceModulo(end.toEpochMilli(), start.toEpochMilli(), step.toMillis()))
    step < Duration.ZERO -> end.plusMillis(differenceModulo(start.toEpochMilli(), end.toEpochMilli(), -step.toMillis()))
    else                 -> throw IllegalArgumentException("step must not be zero")
}

internal fun getProgressionLastElement(start: Date, end: Date, step: Duration): Date = when {
    step > Duration.ZERO -> end - differenceModulo(end.time, start.time, step.toMillis())
    step < Duration.ZERO -> end + differenceModulo(start.time, end.time, -step.toMillis())
    else                 -> throw IllegalArgumentException("step must not be zero")
}

@Suppress("UNCHECKED_CAST")
internal fun <T> getProgressionLastElement(start: T, end: T, step: Duration): T
where T : Temporal, T : Comparable<T> = when {
    step > Duration.ZERO -> end.minus(differenceModulo(end.toEpochMillis(), start.toEpochMillis(), step.toMillis()), ChronoUnit.MILLIS) as T
    step < Duration.ZERO -> end.plus(differenceModulo(start.toEpochMillis(), end.toEpochMillis(), -step.toMillis()), ChronoUnit.MILLIS) as T
    else                 -> throw IllegalArgumentException("step must not be zero")
}