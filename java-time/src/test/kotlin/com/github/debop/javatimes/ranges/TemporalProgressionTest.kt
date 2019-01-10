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

import com.github.debop.javatimes.AbstractJavaTimesTest
import com.github.debop.javatimes.hours
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldNotEqual
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.ZonedDateTime
import java.time.temporal.Temporal


abstract class TemporalProgressionTest<T> : AbstractJavaTimesTest() where T : Temporal, T : Comparable<T> {

    abstract val start: T

    @Suppress("UNCHECKED_CAST")
    val endInclusive: T
        get() = (start + Duration.ofDays(1)) as T

    @Test
    fun `construcot LocalDateTimeProgression`() {

        val progression = TemporalProgression.fromClosedRange(start, endInclusive, 1.hours())

        with(progression) {
            first shouldEqual start
            last shouldEqual endInclusive
            step shouldEqual 1.hours()

            toList().size shouldEqual 25
        }
    }

    @Test
    fun `zero step`() {
        val temporal = start

        assertThrows<IllegalArgumentException> {
            TemporalProgression.fromClosedRange(temporal, temporal, Duration.ZERO)
        }
    }

    @Test
    fun `step greater than range`() {
        val progression = TemporalProgression.fromClosedRange(start, endInclusive, Duration.ofDays(7))

        assertEquals(start, progression.first)
        // last is not endInclusive, step over endInclusive, so last is equal to start
        assertEquals(start, progression.last)
        assertEquals(Duration.ofDays(7), progression.step)

        assertEquals(1, progression.toList().size)
    }

    @Test
    fun `stepping not exact endInclusive`() {
        val progression = TemporalProgression.fromClosedRange(start, endInclusive, Duration.ofHours(5))

        with(progression) {
            first shouldEqual start
            last shouldEqual start + 20.hours()
            last shouldNotEqual endInclusive
            step shouldEqual 5.hours()

            toList().size shouldEqual 5
        }
    }

    @Test
    fun `downTo progression`() {

        val step = Duration.ofHours(-1)
        val progression = TemporalProgression.fromClosedRange(endInclusive, start, step)

        with(progression) {
            first shouldEqual endInclusive
            last shouldEqual start
            step shouldEqual (-1).hours()

            toString() shouldEqual "$endInclusive downTo $start step ${step.negated()}"

            with(toList()) {
                size shouldEqual 25
                first() shouldEqual endInclusive
                last() shouldEqual start
            }
        }
    }
}

class LocalDateTimeProgressionTest : TemporalProgressionTest<LocalDateTime>() {
    override val start: LocalDateTime = LocalDateTime.now()
}

@Disabled("Cannot support range")
class LocalDateProgressionTest : TemporalProgressionTest<LocalDate>() {
    override val start: LocalDate = LocalDate.now()
}

@Disabled("Cannot support range")
class LocalTimeProgressionTest : TemporalProgressionTest<LocalTime>() {
    override val start: LocalTime = LocalTime.now()
}

class OffsetDateTimeProgressionTest : TemporalProgressionTest<OffsetDateTime>() {
    override val start: OffsetDateTime = OffsetDateTime.now()
}

class ZonedDateTimeProgressionTest : TemporalProgressionTest<ZonedDateTime>() {
    override val start: ZonedDateTime = ZonedDateTime.now()
}