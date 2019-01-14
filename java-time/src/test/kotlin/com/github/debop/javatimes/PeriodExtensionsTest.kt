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

import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqualTo
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.math.absoluteValue

/**
 * PeriodExtensionsTest
 *
 * @author debop (Sunghyouk Bae)
 * @since 19. 1. 14
 */
class PeriodExtensionsTest {

    @ParameterizedTest
    @ValueSource(ints = [-42, -1, 0, 1, 42])
    fun `year period generate sequence`(year: Int) = runBlocking<Unit> {
        year.yearPeriod().yearSequence().count() shouldEqualTo year.absoluteValue
    }

    @ParameterizedTest
    @ValueSource(ints = [-42, -1, 0, 1, 42])
    fun `month period generate sequence`(month: Int) = runBlocking<Unit> {
        month.monthPeriod().monthSequence().count() shouldEqualTo month.absoluteValue
    }

    @ParameterizedTest
    @ValueSource(ints = [-42, -1, 0, 1, 42])
    fun `day period generate sequence`(day: Int) = runBlocking<Unit> {
        day.dayPeriod().daySequence().count() shouldEqualTo day.absoluteValue
    }
}