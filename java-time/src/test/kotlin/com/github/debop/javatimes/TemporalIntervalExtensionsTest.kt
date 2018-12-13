package com.github.debop.javatimes

import kotlinx.coroutines.runBlocking
import mu.KLogging
import org.junit.Test
import kotlin.test.assertEquals

/**
 * TemporalIntervalExtensionsTest
 *
 * @autor debop
 * @since 18. 4. 17
 */
class TemporalIntervalExtensionsTest : AbstractJavaTimesTest() {

    companion object : KLogging()

    @Test
    fun `build year sequence`() {
        val start = nowZonedDateTime()
        val end = start + 42.yearPeriod()

        val interval = temporalIntervalOf(start, end)

        val startYear = start.year
        assertEquals(listOf(startYear, startYear + 1, startYear + 2),
                     interval.years(1).take(3).map {
                         logger.debug { "produce $it" }
                         it.year
                     }.toList())

        assertEquals(listOf(startYear, startYear + 2, startYear + 4),
                     interval.years(2).take(3).map {
                         logger.debug { "produce $it" }
                         it.year
                     }.toList())
    }

    @Test
    fun `build month sequece`() {
        val start = nowZonedDateTime()
        val end = start + 42.monthPeriod()

        val interval = temporalIntervalOf(start, end)

        val startMonth = start.month
        assertEquals(listOf(startMonth, startMonth + 1, startMonth + 2),
                     interval.months(1).take(3).map {
                         logger.debug { "produce $it" }
                         it.month
                     }.toList())

        assertEquals(listOf(startMonth, startMonth + 2, startMonth + 4),
                     interval.months(2).take(3).map {
                         logger.debug { "produce $it" }
                         it.month
                     }.toList())
    }

    @Test
    fun `build day sequence`() {
        val start = nowZonedDateTime()
        val end = start + 42.days()

        val interval = temporalIntervalOf(start, end)

        val startDay = start.dayOfYear
        logger.debug { "startDay=$startDay" }

        assertEquals(listOf(startDay, startDay + 1, startDay + 2),
                     interval.days(1).take(3).map {
                         logger.debug { "produce $it, dayOfYear=${it.dayOfYear}" }
                         it.dayOfYear
                     }.toList())

        assertEquals(listOf(startDay, startDay + 2, startDay + 4),
                     interval.days(2).take(3).map {
                         logger.debug { "produce $it, dayOfYear=${it.dayOfYear}" }
                         it.dayOfYear
                     }.toList())
    }

    @Test
    fun `build hour sequence`() = runBlocking {
        val start = nowZonedDateTime()
        val end = start + 42.hours()

        val interval = temporalIntervalOf(start, end)

        val startHour = start.hour
        logger.debug { "startHour=$startHour" }

        assertEquals(24, interval.hours(1).take(24).toList().size)
        assertEquals(12, interval.hours(2).take(12).toList().size)
    }

    @Test
    fun `build minutes sequence`() = runBlocking {
        val start = nowZonedDateTime()
        val end = start + 42.minutes()

        val interval = temporalIntervalOf(start, end)

        val startMinute = start.minute
        logger.debug { "startMinute=$startMinute" }

        assertEquals(24, interval.minutes(1).take(24).toList().size)
        assertEquals(12, interval.minutes(2).take(12).toList().size)
    }
}