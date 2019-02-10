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

import mu.KLogging
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldEqualTo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.DayOfWeek

class InstantExtensionsTest : AbstractJavaTimesTest() {

    companion object : KLogging()

    @Nested
    inner class IntervalOfInstant {

        @Test
        fun `year interval of Instant`() {
            val now = nowInstant()
            val interval = now.yearInterval

            interval.start shouldEqual now.startOfYear()
            interval.end shouldEqual now.startOfYear().toLocalDateTime().plusYears(1).minusNanos(1).toInstant()
        }

        @Test
        fun `month interval of Instant`() {
            val now = nowInstant()
            val interval = now.monthInterval

            interval.start shouldEqual now.startOfMonth()
            interval.end shouldEqual now.startOfMonth().toLocalDateTime().plusMonths(1).minusNanos(1).toInstant()
        }

        @Test
        fun `day interval of Instant`() {
            val now = nowInstant()
            val interval = now.dayInterval

            interval.start shouldEqual now.startOfDay()
            interval.end shouldEqual now.startOfDay().toLocalDateTime().plusDays(1).minusNanos(1).toInstant()
        }
    }

    @Nested
    inner class StartOf {

        @Test
        fun `start of year for instant`() {
            val instant = nowInstant()

            val start = instant.startOfYear()
            val utcStart = start.toZonedDateTime(UtcZoneId)

            logger.debug { "utc start of year=$utcStart" }

            with(utcStart) {
                monthValue shouldEqualTo 1
                dayOfMonth shouldEqualTo 1
                hour shouldEqualTo 0
                minute shouldEqualTo 0
                second shouldEqualTo 0
                nano shouldEqualTo 0
            }
        }

        @Test
        fun `start of month for instant`() {
            val instant = nowInstant()

            val start = instant.startOfMonth()
            val utcStart = start.toZonedDateTime(UtcZoneId)

            logger.debug { "utc start of year=$utcStart" }

            with(utcStart) {
                dayOfMonth shouldEqualTo 1
                hour shouldEqualTo 0
                minute shouldEqualTo 0
                second shouldEqualTo 0
                nano shouldEqualTo 0
            }
        }

        @Test
        fun `start of week for instant`() {
            val instant = nowInstant()

            val start = instant.startOfWeek()
            val utcStart = start.toZonedDateTime(UtcZoneId)

            logger.debug { "utc start of year=$utcStart" }

            with(utcStart) {
                dayOfWeek shouldEqual DayOfWeek.MONDAY
                hour shouldEqualTo 0
                minute shouldEqualTo 0
                second shouldEqualTo 0
                nano shouldEqualTo 0
            }
        }

        @Test
        fun `start of day for instant`() {
            val instant = nowInstant()

            val start = instant.startOfDay()
            val utcStart = start.toZonedDateTime(UtcZoneId)

            with(utcStart) {
                dayOfMonth shouldEqual start.toZonedDateTime(UtcZoneId).dayOfMonth
                hour shouldEqualTo 0
                minute shouldEqualTo 0
                second shouldEqualTo 0
                nano shouldEqualTo 0
            }
        }

        @Test
        fun `start of hour for instant`() {
            val instant = nowInstant()

            val start = instant.startOfHour()
            val utcStart = start.toZonedDateTime(UtcZoneId)

            assertEquals(start.toZonedDateTime(UtcZoneId).hour, utcStart.hour)
            assertEquals(0, utcStart.minute)
            assertEquals(0, utcStart.second)
            assertEquals(0, utcStart.nano)

            with(utcStart) {
                hour shouldEqualTo start.toZonedDateTime(UtcZoneId).hour
                minute shouldEqualTo 0
                second shouldEqualTo 0
                nano shouldEqualTo 0
            }
        }

        @Test
        fun `start of minute for instant`() {
            val instant = nowInstant()

            val start = instant.startOfMinute()
            val utcStart = start.toZonedDateTime(UtcZoneId)

            with(utcStart) {
                minute shouldEqualTo start.toZonedDateTime(UtcZoneId).minute
                second shouldEqualTo 0
                nano shouldEqualTo 0
            }
        }

        @Test
        fun `start of second for instant`() {
            val instant = nowInstant()

            val start = instant.startOfSecond()
            val utcStart = start.toZonedDateTime(UtcZoneId)

            with(utcStart) {
                second shouldEqualTo start.toZonedDateTime(UtcZoneId).second
                nano shouldEqualTo 0
            }
        }
    }
}