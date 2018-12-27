package com.github.debop.javatimes

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.DayOfWeek

class InstantExtensionsTest : AbstractJavaTimesTest() {

    @Test
    fun `start of year for instant`() {
        val instant = nowInstant()

        val start = instant.startOfYear()
        val utcStart = start.toZonedDateTime(UtcZoneId)

        logger.debug { "utc start of year=$utcStart" }

        assertEquals(1, utcStart.monthValue)
        assertEquals(1, utcStart.dayOfMonth)
        assertEquals(0, utcStart.hour)
        assertEquals(0, utcStart.minute)
        assertEquals(0, utcStart.second)
        assertEquals(0, utcStart.nano)
    }

    @Test
    fun `start of month for instant`() {
        val instant = nowInstant()

        val start = instant.startOfMonth()
        val utcStart = start.toZonedDateTime(UtcZoneId)

        logger.debug { "utc start of year=$utcStart" }

        assertEquals(1, utcStart.dayOfMonth)
        assertEquals(0, utcStart.hour)
        assertEquals(0, utcStart.minute)
        assertEquals(0, utcStart.second)
        assertEquals(0, utcStart.nano)
    }

    @Test
    fun `start of week for instant`() {
        val instant = nowInstant()

        val start = instant.startOfWeek()
        val utcStart = start.toZonedDateTime(UtcZoneId)

        logger.debug { "utc start of year=$utcStart" }

        assertEquals(DayOfWeek.MONDAY, utcStart.dayOfWeek)
        assertEquals(0, utcStart.hour)
        assertEquals(0, utcStart.minute)
        assertEquals(0, utcStart.second)
        assertEquals(0, utcStart.nano)
    }

    @Test
    fun `start of day for instant`() {
        val instant = nowInstant()

        val start = instant.startOfDay()
        val utcStart = start.toZonedDateTime(UtcZoneId)

        assertEquals(start.toZonedDateTime(UtcZoneId).dayOfMonth, utcStart.dayOfMonth)
        assertEquals(0, utcStart.hour)
        assertEquals(0, utcStart.minute)
        assertEquals(0, utcStart.second)
        assertEquals(0, utcStart.nano)
    }

    @Test
    fun `start of hour for instant`() {
        val instant = nowInstant()

        val start = instant.startOfHour()
        val utcStart = start.toZonedDateTime(UtcZoneId)

        assertEquals(start.toZonedDateTime(UtcZoneId).hour, utcStart.hour)
        assertEquals(0, utcStart.minute)
        assertEquals(0, utcStart.second)
        assertEquals(0, utcStart.nano)
    }

    @Test
    fun `start of minute for instant`() {
        val instant = nowInstant()

        val start = instant.startOfMinute()
        val utcStart = start.toZonedDateTime(UtcZoneId)

        assertEquals(start.toZonedDateTime(UtcZoneId).minute, utcStart.minute)
        assertEquals(0, utcStart.second)
        assertEquals(0, utcStart.nano)
    }

    @Test
    fun `start of second for instant`() {
        val instant = nowInstant()

        val start = instant.startOfSecond()
        val utcStart = start.toZonedDateTime(UtcZoneId)

        assertEquals(start.toZonedDateTime(UtcZoneId).second, utcStart.second)
        assertEquals(0, utcStart.nano)
    }
}