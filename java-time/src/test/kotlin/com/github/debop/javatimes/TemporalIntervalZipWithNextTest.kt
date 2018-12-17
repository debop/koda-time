package com.github.debop.javatimes

import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

/**
 * TemporalIntervalZipWithNextTest
 *
 * @author debop
 */
class TemporalIntervalZipWithNextTest : AbstractJavaTimesTest() {

    @Test
    fun `zip with next year`() {

        val start = nowZonedDateTime().startOfYear()
        val endExclusive = start + 5.yearPeriod()

        val interval = start..endExclusive
        logger.debug { "year interval=$interval" }

        interval.zipWithNextYear().forEach { (current, next) ->
            logger.trace { "current=$current, next=$next" }
            assertTrue { current in interval }
            assertTrue { next in interval }
            assertTrue { current < next }
        }
    }

    @Test
    fun `zip with next month`() {

        val start = nowZonedDateTime().startOfMonth()
        val endExclusive = start + 5.monthPeriod()

        val interval = start..endExclusive
        logger.debug { "month interval=$interval" }

        interval.zipWithNextMonth().forEach { (current, next) ->
            logger.trace { "current=$current, next=$next" }
            assertTrue { current in interval }
            assertTrue { next in interval }
            assertTrue { current < next }
        }
    }

    @Test
    fun `zip with next week`() {

        val start = nowZonedDateTime().startOfWeek()
        val endExclusive = start + 5.weekPeriod()

        val interval = start..endExclusive
        logger.debug { "week interval=$interval" }

        interval.zipWithNextWeek().forEach { (current, next) ->
            logger.trace { "current=$current, next=$next" }
            assertTrue { current in interval }
            assertTrue { next in interval }
            assertTrue { current < next }
        }
    }

    @Test
    fun `zip with next day`() {

        val start = nowZonedDateTime().startOfDay()
        val endExclusive = start + 5.dayPeriod()

        val interval = start..endExclusive
        logger.debug { "day interval=$interval" }

        interval.zipWithNextDay().forEach { (current, next) ->
            logger.trace { "current=$current, next=$next" }
            assertTrue { current in interval }
            assertTrue { next in interval }
            assertTrue { current < next }
        }
    }

    @Test
    fun `zip with next hour`() {

        val start = nowZonedDateTime().startOfHour()
        val endExclusive = start + 5.hours()

        val interval = start..endExclusive
        logger.debug { "hour interval=$interval" }

        interval.zipWithNextHour().forEach { (current, next) ->
            logger.trace { "current=$current, next=$next" }
            assertTrue { current in interval }
            assertTrue { next in interval }
            assertTrue { current < next }
        }
    }

    @Test
    fun `zip with next minute`() {

        val start = nowZonedDateTime().startOfMinute()
        val endExclusive = start + 5.minutes()

        val interval = start..endExclusive
        logger.debug { "minute interval=$interval" }

        interval.zipWithNextMinute().forEach { (current, next) ->
            logger.trace { "current=$current, next=$next" }
            assertTrue { current in interval }
            assertTrue { next in interval }
            assertTrue { current < next }
        }
    }

    @Test
    fun `zip with next second`() {

        val start = nowZonedDateTime().startOfSecond()
        val endExclusive = start + 5.seconds()

        val interval = start..endExclusive
        logger.debug { "second interval=$interval" }

        interval.zipWithNextSecond().forEach { (current, next) ->
            logger.trace { "current=$current, next=$next" }
            assertTrue { current in interval }
            assertTrue { next in interval }
            assertTrue { current < next }
        }
    }

    @Test
    fun `zip with next milli`() {

        val start = nowZonedDateTime().startOfMillis()
        val endExclusive = start + 5.millis()

        val interval = start..endExclusive
        logger.debug { "millis interval=$interval" }

        interval.zipWithNextMilli().forEach { (current, next) ->
            logger.trace { "current=$current, next=$next" }
            assertTrue { current in interval }
            assertTrue { next in interval }
            assertTrue { current < next }
        }
    }
}