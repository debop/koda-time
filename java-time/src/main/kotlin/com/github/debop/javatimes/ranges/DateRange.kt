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

package com.github.debop.javatimes.ranges

import mu.KLogging
import java.time.Duration
import java.util.Date

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

        fun fromClosedRange(start: Date, end: Date): DateRange = when {
            start <= end -> DateRange(start, end)
            else         -> DateRange(end, start)
        }
    }

    override val start: Date get() = first
    override val endInclusive: Date get() = last

    @Suppress("ConvertTwoComparisonsToRangeCheck")
    override fun contains(value: Date): Boolean = first <= value && value <= last

    override fun isEmpty(): Boolean = first > last

    override fun toString(): String = "$first..$last"
}