package com.github.debop.javatimes

import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldEqualTo
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

        with(utcStart) {
            monthValue shouldEqualTo 1
            dayOfMonth shouldEqualTo 1
            hour shouldEqualTo 0
            minute shouldEqualTo 0
            second shouldEqualTo 0
            nano shouldEqualTo 0
        }
    }

    @Test
    fun `start of month for instant`() {
        val instant = nowInstant()

        val start = instant.startOfMonth()
        val utcStart = start.toZonedDateTime(UtcZoneId)

        logger.debug { "utc start of year=$utcStart" }

        with(utcStart) {
            dayOfMonth shouldEqualTo 1
            hour shouldEqualTo 0
            minute shouldEqualTo 0
            second shouldEqualTo 0
            nano shouldEqualTo 0
        }
    }

    @Test
    fun `start of week for instant`() {
        val instant = nowInstant()

        val start = instant.startOfWeek()
        val utcStart = start.toZonedDateTime(UtcZoneId)

        logger.debug { "utc start of year=$utcStart" }

        with(utcStart) {
            dayOfWeek shouldEqual DayOfWeek.MONDAY
            hour shouldEqualTo 0
            minute shouldEqualTo 0
            second shouldEqualTo 0
            nano shouldEqualTo 0
        }
    }

    @Test
    fun `start of day for instant`() {
        val instant = nowInstant()

        val start = instant.startOfDay()
        val utcStart = start.toZonedDateTime(UtcZoneId)

        with(utcStart) {
            dayOfMonth shouldEqual start.toZonedDateTime(UtcZoneId).dayOfMonth
            hour shouldEqualTo 0
            minute shouldEqualTo 0
            second shouldEqualTo 0
            nano shouldEqualTo 0
        }
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

        with(utcStart) {
            hour shouldEqualTo start.toZonedDateTime(UtcZoneId).hour
            minute shouldEqualTo 0
            second shouldEqualTo 0
            nano shouldEqualTo 0
        }
    }

    @Test
    fun `start of minute for instant`() {
        val instant = nowInstant()

        val start = instant.startOfMinute()
        val utcStart = start.toZonedDateTime(UtcZoneId)

        with(utcStart) {
            minute shouldEqualTo start.toZonedDateTime(UtcZoneId).minute
            second shouldEqualTo 0
            nano shouldEqualTo 0
        }
    }

    @Test
    fun `start of second for instant`() {
        val instant = nowInstant()

        val start = instant.startOfSecond()
        val utcStart = start.toZonedDateTime(UtcZoneId)

        with(utcStart) {
            second shouldEqualTo start.toZonedDateTime(UtcZoneId).second
            nano shouldEqualTo 0
        }
    }
}