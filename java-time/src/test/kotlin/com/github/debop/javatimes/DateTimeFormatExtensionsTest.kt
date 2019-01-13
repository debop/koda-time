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
import org.amshove.kluent.shouldContain
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.TemporalAccessor

/**
 * DateTimeFormatExtensionsTest
 *
 * @author debop (Sunghyouk Bae)
 * @since 19. 1. 13
 */
class DateTimeFormatExtensionsTest {

    companion object : KLogging()

    @Test
    fun `print ISO format with LocalDateTime`() {
        val now = nowLocalDateTime()
        val today = now.startOfDay()

        logger.trace { "now=${now.toIsoString()}" }
        logger.trace { "today=${today.toIsoString()}" }
        logger.trace { "today=${today.toIsoDateString()}" }

        Assertions.assertTrue(today.toIsoString().contains("T00:00:00"))
        today.toIsoString() shouldContain "T00:00:00"

        val parsedToday = LocalDate.parse(today.toIsoDateString())
        parsedToday shouldEqual today.toLocalDate()

        val parsedNow = LocalDateTime.parse(now.toIsoString())

        parsedNow shouldEqual now
    }

    fun nowTemporal(): Array<TemporalAccessor> = arrayOf(nowLocalDate(),
                                                         nowLocalTime(),
                                                         nowLocalDateTime(),
                                                         nowOffsetTime(),
                                                         nowOffsetDateTime(),
                                                         nowZonedDateTime(),
                                                         nowInstant())

    @ParameterizedTest
    @MethodSource("nowTemporal")
    fun `convert TemporalAccessor to string with DateTime Formatter`(temporal: TemporalAccessor) {

        val clazz = temporal.javaClass.simpleName
        logger.trace { "$clazz to IsoString         =${temporal.toIsoString()}" }
        logger.trace { "$clazz to IsoDateString     =${temporal.toIsoDateString()}" }
        logger.trace { "$clazz to IsoTimeString     =${temporal.toIsoTimeString()}" }
        logger.trace { "$clazz to IsoLocalDateString=${temporal.toIsoLocalDateString()}" }
        logger.trace { "$clazz to IsoLocalTimeString=${temporal.toIsoLocalTimeString()}" }
    }
}