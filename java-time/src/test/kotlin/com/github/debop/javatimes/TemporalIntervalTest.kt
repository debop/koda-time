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
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeNull
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldEqualTo
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime
import java.time.Period

class TemporalIntervalTest : AbstractJavaTimesTest() {

    companion object : KLogging()

    @Test
    fun `constructor by millis`() {
        val interval = temporalIntervalOf(0, 100)

        interval.startEpochMillis shouldEqualTo 0
        interval.endEpochMillis shouldEqualTo 100
        interval.zoneId shouldEqual UtcZoneId
        interval.toDurationMillis() shouldEqualTo 100
    }

    @Test
    fun `construct invalid range`() {
        val interval = temporalIntervalOf(100, 0)

        interval.startEpochMillis shouldEqualTo 0
        interval.endEpochMillis shouldEqualTo 100
        interval.zoneId shouldEqual UtcZoneId
        interval.toDurationMillis() shouldEqualTo 100
    }

    @Test
    fun `constructor by ZonedDateTime`() {
        val start = nowZonedDateTime()
        val end = start + 5.days()
        val interval = temporalIntervalOf(start, end)

        with(interval) {
            startDateTime shouldEqual start
            endDateTime shouldEqual end
            zoneId shouldEqual SystemZoneId
        }
        logger.debug { "SystemTimeZone=${SystemTimeZone.id}" }
        interval.toPeriod() shouldEqual Period.ofDays(5)
    }

    @Test
    fun `constructor by start with period`() {
        val start = nowZonedDateTime(UtcZoneId)
        val period = Period.ofDays(5)
        val interval = temporalIntervalOf(start, period)

        with(interval) {
            startDateTime shouldEqual start
            endDateTime shouldEqual start + period
            zoneId shouldEqual UtcZoneId
            toPeriod() shouldEqual period
        }
    }

    @Test
    fun `constructor by start with duration`() {
        val start = nowZonedDateTime(UtcZoneId)
        val duration = 10.hours() + 5.seconds()
        val interval = temporalIntervalOf(start, duration)

        with(interval) {
            startDateTime shouldEqual start
            endDateTime shouldEqual start + duration
            zoneId shouldEqual UtcZoneId
            toDuration() shouldEqual duration
        }
    }

    @Test
    fun `constructor by end with period`() {
        val end = nowZonedDateTime(UtcZoneId)
        val period = Period.ofDays(5)
        val interval = temporalIntervalOf(period, end)

        with(interval) {
            startDateTime shouldEqual end - period
            endDateTime shouldEqual end
            zoneId shouldEqual UtcZoneId
            toPeriod() shouldEqual period
        }
    }

    @Test
    fun `constructor by end with duration`() {
        val end = nowZonedDateTime(UtcZoneId)
        val duration = 10.hours() + 5.seconds()

        val interval = temporalIntervalOf(duration, end)

        with(interval) {
            startDateTime shouldEqual end - duration
            endDateTime shouldEqual end
            zoneId shouldEqual UtcZoneId
            toDuration() shouldEqual duration
        }
    }

    @Test
    fun `overlap two intervals`() {
        val interval1 = TemporalInterval(0, 100)
        val interval2 = TemporalInterval(50, 150)
        val interval3 = TemporalInterval(200, 300)

        interval1.overlaps(interval2).shouldBeTrue()
        interval1.overlap(interval2) shouldEqual TemporalInterval(50, 100)

        interval1.overlaps(interval3).shouldBeFalse()
        interval1.overlap(interval3).shouldBeNull()
    }

    @Test
    fun `gap two intervals`() {
        val interval1 = TemporalInterval(0, 100)
        val interval2 = TemporalInterval(50, 150)
        val interval3 = TemporalInterval(200, 300)

        interval1.gap(interval2).shouldBeNull()
        interval1.gap(interval3) shouldEqual TemporalInterval(100, 200)
    }

    @Test
    fun `abuts two intervals`() {
        val interval1 = TemporalInterval(0, 100)
        val interval2 = TemporalInterval(50, 150)
        val interval3 = TemporalInterval(200, 300)
        val interval4 = TemporalInterval(100, 200)
        val interval5 = TemporalInterval(100, 300)

        interval1.abuts(interval2).shouldBeFalse()
        interval1.abuts(interval3).shouldBeFalse()

        interval1.abuts(interval4).shouldBeTrue()
        interval3.abuts(interval4).shouldBeTrue()

        interval4.abuts(interval5).shouldBeFalse()    // 연속하는 것이 아니라 start 가 같다
    }

    @Test
    fun `change with millis`() {
        val interval1 = TemporalInterval(0, 100)
        val interval2 = TemporalInterval(50, 100)
        val interval3 = TemporalInterval(0, 200)
        val interval4 = TemporalInterval(50, 200)

        interval1.withStartMillis(50) shouldEqual interval2
        interval1.withEndMillis(200) shouldEqual interval3
        interval1.withStartMillis(50).withEndMillis(200) shouldEqual interval4
    }

    @Test
    fun `change with duration`() {
        val interval1 = TemporalInterval(0, 100)
        val interval2 = TemporalInterval(50, 100)
        val interval3 = TemporalInterval(0, 200)
        val interval4 = TemporalInterval(50, 200)

        interval1.withAmountBeforeEnd(50.millis()) shouldEqual interval2
        interval1.withAmountAfterStart(200.millis()) shouldEqual interval3
        interval3.withAmountBeforeEnd(150.millis()) shouldEqual interval4
    }

    @Test
    fun `change with period`() {
        val start = nowZonedDateTime()

        val interval1 = TemporalInterval(start, start + 100.days())
        val interval2 = TemporalInterval(start + 50.days(), start + 100.days())
        val interval3 = TemporalInterval(start, start + 200.days())
        val interval4 = TemporalInterval(start + 50.days(), start + 200.days())

        interval1.withAmountBeforeEnd(50.dayPeriod()) shouldEqual interval2
        interval1.withAmountAfterStart(200.dayPeriod()) shouldEqual interval3
        interval3.withAmountBeforeEnd(150.dayPeriod()) shouldEqual interval4
    }

    @Test
    fun `parse temporalInterval`() {
        val start = nowZonedDateTime(UtcZoneId)

        val interval1 = TemporalInterval(start, start + 100.days())

        TemporalInterval.parse(interval1.toString()) shouldEqual interval1
    }

    @Test
    fun `parse with offset`() {
        val start = OffsetDateTime.now()
        val end = start + 1.hours()

        val text = "$start~$end"

        val parsed = TemporalInterval.parseWithOffset(text)

        logger.trace { "text=$text, parsed=$parsed" }

        parsed.start.toOffsetDateTime() shouldEqual start
        parsed.end.toOffsetDateTime() shouldEqual end
    }
}