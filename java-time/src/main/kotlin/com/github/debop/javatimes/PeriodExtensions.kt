/*
 * Copyright (c) 2016. Sunghyouk Bae <sunghyouk.bae@gmail.com>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
suspend fun Period.yearSequence(step: Int = 1): Sequence<Int> = sequence {
    require(step > 0) { "step must positive number. step=$step" }

    if (this@yearSequence.isZero) {
        return@sequence
    }
    var year = 0
    val years = this@yearSequence.years
    if (years > 0) {
        while (year < years) {
            yield(year)
            year += step
        }
    } else {
        while (year > years) {
            yield(year)
            year -= step
        }
    }
}

/**
 * month sequence of `java.time.Period`
 */
suspend fun Period.monthSequence(step: Int = 1): Sequence<Int> = sequence {
    require(step > 0) { "step must positive number. step=$step" }

    if (this@monthSequence.isZero) {
        return@sequence
    }
    var month = 0
    val months = this@monthSequence.months
    if (months > 0) {
        while (month < months) {
            yield(month)
            month += step
        }
    } else {
        while (month > months) {
            yield(month)
            month -= step
        }
    }
}

/**
 * day sequence of `java.time.Period`
 */
suspend fun Period.daySequence(step: Int = 1): Sequence<Int> = sequence {
    require(step > 0) { "step must positive number. step=$step" }

    if (this@daySequence.isZero) {
        return@sequence
    }
    var day = 0
    val days = this@daySequence.days
    if (days > 0) {
        while (day < days) {
            yield(day)
            day += step
        }
    } else {
        while (day > days) {
            yield(day)
            day -= step
        }
    }
}
