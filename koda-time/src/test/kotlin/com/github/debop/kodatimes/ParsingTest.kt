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
import org.amshove.kluent.shouldBeNull
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldNotBeNull
import org.amshove.kluent.shouldNotEqual
import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.joda.time.LocalTime
import org.junit.jupiter.api.Test

class ParsingTest : AbstractKodaTimesTest() {

    companion object : KLogging() {
        private const val EXPECTED_DATE_STR: String = "2016-08-19"
        private const val EXPECTED_TIME_STR: String = "17:55:34"
    }

    @Test
    fun `string to DateTime`() {
        val expected = DateTime(EXPECTED_DATE_STR)

        EXPECTED_DATE_STR.toDateTime() shouldEqual expected
        EXPECTED_DATE_STR.toDateTime("yyyy-MM-dd") shouldEqual expected

        EXPECTED_DATE_STR.toDateTime("dd/MM/yyyy").shouldBeNull()
        "".toDateTime().shouldBeNull()
    }

    @Test
    fun `string to LocalDate`() {
        val expected = LocalDate(EXPECTED_DATE_STR)

        EXPECTED_DATE_STR.toLocalDate() shouldEqual expected
        EXPECTED_DATE_STR.toLocalDate("yyyy-MM-dd") shouldEqual expected
        EXPECTED_DATE_STR.toLocalDate("YYYY-MM-dd") shouldEqual expected

        EXPECTED_DATE_STR.toLocalDate("YYYY-MM-DD") shouldNotEqual expected // D means `day of year`
        EXPECTED_DATE_STR.toLocalDate("dd/MM/yyyy").shouldBeNull()
        "".toLocalDate().shouldBeNull()
    }

    @Test
    fun stringToLocalTime() {
        val expected = LocalTime(EXPECTED_TIME_STR)

        EXPECTED_TIME_STR.toLocalTime() shouldEqual expected
        EXPECTED_TIME_STR.toLocalTime("HH:mm:ss") shouldEqual expected
        EXPECTED_TIME_STR.toLocalTime("HH:mm:SS") shouldNotEqual expected  // S means `fraction of second`

        // hh : [0~12], HH: [0~24]
        EXPECTED_TIME_STR.toLocalTime("hh:mm:ss").shouldBeNull()
        EXPECTED_TIME_STR.toLocalTime("HH:MM:ss").shouldBeNull()
        EXPECTED_TIME_STR.toLocalTime("HH:mm:SS").shouldNotBeNull()

        "".toLocalTime().shouldBeNull()
        "".toLocalTime("HH:mm:ss").shouldBeNull()
    }
}