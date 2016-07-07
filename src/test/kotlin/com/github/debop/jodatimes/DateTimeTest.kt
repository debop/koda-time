/*
 * Copyright (c) 2016. Sunghyouk Bae <sunghyouk.bae@gmail.com>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.debop.jodatimes

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.assertj.jodatime.api.Assertions.assertThat
import org.joda.time.DateTime
import org.joda.time.Interval
import org.joda.time.LocalDate
import org.junit.Test

class DateTimeTest : AbstractJodaTimesTest() {

  @Test fun dateTimeManupulation() {
    val now = DateTime.now()

    Assertions.assertThat(now).isEqualTo(now)
    assertThat(now == now).isTrue()
    assertThat((now + 1.hours()).isAfter(now)).isTrue()
  }

  @Test fun dateTimeSetter() {
    val actual =
        DateTime.parse("2014-01-01T01:01:01.123+0900")
            .withYear(2013)
            .withMonthOfYear(3)
            .withDayOfMonth(3)
            .withDayOfMonth(2)
            .withHourOfDay(7)
            .withMinuteOfHour(8)
            .withSecondOfMinute(9)

    val expected = DateTime.parse("2013-03-02T07:08:09.123+0900")

    assertThat(actual).isEqualTo(expected)
  }

  @Test fun operatorTest() {
    assertThat(DateTime.now().nextMonth() < DateTime.now() + 2.months()).isTrue()

    val now = DateTime.now()
    val range: Interval = now..now.tomorrow()
    println("range=$range")

    val sec: Interval = now..now.nextSecond()
    assertThat(sec.millis()).isEqualTo(1000L)
  }

  @Test fun sortDateTime() {
    val now = DateTime.now()
    val list = listOf(now, now + 3.seconds(), now + 10.seconds(), now + 1.seconds(), now - 2.seconds())

    val expected = listOf(now - 2.seconds(), now, now + 1.seconds(), now + 3.seconds(), now + 10.seconds())
    val sorted = list.sorted()
    assertThat(sorted).isEqualTo(expected)

    Assertions.assertThat(list.max()).isEqualTo(now + 10.seconds())
  }

  @Test fun sortLocalDate() {
    val today = LocalDate.now()

    val list = listOf(today + 1.days(), today + 3.days(), today + 10.days(), today + 2.days())

    val expected = listOf(today + 1.days(), today + 2.days(), today + 3.days(), today + 10.days())
    val sorted = list.sorted()
    assertThat(sorted).isEqualTo(expected)
    assertThat(list.max()).isEqualTo(today + 10.days())
  }

}