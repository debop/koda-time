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

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.LocalDate


class JavatimexTest {

  companion object {
    private val log: Logger = LoggerFactory.getLogger(JavatimexTest::class.java)
  }

  @Test fun `constant variables`() {
    assertEquals(LocalDate.now(), NowLocalDate)
  }

  @Test fun `time units`() {
    assertEquals(1.microseconds, 1000.nanoseconds)
    assertEquals(1.millis, 1000.microseconds)
    assertEquals(1.seconds, 1000.millis)
    assertEquals(1.minutes, 60.seconds)
    assertEquals(1.hours, 60.minutes)

    assertEquals(1.weeks, 7.days)
  }

  @Test fun `time operator`() {
    assertEquals(1.microseconds, 1000 * 1.nanoseconds)
  }

  @Test fun `Number to LocalDateTime`() {
    val now = nowInstant()
    val ms = now.toEpochMilli()
    assertEquals(now, ms.toInstant())
    assertEquals(now.toLocalDateTime(), ms.toLocalDateTime())
  }
}