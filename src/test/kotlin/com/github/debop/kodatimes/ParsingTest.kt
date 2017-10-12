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

import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.joda.time.LocalTime
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class ParsingTest : AbstractKodaTimesTest() {

  companion object {
    private val EXPECTED_DATE_STR: String = "2016-08-19"
    private val EXPECTED_TIME_STR: String = "17:55:34"
  }

  @Test fun stringToDate() {
    val expected = DateTime(EXPECTED_DATE_STR)

    assertEquals(expected, EXPECTED_DATE_STR.toDateTime())
    assertEquals(expected, EXPECTED_DATE_STR.toDateTime("yyyy-MM-dd"))

    assertNull(EXPECTED_DATE_STR.toDateTime("dd/MM/yyyy"))
    assertNull("".toDateTime())
  }

  @Test fun stringToLocalDate() {
    val expected = LocalDate(EXPECTED_DATE_STR)

    assertEquals(expected, EXPECTED_DATE_STR.toLocalDate())
    assertEquals(expected, EXPECTED_DATE_STR.toLocalDate("yyyy-MM-dd"))

    assertNull(EXPECTED_DATE_STR.toLocalDate("dd/MM/yyyy"))
    assertNull("".toLocalDate())
  }

  @Test fun stringToLocalTime() {
    val expected = LocalTime(EXPECTED_TIME_STR)

    assertEquals(expected, EXPECTED_TIME_STR.toLocalTime())
    assertEquals(expected, EXPECTED_TIME_STR.toLocalTime("HH:mm:ss"))

    // hh : [0~12], HH: [0~24]
    assertNull(EXPECTED_TIME_STR.toLocalTime("hh:mm:ss"))
    assertNotNull(EXPECTED_TIME_STR.toLocalTime("HH:mm:ss"))

    assertNull("".toLocalTime())
    assertNull("".toLocalTime("HH:mm:ss"))
  }
}