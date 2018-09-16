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
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.ZonedDateTime
import java.time.temporal.Temporal

abstract class TemporalRangeTest<T> : AbstractJavaTimesTest() where T : Temporal, T : Comparable<T> {

    abstract val start: T
    @Suppress("UNCHECKED_CAST")
    val endInclusive: T get() = (start + Duration.ofDays(1)) as T
    abstract val range: TemporalRange<T>

    @Test fun `simple constructor`() {
        val range = TemporalRange.of(start, endInclusive)

        assertEquals(start, range.start)
        assertEquals(endInclusive, range.endInclusive)
        assertEquals(start, range.first)
        assertEquals(endInclusive, range.last)
    }

    @Test fun `empty range`() {
        val range = TemporalRange.of(endInclusive, start)

        assertTrue(range.isEmpty())
        assertEquals(TemporalRange.EMPTY, range)
    }

    @Test fun `create by rangeTo`() {
        val range1 = range
        val range2 = TemporalRange.of(start, endInclusive)
        assertEquals(range2, range1)
    }
}

class LocalDateTimeRangeTest : TemporalRangeTest<LocalDateTime>() {
    override val start: LocalDateTime = LocalDateTime.now()
    override val range: TemporalRange<LocalDateTime> = start .. endInclusive
}

class OffsetDateTimeRangeTest : TemporalRangeTest<OffsetDateTime>() {
    override val start: OffsetDateTime = OffsetDateTime.now()
    override val range: TemporalRange<OffsetDateTime> = start .. endInclusive
}

class ZonedDateTimeRangeTest : TemporalRangeTest<ZonedDateTime>() {
    override val start: ZonedDateTime = ZonedDateTime.now()
    override val range: TemporalRange<ZonedDateTime> = start .. endInclusive
}

@Disabled("Cannot support range")
class LocalDateRangeTest : TemporalRangeTest<LocalDate>() {
    override val start: LocalDate = LocalDate.now()
    override val range: TemporalRange<LocalDate> = start .. endInclusive
}

@Disabled("Cannot support range")
class LocalTimeRangeTest : TemporalRangeTest<LocalTime>() {
    override val start: LocalTime = LocalTime.now()
    override val range: TemporalRange<LocalTime> = start .. endInclusive
}
