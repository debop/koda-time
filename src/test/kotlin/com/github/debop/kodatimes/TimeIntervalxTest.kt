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

package com.github.debop.kodatimes

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.joda.time.Interval
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TimeIntervalxTest : AbstractKodaTimesTest() {

  @Test fun rangeTest() {
    val start = now()
    val end = start + 1.days()

    val range: Interval = start .. end

    assertTrue {
      range.days().all { it in start.startOfDay() .. end }
    }
    assertEquals(end.startOfDay(), range.days().last())
  }

  @Test fun rangeStepTest() {
    val start = now()
    val end = start + 1.days() + 1.millis()

    // range contains is start <= x && x < end
    (start .. end step 1.hours().toPeriod()).forEach {
      assertTrue { it in start .. end }
    }

    assertTrue((start .. end).hours().all { it in start .. end })
  }

  @Test fun `chunk years`() {
    val start = now().startOfYear()
    val endExclusive = start + 5.years()
    log.debug("start=$start, end=$endExclusive")
    val interval = start .. endExclusive

    val chunks = interval.chunkYear(4).toList()
    chunks.forEach(::println)

    assertThat(chunks.size).isEqualTo(2)

    chunks.forEach { chunk ->
      log.debug("chunk=$chunk")
      assertThat(chunk.size).isLessThanOrEqualTo(4)
      assertThat(chunk.first() in interval).isTrue()
      assertThat(chunk.last() in interval).isTrue()
    }

    assertThatThrownBy {
      interval.chunkYear(0)
    }.isInstanceOf(IllegalArgumentException::class.java)

    assertThatThrownBy {
      interval.chunkYear(-1)
    }.isInstanceOf(IllegalArgumentException::class.java)

  }

  @Test fun `chunk months`() {
    val start = now().startOfMonth()
    val endExclusive = start + 13.months()
    log.debug("start=$start, end=$endExclusive")
    val interval = start .. endExclusive

    val chunks = interval.chunkMonth(5).toList()

    chunks.forEach(::println)
    assertThat(chunks.size).isEqualTo(3)

    chunks.forEach {
      log.debug("chunk=$it")
      assertTrue { it.size <= 5 }
      assertTrue { it.first() in interval }
      assertTrue { it.last() in interval }
    }

    assertThatThrownBy {
      interval.chunkMonth(0)
    }.isInstanceOf(IllegalArgumentException::class.java)

    assertThatThrownBy {
      interval.chunkMonth(-1)
    }.isInstanceOf(IllegalArgumentException::class.java)

  }

  @Test fun `chunk month and aggregate`() {
    val start = now().startOfMonth()
    val endExclusive = start + 13.months()
    log.debug("start=$start, end=$endExclusive")
    val interval = start .. endExclusive

    val intervals = interval.chunkMonth(5).map { months -> months.first() .. months.last() }.toList()

    assertEquals(3, intervals.size)

    intervals.forEach {
      assertTrue { interval.contains(interval) }
    }
  }

  @Test fun `chunk days`() {
    val start = now().startOfDay()
    val endExclusive = start + 66.days()
    log.debug("start=$start, end=$endExclusive")
    val interval = start .. endExclusive

    val chunks = interval.chunkDay(30).toList()

    chunks.forEach(::println)
    assertThat(chunks.size).isEqualTo(3)

    chunks.forEach {
      log.debug("chunk=$it")
      assertTrue { it.size <= 30 }
      assertTrue { it.first() in interval }
      assertTrue { it.last() in interval }
    }

    assertThatThrownBy {
      interval.chunkDay(0)
    }.isInstanceOf(IllegalArgumentException::class.java)

    assertThatThrownBy {
      interval.chunkDay(-1)
    }.isInstanceOf(IllegalArgumentException::class.java)

  }

  @Test fun `chunk day and aggregate`() {
    val start = now().startOfDay()
    val endExclusive = start + 66.days()
    log.debug("start=$start, end=$endExclusive")
    val interval = start .. endExclusive

    val intervals = interval.chunkDay(30).map { days -> days.first() .. days.last() }.toList()

    intervals.forEach(::println)
    assertEquals(3, intervals.size)

    intervals.forEach {
      assertTrue { interval.contains(interval) }
    }
  }

  @Test fun `windowed year`() {
    val start = now().startOfYear()
    val endExclusive = start + 5.years()
    log.debug("start=$start, end=$endExclusive")
    val interval = start .. endExclusive

    val windowed = interval.windowedYear(3, 2)
    windowed.forEach { items ->
      log.debug("items = $items")
      assertThat(items.first() in interval).isTrue()
      assertThat(items.last() in interval).isTrue()
    }
    assertThat(windowed.count()).isEqualTo(3)
  }

  @Test
  fun `zipWithNext years`() {
    val start = now().startOfYear()
    val endExclusive = start + 5.years()
    log.debug("start=$start, end=$endExclusive")
    val interval = start .. endExclusive

    val pairs = interval.zipWithNextYear().toList()
    assertThat(pairs.size).isEqualTo(4)

    pairs.forEach { (current, next) ->
      log.debug("current=$current, next=$next")
      assertThat(current in interval).isTrue()
      assertThat(next in interval).isTrue()
    }
  }
}