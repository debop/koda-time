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

import org.joda.time.*

/**
 * DurationBuilder
 * @author debop sunghyouk.bae@gmail.com
 */
class DurationBuilder(val period: Period) {

  operator fun minus(that: DurationBuilder): DurationBuilder = DurationBuilder(this.period.minus(that.period))
  operator fun minus(rp: ReadablePeriod): Period = period.minus(rp)
  operator fun minus(amount: Long): Duration = standardDuration.minus(amount)
  operator fun minus(amount: ReadableDuration): Duration = standardDuration.minus(amount)

  operator fun plus(that: DurationBuilder): DurationBuilder = DurationBuilder(this.period.plus(that.period))
  operator fun plus(rp: ReadablePeriod): Period = period.plus(rp)
  operator fun plus(amount: Long): Duration = standardDuration.plus(amount)
  operator fun plus(amount: ReadableDuration): Duration = standardDuration.plus(amount)

  operator fun times(multiplicand: Long): Duration
      = standardDuration.times(multiplicand)

  operator fun div(divisor: Long): Duration
      = standardDuration.div(divisor)

  operator fun unaryMinus(): Duration = standardDuration.negated()

  fun ago(): DateTime = DateTime.now().minus(period)
  fun later(): DateTime = DateTime.now().plus(period)
  fun from(moment: DateTime): DateTime = moment.plus(period)
  fun before(moment: DateTime): DateTime = moment.minus(period)

  val standardDuration: Duration
    get() = period.toStandardDuration()
  val duration: Duration
    get() = period.toStandardDuration()

  fun toPeriod(): Period = period

  val millis: Long
    get() = standardDuration.millis

  val seconds: Long
    get() = standardDuration.standardSeconds

  val minutes: Long
    get() = standardDuration.standardMinutes

  val hours: Long
    get() = standardDuration.standardHours

  val days: Long
    get() = standardDuration.standardDays

}