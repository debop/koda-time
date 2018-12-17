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

package com.github.debop.javatimes

import mu.KLogging
import org.junit.jupiter.api.Test
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue


class JavatimexTest {

    companion object : KLogging()

    @Test
    fun `system default zone`() {
        logger.debug {
            """
            |
            |SystemTimeZone=$SystemTimeZone
            |SystemZoneId  =$SystemZoneId
            |SystemOffset  =$SystemOffset
            |
            |UtcTimeZone   =$UtcTimeZone
            |UtcZoneId     =$UtcZoneId
            |UtcOffset     =$UtcOffset
            |
            """.trimMargin()
        }
    }

    @Test
    fun `print ISO format`() {
        val now = nowLocalDateTime()
        val today = now.startOfDay()

        logger.debug { "now=${now.toIsoString()}" }
        logger.debug { "today=${today.toIsoString()}" }
        logger.debug { "today=${today.toIsoDateString()}" }

        assertTrue(today.toIsoString().contains("T00:00:00"))

        val parsedToday = LocalDate.parse(today.toIsoDateString())
        assertEquals(today.toLocalDate(), parsedToday)

        val parsedNow = LocalDateTime.parse(now.toIsoString())
        assertEquals(now, parsedNow)
    }

    @Test
    fun `now and today`() {
        assertEquals(nowInstant().truncatedTo(ChronoUnit.DAYS), todayInstant())

        val today = nowInstant(UtcZoneId).truncatedTo(ChronoUnit.SECONDS)

        assertEquals(today, nowLocalDateTime().trimToSecond().toInstant())
        assertEquals(today, nowOffsetDateTime().trimToSecond().toInstant())
        assertEquals(today, nowZonedDateTime().trimToSecond().toInstant())

        assertEquals(todayOffsetDateTime().toInstant(), todayZonedDateTime().toOffsetDateTime().toInstant())
        assertEquals(todayZonedDateTime().toInstant(), todayOffsetDateTime().toZonedDateTime().toInstant())
    }

    @Test
    fun `conversion Instant, LocalDateTime, OffsetDateTime, ZonedDateTime`() {

        val nowInstant = nowInstant()

        // val local = nowInstant.toLocalDateTime()

        logger.debug {
            """
            |
            |nowInstant        = $nowInstant
            |nowLocalDateTime  = ${nowInstant.toLocalDateTime(SystemZoneId)}
            |nowOffsetDateTime = ${nowInstant.toOffsetDateTime()}
            |nowZonedDateTime  = ${nowInstant.toZonedDateTime()}
            """.trimMargin()
        }

        assertEquals(nowInstant, nowInstant.toLocalDateTime().toInstant())
        assertEquals(nowInstant, nowInstant.toOffsetDateTime().toInstant())
        assertEquals(nowInstant, nowInstant.toZonedDateTime().toInstant())
    }

    @Test
    fun `check constant variables`() {
        assertEquals(LocalDate.now(), nowLocalDate())
    }

    @Test
    fun `time unit conversions`() {
        assertEquals(1.micros(), 1000.nanos())
        assertEquals(1.millis(), 1000.micros())
        assertEquals(1.seconds(), 1000.millis())
        assertEquals(1.minutes(), 60.seconds())
        assertEquals(1.hours(), 60.minutes())

        assertEquals(1.weeks(), 7.days())
    }

    @Test
    fun `time operations`() {
        assertEquals(5.micros(), 5000 * 1.nanos())
        assertEquals(10.micros(), 10000 * 2.nanos() / 2)

        assertEquals(100.millis(), 10 * 10.seconds() / 1000)

        assertEquals(50.millis(), 1.seconds() - 950.millis())
    }

    @Test
    fun `number to LocalDateTime`() {
        val now = nowInstant()
        val ms = now.toEpochMilli()

        assertEquals(now, ms.toInstant())
        assertEquals(now.toLocalDateTime(), ms.toLocalDateTime())
    }

    @Test
    fun `Instant with methods`() {
        val today = nowInstant().startOfDay()
        assertEquals(0, today.toCalendar().get(Calendar.HOUR_OF_DAY))

        // 한국은 일요일이 시작일이다.
        val startOfWeek = Instant.now().startOfWeek()
        assertEquals(2, startOfWeek.toCalendar(TimeZone.getDefault()).get(Calendar.DAY_OF_WEEK))
        println(startOfWeek)

        val startOfMonth = Instant.now().startOfMonth()
        assertEquals(1, startOfMonth.toCalendar().get(Calendar.DAY_OF_MONTH))

        val startOfYear = Instant.now().startOfYear()
        assertEquals(1, startOfYear.toCalendar().get(Calendar.DAY_OF_MONTH))
        assertEquals(1, startOfYear.toCalendar().get(Calendar.DAY_OF_YEAR))
    }

    @Test
    fun `Instant arithmetic`() {
        val now = nowInstant()
        // val ms = now.toEpochMilli()

        val after5 = now + 5.days()
        val before5 = now - 5.days()

        assertEquals(after5, before5 + 10.days())
    }
}