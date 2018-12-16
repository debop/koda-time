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

package com.github.debop.kodatimes.ranges

import com.github.debop.kodatimes.AbstractKodaTimesTest
import com.github.debop.kodatimes.dayDuration
import com.github.debop.kodatimes.dayDurationOf
import com.github.debop.kodatimes.days
import com.github.debop.kodatimes.hourDuration
import com.github.debop.kodatimes.hours
import com.github.debop.kodatimes.minus
import com.github.debop.kodatimes.now
import com.github.debop.kodatimes.plus
import com.github.debop.kodatimes.standardDays
import com.github.debop.kodatimes.standardHours
import com.github.debop.kodatimes.today
import com.github.debop.kodatimes.unaryMinus
import mu.KLogging
import org.joda.time.Duration
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotEquals

class InstantProgressionTest : AbstractKodaTimesTest() {

    companion object : KLogging()

    @Test
    fun `create simple`() {
        val start = today().toInstant()
        val endInclusive = start + 1.days()

        val progression = InstantProgression.fromClosedRange(start, endInclusive, standardHours(1))

        assertEquals(start, progression.first)
        assertEquals(endInclusive, progression.last)
        assertEquals(standardHours(1), progression.step)

        val list = progression.toList()
        assertEquals(25, list.count())
    }

    @Test
    fun `zero step`() {
        val instant = now().toInstant()

        assertFailsWith(IllegalArgumentException::class) {
            InstantProgression.fromClosedRange(instant, instant, Duration(0))
        }
    }

    @Test
    fun `step greater than range`() {
        val start = today().toInstant()
        val endInclusive = start + 1.days()

        val progression = InstantProgression.fromClosedRange(start, endInclusive, standardDays(7))

        assertEquals(start, progression.first)
        // last is not endInclusive, step over endInclusive, so last is equal to start
        assertEquals(start, progression.last)
        assertEquals(7.dayDuration(), progression.step)

        val list = progression.toList()
        logger.debug { "list=$list" }
        assertEquals(1, list.count())
    }

    @Test
    fun `stepping not exact endInclusive`() {
        val start = today().toInstant()
        val endInclusive = start + 1.days()

        val progression = InstantProgression.fromClosedRange(start, endInclusive, 5.hourDuration())

        assertEquals(start, progression.first)
        assertEquals(start + 20.hours(), progression.last)
        assertNotEquals(endInclusive, progression.last)

        println("progression=$progression")

        val list = progression.toList()
        logger.debug { "list=$list" }
        assertEquals(5, list.count())

        // Instant 는 GMT 기준이라 Local time과는 시간차가 있습니다. 그래서 해당 Timezone 으로 변경해야 합니다.
        assertEquals(listOf(0, 5, 10, 15, 20), list.map { it.toDateTime().hourOfDay })
    }

    @Test
    fun `downTo progression`() {
        val start = today().toInstant()
        val endInclusive = start - 5.days()
        val step = dayDurationOf(-1)

        val progression = InstantProgression.fromClosedRange(start, endInclusive, step)

        assertEquals(start, progression.first)
        assertEquals(endInclusive, progression.last)
        assertEquals("$start downTo $endInclusive step ${-step}", progression.toString())

        val list = progression.toList()
        logger.debug { "list=$list" }
        assertEquals(6, list.count())
        assertEquals(start, list.first())
        assertEquals(endInclusive, list.last())
    }
}