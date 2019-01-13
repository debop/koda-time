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
import com.github.debop.javatimes.days
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldNotEqual
import org.junit.jupiter.api.Test
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class InstantRangeTest : AbstractJavaTimesTest() {

    @Test
    fun `simple creation`() {

        val start = Instant.ofEpochSecond(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))

        val endInclusive = start + 5.days()
        val range = InstantRange(start, endInclusive)

        range.start shouldEqual start
        range.endInclusive shouldEqual endInclusive

        range.first shouldEqual start
        range.last shouldEqual endInclusive

        range.toString() shouldEqual "$start..$endInclusive"
    }

    @Test
    fun `empty range`() {

        val start = Instant.ofEpochSecond(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
        val endInclusive = start - 1.days()

        val range = InstantRange.fromClosedRange<Instant>(start, endInclusive)

        range.isEmpty().shouldBeFalse()
        range shouldNotEqual TemporalRange.EMPTY

        val range2 = start..endInclusive
        range2.isEmpty().shouldBeFalse()
        range2 shouldNotEqual TemporalRange.EMPTY

        range2 shouldEqual range
    }
}