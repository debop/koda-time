package com.github.debop.javatimes

import mu.KLogging
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


class TemporalIntervalWindowedTest : AbstractJavaTimesTest() {

    companion object : KLogging()

    @Test
    fun `windowed year`() {
        val start = nowZonedDateTime().startOfYear()
        val endExclusive = start + 5.yearPeriod()

        val interval = start..endExclusive

        logger.debug { "year interval=$interval" }

        val windowed = interval.windowedYear(3, 2)
        windowed.forEachIndexed { index, items ->
            logger.debug { "index=$index, items=$items" }
            assertTrue { items.first() in interval }
            assertTrue { items.last() in interval }
        }
        assertEquals(3, windowed.count())

        assertThrows<IllegalArgumentException> {
            interval.windowedYear(-1, 2)
        }
        assertThrows<IllegalArgumentException> {
            interval.windowedYear(1, -2)
        }
    }

    @Test
    fun `windowed month`() {
        val start = nowZonedDateTime().startOfMonth()
        val endExclusive = start + 5.monthPeriod()

        val interval = start..endExclusive

        logger.debug { "month interval=$interval" }

        val windowed = interval.windowedMonth(3, 2)
        windowed.forEachIndexed { index, items ->
            logger.debug { "index=$index, items=$items" }
            assertTrue { items.first() in interval }
            assertTrue { items.last() in interval }
        }
        assertEquals(3, windowed.count())

        assertThrows<IllegalArgumentException> {
            interval.windowedMonth(-1, 2)
        }
        assertThrows<IllegalArgumentException> {
            interval.windowedMonth(1, -2)
        }
    }

    @Test
    fun `windowed week`() {
        val start = nowZonedDateTime().startOfWeek()
        val endExclusive = start + 5.weekPeriod()

        val interval = start..endExclusive

        logger.debug { "week interval=$interval" }

        val windowed = interval.windowedWeek(3, 2)
        windowed.forEachIndexed { index, items ->
            logger.debug { "index=$index, items=$items" }
            assertTrue { items.first() in interval }
            assertTrue { items.last() in interval }
        }
        assertEquals(3, windowed.count())

        assertThrows<IllegalArgumentException> {
            interval.windowedWeek(-1, 2)
        }
        assertThrows<IllegalArgumentException> {
            interval.windowedWeek(1, -2)
        }
    }

    @Test
    fun `windowed days`() {
        val start = nowZonedDateTime().startOfDay()
        val endExclusive = start + 5.days()

        val interval = start..endExclusive

        logger.debug { "day interval=$interval" }

        val windowed = interval.windowedDay(3, 2)
        windowed.forEachIndexed { index, items ->
            logger.debug { "index=$index, items=$items" }
            assertTrue { items.first() in interval }
            assertTrue { items.last() in interval }
        }
        assertEquals(3, windowed.count())

        assertThrows<IllegalArgumentException> {
            interval.windowedDay(-1, 2)
        }
        assertThrows<IllegalArgumentException> {
            interval.windowedDay(1, -2)
        }
    }

    @Test
    fun `windowed hours`() {
        val start = nowZonedDateTime().startOfHour()
        val endExclusive = start + 5.hours()

        val interval = start..endExclusive

        logger.debug { "hour interval=$interval" }

        val windowed = interval.windowedHour(3, 2)
        windowed.forEachIndexed { index, items ->
            logger.debug { "index=$index, items=$items" }
            assertTrue { items.first() in interval }
            assertTrue { items.last() in interval }
        }
        assertEquals(3, windowed.count())

        assertThrows<IllegalArgumentException> {
            interval.windowedHour(-1, 2)
        }
        assertThrows<IllegalArgumentException> {
            interval.windowedHour(1, -2)
        }
    }

    @Test
    fun `windowed minutes`() {
        val start = nowZonedDateTime().startOfMinute()
        val endExclusive = start + 5.minutes()

        val interval = start..endExclusive

        logger.debug { "minute interval=$interval" }

        val windowed = interval.windowedMinute(3, 2)
        windowed.forEachIndexed { index, items ->
            logger.debug { "index=$index, items=$items" }
            assertTrue { items.first() in interval }
            assertTrue { items.last() in interval }
        }
        assertEquals(3, windowed.count())

        assertThrows<IllegalArgumentException> {
            interval.windowedMinute(-1, 2)
        }
        assertThrows<IllegalArgumentException> {
            interval.windowedMinute(1, -2)
        }
    }

    @Test
    fun `windowed seconds`() {
        val start = nowZonedDateTime().startOfSecond()
        val endExclusive = start + 5.seconds()

        val interval = start..endExclusive

        logger.debug { "second interval=$interval" }

        val windowed = interval.windowedSecond(3, 2)
        windowed.forEachIndexed { index, items ->
            logger.debug { "index=$index, items=$items" }
            assertTrue { items.first() in interval }
            assertTrue { items.last() in interval }
        }
        assertEquals(3, windowed.count())

        assertThrows<IllegalArgumentException> {
            interval.windowedSecond(-1, 2)
        }
        assertThrows<IllegalArgumentException> {
            interval.windowedSecond(1, -2)
        }
    }

    @Test
    fun `windowed millis`() {
        val start = nowZonedDateTime().startOfMillis()
        val endExclusive = start + 5.millis()

        val interval = start..endExclusive

        logger.debug { "millis interval=$interval" }

        val windowed = interval.windowedMilli(3, 2)
        windowed.forEachIndexed { index, items ->
            logger.debug { "index=$index, items=$items" }
            assertTrue { items.first() in interval }
            assertTrue { items.last() in interval }
        }
        assertEquals(3, windowed.count())

        assertThrows<IllegalArgumentException> {
            interval.windowedMilli(-1, 2)
        }
        assertThrows<IllegalArgumentException> {
            interval.windowedMilli(1, -2)
        }
    }
}