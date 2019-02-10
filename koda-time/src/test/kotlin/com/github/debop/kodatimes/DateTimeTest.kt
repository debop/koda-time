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

import mu.KLogging
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldEqual
import org.joda.time.DateTime
import org.joda.time.Interval
import org.joda.time.LocalDate
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class DateTimeTest : AbstractKodaTimesTest() {

    companion object : KLogging() {
        private const val EXPECTED_DATETIME_STR = "2013-03-02T07:08:09.123+0900"
    }

    @Test
    fun dateTimeManupulation() {
        val now = DateTime.now()

        (now + 1.hours()).isAfter(now).shouldBeTrue()
        (now + 1.hours()).isAfterNow.shouldBeTrue()

        (now + 1.hours()).isBefore(now).shouldBeFalse()
        (now + 1.hours()).isBeforeNow.shouldBeFalse()

        (now - 1.hours()).isAfter(now).shouldBeFalse()
        (now - 1.hours()).isAfterNow.shouldBeFalse()

        (now - 1.hours()).isBefore(now).shouldBeTrue()
        (now - 1.hours()).isBeforeNow.shouldBeTrue()
    }

    @Test
    fun `build datetime using builder`() {

        val expected = DateTime.parse(EXPECTED_DATETIME_STR)

        val actual =
            DateTime.parse("2014-01-01T01:01:01.123+0900")
                .withYear(2013)
                .withMonthOfYear(3)
                .withDayOfMonth(3)
                .withDayOfMonth(2)
                .withHourOfDay(7)
                .withMinuteOfHour(8)
                .withSecondOfMinute(9)

        actual shouldEqual expected
    }

    @Test
    fun `date time builder pattern with millis`() {

        val expected = DateTime.parse(EXPECTED_DATETIME_STR)

        val actual =
            DateTime.parse("2014-01-01T01:01:01.123+0900")
                .withYear(2013)
                .withMonthOfYear(3)
                .withDayOfMonth(3)
                .withDayOfMonth(2)
                .withHourOfDay(7)
                .withMinuteOfHour(8)
                .withSecondOfMinute(9)
                .withMillisOfSecond(500)

        actual shouldEqual expected.withMillisOfSecond(500)
    }

    @Test
    fun operatorTest() {
        assertTrue { DateTime.now().nextMonth() < DateTime.now() + 2.months() }

        val now = DateTime.now()
        val range: Interval = now..now.tomorrow()
        range.days().toList().size shouldEqual 2

        val sec: Interval = now..now.nextSecond()
        sec.millis() shouldEqual 1000L
    }

    @Test
    fun sortDateTime() {
        val now = DateTime.now()
        val list = listOf(now, now + 3.seconds(), now + 10.seconds(), now + 1.seconds(), now - 2.seconds())

        val expected = listOf(now - 2.seconds(), now, now + 1.seconds(), now + 3.seconds(), now + 10.seconds())
        val sorted = list.sorted()

        sorted shouldEqual expected
        list.max() shouldEqual now + 10.seconds()
        list.min() shouldEqual now - 2.seconds()
    }

    @Test
    fun sortLocalDate() {
        val today = LocalDate.now()

        val list = listOf(today + 1.days(), today + 3.days(), today + 10.days(), today + 2.days())

        val expected = listOf(today + 1.days(), today + 2.days(), today + 3.days(), today + 10.days())
        val sorted = list.sorted()

        sorted shouldEqual expected
        list.max() shouldEqual today + 10.days()
        list.min() shouldEqual today + 1.days()
    }
}