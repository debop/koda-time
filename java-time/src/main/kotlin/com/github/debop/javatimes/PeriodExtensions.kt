package com.github.debop.javatimes

import java.time.Period
import java.time.temporal.Temporal

operator fun Period.unaryMinus(): Period = this.negated()

@Suppress("UNCHECKED_CAST")
operator fun <T : Temporal> Period.plus(instant: T): T = addTo(instant) as T

@Suppress("UNCHECKED_CAST")
operator fun <T : Temporal> Period.minus(instant: T): T = subtractFrom(instant) as T

@JvmOverloads
fun periodOf(years: Int, months: Int = 0, days: Int = 0): Period = Period.of(years, months, days)

/**
 * year sequence of `Period`
 */
suspend fun Period.yearSequence(): Sequence<Int> = sequence {
    var year = 0
    val years = this@yearSequence.years
    if(years > 0) {
        while(year < years) {
            yield(year++)
        }
    } else {
        while(year > years) {
            yield(year--)
        }
    }
}

/**
 * month sequence of `java.time.Period`
 */
suspend fun Period.monthSequence(): Sequence<Int> = sequence {
    var month = 0
    val months = this@monthSequence.months
    if(months > 0) {
        while(month < months) {
            yield(month++)
        }
    } else {
        while(month > months) {
            yield(month--)
        }
    }
}

/**
 * day sequence of `java.time.Period`
 */
suspend fun Period.daySequence(): Sequence<Int> = sequence {
    var day = 0
    val days = this@daySequence.days
    if(days > 0) {
        while(day < days) {
            yield(day++)
        }
    } else {
        while(day > days) {
            yield(day--)
        }
    }
}