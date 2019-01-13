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
import com.github.debop.javatimes.toDate
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldNotEqual
import org.junit.jupiter.api.Test
import java.time.Duration
import java.util.Date

class DateRangeTest : AbstractJavaTimesTest() {

    @Test
    fun `simple creation`() {
        val start = Date()
        val endInclusive = (start.toInstant() + Duration.ofDays(5)).toDate()

        val range = DateRange.fromClosedRange(start, endInclusive)

        range.start shouldEqual start
        range.endInclusive shouldEqual endInclusive

        range.first shouldEqual start
        range.last shouldEqual endInclusive

        range.toString() shouldEqual "$start..$endInclusive"
    }

    @Test
    fun `when start is after from endInclusive`() {

        val start = Date()
        val endInclusive = (start.toInstant() - Duration.ofDays(1)).toDate()

        val range = DateRange.fromClosedRange(start, endInclusive)

        range.isEmpty().shouldBeFalse()
        range shouldNotEqual DateRange.EMPTY

        val range2 = start..endInclusive

        range2.isEmpty().shouldBeFalse()
        range2 shouldNotEqual DateRange.EMPTY

        range2 shouldEqual range
    }
}