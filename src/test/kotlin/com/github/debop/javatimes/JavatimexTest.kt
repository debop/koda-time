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

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.LocalDate


class JavatimexTest {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(this::class.java)
    }

    @Test fun `constant variables`() {
        assertThat(NowLocalDate).isEqualTo(LocalDate.now())
    }

    @Test fun `time units`() {
        assertThat(1000.nanoseconds).isEqualTo(1.microseconds)
        assertThat(1000.microseconds).isEqualTo(1.millis)
        assertThat(1000.millis).isEqualTo(1.seconds)
        assertThat(60.seconds).isEqualTo(1.minutes)
        assertThat(60.minutes).isEqualTo(1.hours)

        assertThat(7.days).isEqualTo(1.weeks)
    }

    @Test fun `time operator`() {
        assertThat(1000 * 1.nanoseconds).isEqualTo(1.microseconds)
    }

    @Test fun `Number to LocalDateTime`() {
        val now = nowInstant()
        val ms = now.toEpochMilli()
        assertThat(ms.toInstant()).isEqualTo(now)
        assertThat(ms.toLocalDateTime()).isEqualTo(now.toLocalDateTime())
    }
}