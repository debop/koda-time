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

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class TimeIntervalChunkTest: AbstractKodaTimesTest() {

    @Test
    fun `chunk years`() {
        val start = now().startOfYear()
        val endExclusive = start + 5.years()
        logger.debug { "start=$start, end=$endExclusive" }
        val interval = start..endExclusive

        val chunks = interval.chunkYear(4).toList()
        chunks.forEach(::println)

        assertEquals(2, chunks.size)

        chunks.forEach { chunk ->
            logger.debug { "chunk=$chunk" }
            assertTrue { chunk.size <= 4 }
            assertTrue { chunk.first() in interval }
            assertTrue { chunk.last() in interval }
        }

        assertFailsWith(IllegalArgumentException::class) {
            interval.chunkYear(0)
        }

        assertFailsWith(IllegalArgumentException::class) {
            interval.chunkYear(-1)
        }
    }

    @Test
    fun `chunk year and aggregate`() {
        val start = now().startOfYear()
        val endExclusive = start + 5.years()
        logger.debug("start=$start, end=$endExclusive")
        val interval = start..endExclusive

        val intervals = interval.chunkYear(3).map { years -> years.first()..years.last() }.toList()

        assertEquals(2, intervals.size)

        intervals.forEach { _ ->
            assertTrue { interval.contains(interval) }
        }
    }

    @Test
    fun `chunk months`() {
        val start = now().startOfMonth()
        val endExclusive = start + 13.months()
        logger.debug("start=$start, end=$endExclusive")
        val interval = start..endExclusive

        val chunks = interval.chunkMonth(5).toList()

        chunks.forEach(::println)
        assertEquals(3, chunks.size)

        chunks.forEach {
            logger.debug("chunk=$it")
            assertTrue { it.size <= 5 }
            assertTrue { it.first() in interval }
            assertTrue { it.last() in interval }
        }

        assertFailsWith(IllegalArgumentException::class) {
            interval.chunkMonth(0)
        }

        assertFailsWith(IllegalArgumentException::class) {
            interval.chunkMonth(-1)
        }
    }

    @Test
    fun `chunk month and aggregate`() {
        val start = now().startOfMonth()
        val endExclusive = start + 13.months()
        logger.debug("start=$start, end=$endExclusive")
        val interval = start..endExclusive

        val intervals = interval.chunkMonth(5).map { months -> months.first()..months.last() }.toList()

        assertEquals(3, intervals.size)

        intervals.forEach { _ ->
            assertTrue { interval.contains(interval) }
        }
    }

    @Test
    fun `chunk week`() {
        val start = now().startOfWeek()
        val endExclusive = start + 5.weeks()
        logger.debug("start=$start, end=$endExclusive")
        val interval = start..endExclusive

        val chunks = interval.chunkWeek(2).toList()

        chunks.forEach(::println)
        assertEquals(3, chunks.size)

        chunks.forEach {
            logger.debug("chunk=$it")
            assertTrue { it.size <= 2 }
            assertTrue { it.first() in interval }
            assertTrue { it.last() in interval }
        }

        assertFailsWith(IllegalArgumentException::class) {
            interval.chunkWeek(0)
        }

        assertFailsWith(IllegalArgumentException::class) {
            interval.chunkWeek(-1)
        }

    }

    @Test
    fun `chunk week and aggregate`() {
        val start = now().startOfWeek()
        val endExclusive = start + 5.weeks()
        logger.debug("start=$start, end=$endExclusive")
        val interval = start..endExclusive

        val intervals = interval.chunkWeek(2).map { it.first()..it.last() }.toList()

        intervals.forEach(::println)
        assertEquals(3, intervals.size)

        intervals.forEach {
            assertTrue { interval.contains(interval) }
        }
    }

    @Test
    fun `chunk days`() {
        val start = now().startOfDay()
        val endExclusive = start + 66.days()
        logger.debug("start=$start, end=$endExclusive")
        val interval = start..endExclusive

        val chunks = interval.chunkDay(30).toList()

        chunks.forEach(::println)
        assertEquals(3, chunks.size)

        chunks.forEach {
            logger.debug("chunk=$it")
            assertTrue { it.size <= 30 }
            assertTrue { it.first() in interval }
            assertTrue { it.last() in interval }
        }

        assertFailsWith(IllegalArgumentException::class) {
            interval.chunkDay(0)
        }

        assertFailsWith(IllegalArgumentException::class) {
            interval.chunkDay(-1)
        }

    }

    @Test
    fun `chunk day and aggregate`() {
        val start = now().startOfDay()
        val endExclusive = start + 66.days()
        logger.debug("start=$start, end=$endExclusive")
        val interval = start..endExclusive

        val intervals = interval.chunkDay(30).map { days -> days.first()..days.last() }.toList()

        intervals.forEach(::println)
        assertEquals(3, intervals.size)

        intervals.forEach {
            assertTrue { interval.contains(interval) }
        }
    }

    @Test
    fun `chunk hours`() {
        val start = now().trimToHour()
        val endExclusive = start + 66.hours()
        logger.debug("start=$start, end=$endExclusive")
        val interval = start..endExclusive

        val chunks = interval.chunkHour(20).toList()

        chunks.forEach(::println)
        assertEquals(4, chunks.size)

        chunks.forEach {
            logger.debug("chunk=$it")
            assertTrue { it.size <= 20 }
            assertTrue { it.first() in interval }
            assertTrue { it.last() in interval }
        }

        assertFailsWith(IllegalArgumentException::class) {
            interval.chunkHour(0)
        }

        assertFailsWith(IllegalArgumentException::class) {
            interval.chunkHour(-1)
        }

    }

    @Test
    fun `chunk hour and aggregate`() {
        val start = now().trimToHour()
        val endExclusive = start + 66.hours()
        logger.debug("start=$start, end=$endExclusive")
        val interval = start..endExclusive

        val intervals = interval.chunkHour(20).map { it.first()..it.last() }.toList()

        intervals.forEach(::println)
        assertEquals(4, intervals.size)

        intervals.forEach {
            assertTrue { interval.contains(interval) }
        }
    }

    @Test
    fun `chunk minute`() {
        val start = now().trimToMinute()
        val endExclusive = start + 33.minutes()
        logger.debug("start=$start, end=$endExclusive")
        val interval = start..endExclusive

        val chunks = interval.chunkMinute(10).toList()

        chunks.forEach(::println)
        assertEquals(4, chunks.size)

        chunks.forEach {
            logger.debug("chunk=$it")
            assertTrue { it.size <= 10 }
            assertTrue { it.first() in interval }
            assertTrue { it.last() in interval }
        }

        assertFailsWith(IllegalArgumentException::class) {
            interval.chunkMinute(0)
        }

        assertFailsWith(IllegalArgumentException::class) {
            interval.chunkMinute(-1)
        }

    }

    @Test
    fun `chunk minute and aggregate`() {
        val start = now().trimToMinute()
        val endExclusive = start + 33.minutes()
        logger.debug("start=$start, end=$endExclusive")
        val interval = start..endExclusive

        val intervals = interval.chunkMinute(10).map { it.first()..it.last() }.toList()

        intervals.forEach(::println)
        assertEquals(4, intervals.size)

        intervals.forEach { _ ->
            assertTrue { interval.contains(interval) }
        }
    }

    @Test
    fun `chunk second`() {
        val start = now().trimToSecond()
        val endExclusive = start + 33.seconds()
        logger.debug("start=$start, end=$endExclusive")
        val interval = start..endExclusive

        val chunks = interval.chunkSecond(10).toList()

        chunks.forEach(::println)
        assertEquals(4, chunks.size)

        chunks.forEach {
            logger.debug("chunk=$it")
            assertTrue { it.size <= 10 }
            assertTrue { it.first() in interval }
            assertTrue { it.last() in interval }
        }

        assertFailsWith(IllegalArgumentException::class) {
            interval.chunkSecond(0)
        }

        assertFailsWith(IllegalArgumentException::class) {
            interval.chunkSecond(-1)
        }
    }

    @Test
    fun `chunk second and aggregate`() {
        val start = now().trimToSecond()
        val endExclusive = start + 33.seconds()
        logger.debug("start=$start, end=$endExclusive")
        val interval = start..endExclusive

        val intervals = interval.chunkSecond(10).map { it.first()..it.last() }.toList()

        intervals.forEach(::println)
        assertEquals(4, intervals.size)

        intervals.forEach { _ ->
            assertTrue { interval.contains(interval) }
        }
    }
}