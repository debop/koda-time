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

import org.joda.time.Interval
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class TimeIntervalxTest : AbstractKodaTimesTest() {

  @Test fun rangeTest() {
    val start = now()
    val end = start + 1.days()

    val range: Interval = start .. end

    assertTrue {
      range.days().all { it in start.startOfDay() .. end }
    }
    assertEquals(range.days().last(), end.startOfDay())
  }

  @Test fun rangeStepTest() {
    val start = now()
    val end = start + 1.days() + 1.millis()

    // range contains is start <= x && x < end
    (start .. end step 1.hours().toPeriod()).forEach {
      assertTrue { it in start .. end }
    }

    assertTrue((start .. end).hours().all { it in start .. end })
  }

}