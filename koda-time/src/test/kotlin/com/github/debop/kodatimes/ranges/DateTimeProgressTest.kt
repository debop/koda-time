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
import com.github.debop.kodatimes.now
import com.github.debop.kodatimes.standardDays
import com.github.debop.kodatimes.standardHours
import com.github.debop.kodatimes.today
import com.github.debop.kodatimes.unaryMinus
import mu.KLogging
import org.joda.time.Duration
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class DateTimeProgressTest : AbstractKodaTimesTest() {

    companion object : KLogging()

    @Test
    fun `create simple`() {
        val start = today()
        val endInclusive = start + 1.days()

        val progression = DateTimeProgression.fromClosedRange(start, endInclusive, standardHours(1))

        assertEquals(start, progression.first)
        assertEquals(endInclusive, progression.last)
        assertEquals(standardHours(1), progression.step)

        val list = progression.toList()
        assertEquals(25, list.count())
    }

    @Test
    fun `zero step`() {
        val instant = now()

        assertThrows(IllegalArgumentException::class.java) {
            DateTimeProgression.fromClosedRange(instant, instant, Duration(0))
        }
    }

    @Test
    fun `step greater than range`() {
        val start = today()
        val endInclusive = start + 1.days()

        val progression = DateTimeProgression.fromClosedRange(start, endInclusive, standardDays(7))

        assertEquals(start, progression.first)
        // last is not endInclusive, step over endInclusive, so last is equal to start
        assertEquals(start, progression.last)
        assertEquals(7.dayDuration(), progression.step)

        val list = progression.toList()
        assertEquals(1, list.count())
    }

    @Test
    fun `stepping not exact endInclusive`() {
        val start = today()
        val endInclusive = start + 1.days()

        val progression = DateTimeProgression.fromClosedRange(start, endInclusive, 5.hourDuration())

        assertEquals(start, progression.first)
        assertEquals(start + 20.hours(), progression.last)
        assertNotEquals(endInclusive, progression.last)

        val list = progression.toList()
        assertEquals(5, list.count())
        assertEquals(listOf(0, 5, 10, 15, 20), list.map { it.hourOfDay })
    }

    @Test
    fun `downTo progression`() {
        val start = today()
        val endInclusive = start - 5.days()
        val step = dayDurationOf(-1)

        val progression = DateTimeProgression.fromClosedRange(start, endInclusive, step)

        assertEquals(start, progression.first)
        assertEquals(endInclusive, progression.last)

        assertEquals("$start downTo $endInclusive step ${-step}", progression.toString())

        val list = progression.toList()
        assertEquals(6, list.count())
        assertEquals(start, list.first())
        assertEquals(endInclusive, list.last())
    }
}