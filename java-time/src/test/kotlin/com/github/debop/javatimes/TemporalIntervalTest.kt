package com.github.debop.javatimes

import mu.KLogging
import org.junit.Test
import java.time.OffsetDateTime
import java.time.Period
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * TemporalIntervalTest
 *
 * @autor debop
 * @since 18. 4. 17
 */
class TemporalIntervalTest : AbstractJavaTimesTest() {

    companion object : KLogging()

    @Test
    fun `constructor by millis`() {
        val interval = temporalIntervalOf(0, 100)

        assertEquals(0, interval.startEpochMillis)
        assertEquals(100, interval.endEpochMillis)
        assertEquals(UtcZoneId, interval.zoneId)

        assertEquals(100, interval.toDurationMillis())
    }

    @Test
    fun `construct invalid range`() {
        val interval = temporalIntervalOf(100, 0)

        assertEquals(0, interval.startEpochMillis)
        assertEquals(100, interval.endEpochMillis)
        assertEquals(UtcZoneId, interval.zoneId)
        assertEquals(100, interval.toDurationMillis())
    }

    @Test
    fun `constructor by ZonedDateTime`() {
        val startDateTime = nowZonedDateTime()
        val end = startDateTime + 5.days()
        val interval = temporalIntervalOf(startDateTime, end)

        assertEquals(startDateTime, interval.startDateTime)
        assertEquals(end, interval.endDateTime)
        assertEquals(SystemZoneId, interval.zoneId)

        logger.debug { "SystemTimeZone=$SystemTimeZone" }

        assertEquals(Period.ofDays(5), interval.toPeriod())
    }

    @Test
    fun `constructor by start with period`() {
        val startDateTime = nowZonedDateTime(UtcZoneId)
        val period = Period.ofDays(5)

        val interval = temporalIntervalOf(startDateTime, period)

        assertEquals(startDateTime, interval.startDateTime)
        assertEquals(startDateTime + period, interval.endDateTime)
        assertEquals(UtcZoneId, interval.zoneId)

        assertEquals(period, interval.toPeriod())
    }

    @Test
    fun `constructor by start with duration`() {
        val startDateTime = nowZonedDateTime(UtcZoneId)
        val duration = 10.hours() + 5.seconds()

        val interval = temporalIntervalOf(startDateTime, duration)

        assertEquals(startDateTime, interval.startDateTime)
        assertEquals(startDateTime + duration, interval.endDateTime)
        assertEquals(UtcZoneId, interval.zoneId)

        assertEquals(duration, interval.toDuration())
    }

    @Test
    fun `constructor by end with period`() {
        val endDateTime = nowZonedDateTime(UtcZoneId)
        val period = Period.ofDays(5)

        val interval = temporalIntervalOf(period, endDateTime)

        assertEquals(endDateTime - period, interval.startDateTime)
        assertEquals(endDateTime, interval.endDateTime)
        assertEquals(UtcZoneId, interval.zoneId)

        assertEquals(period, interval.toPeriod())
    }

    @Test
    fun `constructor by end with duration`() {
        val endDateTime = nowZonedDateTime(UtcZoneId)
        val duration = 10.hours() + 5.seconds()

        val interval = temporalIntervalOf(duration, endDateTime)

        assertEquals(endDateTime - duration, interval.startDateTime)
        assertEquals(endDateTime, interval.endDateTime)
        assertEquals(UtcZoneId, interval.zoneId)

        assertEquals(duration, interval.toDuration())
    }

    @Test
    fun `overlap two intervals`() {
        val interval1 = TemporalInterval(0, 100)
        val interval2 = TemporalInterval(50, 150)
        val interval3 = TemporalInterval(200, 300)

        assertTrue(interval1.overlaps(interval2))
        assertFalse(interval1.overlaps(interval3))

        assertEquals(TemporalInterval(50, 100), interval1.overlap(interval2))
        assertNull(interval1.overlap(interval3))
    }

    @Test
    fun `gap two intervals`() {
        val interval1 = TemporalInterval(0, 100)
        val interval2 = TemporalInterval(50, 150)
        val interval3 = TemporalInterval(200, 300)

        assertNull(interval1.gap(interval2))
        assertEquals(TemporalInterval(100, 200), interval1.gap(interval3))
    }

    @Test
    fun `abuts two intervals`() {
        val interval1 = TemporalInterval(0, 100)
        val interval2 = TemporalInterval(50, 150)
        val interval3 = TemporalInterval(200, 300)
        val interval4 = TemporalInterval(100, 200)
        val interval5 = TemporalInterval(100, 300)

        assertFalse(interval1.abuts(interval2))
        assertFalse(interval1.abuts(interval3))
        assertTrue(interval1.abuts(interval4))
        assertTrue(interval3.abuts(interval4))
        assertFalse(interval4.abuts(interval5))    // 연속하는 것이 아니라 start 가 같다
    }

    @Test
    fun `change with millis`() {
        val interval1 = TemporalInterval(0, 100)
        val interval2 = TemporalInterval(50, 100)
        val interval3 = TemporalInterval(0, 200)
        val interval4 = TemporalInterval(50, 200)

        assertEquals(interval2, interval1.withStartMillis(50))
        assertEquals(interval3, interval1.withEndMillis(200))
        assertEquals(interval4, interval1.withStartMillis(50).withEndMillis(200))
    }

    @Test
    fun `change with duration`() {
        val interval1 = TemporalInterval(0, 100)
        val interval2 = TemporalInterval(50, 100)
        val interval3 = TemporalInterval(0, 200)
        val interval4 = TemporalInterval(50, 200)

        assertEquals(interval2, interval1.withAmountBeforeEnd(50.millis()))
        assertEquals(interval3, interval1.withAmountAfterStart(200.millis()))
        assertEquals(interval4, interval3.withAmountBeforeEnd(150.millis()))
    }

    @Test
    fun `change with period`() {
        val start = nowZonedDateTime()

        val interval1 = TemporalInterval(start, start + 100.days())
        val interval2 = TemporalInterval(start + 50.days(), start + 100.days())
        val interval3 = TemporalInterval(start, start + 200.days())
        val interval4 = TemporalInterval(start + 50.days(), start + 200.days())

        assertEquals(interval2, interval1.withAmountBeforeEnd(50.dayPeriod()))
        assertEquals(interval3, interval1.withAmountAfterStart(200.dayPeriod()))
        assertEquals(interval4, interval3.withAmountBeforeEnd(150.dayPeriod()))
    }

    @Test
    fun `parse temporalInterval`() {
        val start = nowZonedDateTime(UtcZoneId)

        val interval1 = TemporalInterval(start, start + 100.days())

        val parsed = TemporalInterval.parse(interval1.toString())
        assertEquals(interval1, parsed)
    }

    @Test
    fun `parse with offset`() {
        val start = OffsetDateTime.now()
        val end = start + 1.hours()

        val text = "$start~$end"

        val parsed = TemporalInterval.parseWithOffset(text)

        println("text=$text")
        println("parsed=$parsed")
        assertEquals(start, parsed.start.toOffsetDateTime())
        assertEquals(end, parsed.end.toOffsetDateTime())
    }
}