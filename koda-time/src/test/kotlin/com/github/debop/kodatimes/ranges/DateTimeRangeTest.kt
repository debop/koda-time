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
import com.github.debop.kodatimes.days
import com.github.debop.kodatimes.today
import mu.KLogging
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue


class DateTimeRangeTest: AbstractKodaTimesTest() {
    companion object : KLogging()


    @Test
    fun `simple creation`() {

        val start = today()
        val endInclusive = start + 5.days()
        val range = DateTimeRange(start, endInclusive)

        assertEquals(start, range.start)
        assertEquals(endInclusive, range.endInclusive)

        assertEquals(start, range.first)
        assertEquals(endInclusive, range.last)

        assertEquals("$start..$endInclusive", range.toString())
    }

    @Test
    fun `empty range`() {

        val start = today()
        val endInclusive = start - 1.days()

        val range = DateTimeRange(start, endInclusive)

        assertTrue { range.isEmpty() }
        assertEquals(DateTimeRange.EMPTY, range)
    }

}