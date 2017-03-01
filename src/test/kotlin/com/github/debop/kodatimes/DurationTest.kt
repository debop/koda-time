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
 *
 */

package com.github.debop.kodatimes

import org.assertj.core.api.Assertions.assertThat
import org.joda.time.DateTime
import org.joda.time.Duration
import org.junit.Test

class DurationTest : AbstractKodaTimesTest() {

  @Test fun makeDuration() {
    val now = DateTime.now()
    val duration: Duration = (now..now + 1.days()).toDuration()
    assertThat(duration.standardDays).isEqualTo(1L)

    assertThat((now..now + 7.days()).toDuration().standardDays).isEqualTo(7L)
    assertThat((now..now + 40.days()).toDuration().standardDays).isEqualTo(40L)
    assertThat((now..now + 500.days()).toDuration().standardDays).isEqualTo(500L)
  }

  @Test fun sortDuration() {
    val list = listOf(1.seconds(), 5.seconds(), 2.seconds(), 4.seconds()).map { it.duration }
    val expected = listOf(1.seconds(), 2.seconds(), 4.seconds(), 5.seconds()).map { it.duration }

    assertThat(list.sorted()).isEqualTo(expected)
    assertThat(list.max()).isEqualTo(5.seconds().duration)
  }
}