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
import com.github.debop.javatimes.hours
import com.github.debop.javatimes.minus
import com.github.debop.javatimes.plus
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldEqualTo
import org.amshove.kluent.shouldNotEqual
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.Duration
import java.util.Date


class DateProgressionTest : AbstractJavaTimesTest() {

    @Test
    fun `create simple`() {
        val start = Date()
        val endInclusive = start + Duration.ofDays(1).toMillis()

        val progression = DateProgression.fromClosedRange(start, endInclusive, 1.hours())

        progression.first shouldEqual start
        progression.last shouldEqual endInclusive
        progression.step shouldEqual 1.hours()

        progression.toList().size shouldEqualTo 25
    }

    @Test
    fun `zero step`() {
        val instant = Date()

        assertThrows<IllegalArgumentException> {
            DateProgression.fromClosedRange(instant, instant, Duration.ZERO)
        }
    }

    @Test
    fun `step greater than range`() {
        val start = Date()
        val endInclusive = start + Duration.ofDays(1).toMillis()

        val progression = DateProgression.fromClosedRange(start, endInclusive, Duration.ofDays(7))

        progression.first shouldEqual start
        // last is not endInclusive, step over endInclusive, so last is equal to start
        progression.last shouldEqual start
        progression.step shouldEqual 7.days()

        progression.toList().size shouldEqualTo 1
    }

    @Test
    fun `stepping not exact endInclusive`() {
        val start = Date()
        val endInclusive = start + Duration.ofDays(1).toMillis()

        val progression = DateProgression.fromClosedRange(start, endInclusive, Duration.ofHours(5))

        progression.first shouldEqual start
        progression.last shouldEqual start + 20.hours()
        progression.last shouldNotEqual endInclusive

        progression.toList().size shouldEqualTo 5
    }

    @Test
    fun `downTo progression`() {
        val start = Date()
        val endInclusive = start - Duration.ofDays(5).toMillis()
        val step = Duration.ofDays(-1L)

        val progression = DateProgression.fromClosedRange(start, endInclusive, step)

        progression.first shouldEqual start
        progression.last shouldEqual endInclusive

        progression.toString() shouldEqual "$start downTo $endInclusive step ${step.negated()}"

        val list = progression.toList()
        list.size shouldEqualTo 6
        list.first() shouldEqual start
        list.last() shouldEqual endInclusive

    }
}