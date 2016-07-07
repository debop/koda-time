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

import org.joda.time.*

/**
 * DurationBuilder
 * @author debop sunghyouk.bae@gmail.com
 */
class DurationBuilder(val self: Period) {
  operator fun minus(that: DurationBuilder): DurationBuilder = DurationBuilder(this.self.minus(that.self))
  operator fun plus(that: DurationBuilder): DurationBuilder = DurationBuilder(this.self.plus(that.self))

  fun ago(): DateTime = DateTime.now().minus(self)
  fun later(): DateTime = DateTime.now().plus(self)
  fun from(moment: DateTime): DateTime = moment.plus(self)
  fun before(moment: DateTime): DateTime = moment.minus(self)

  fun standardDuration(): Duration = self.toStandardDuration()

  fun toDuration(): Duration = self.toStandardDuration()
  fun toPeriod(): Period = self

  operator fun minus(period: ReadablePeriod): Period = self.minus(period)
  operator fun plus(period: ReadablePeriod): Period = self.plus(period)

  fun millis(): Long = standardDuration().millis
  fun seconds(): Long = standardDuration().standardSeconds

  operator fun minus(amount: Long): Duration = standardDuration().minus(amount)
  operator fun minus(amount: ReadableDuration): Duration = standardDuration().minus(amount)

  operator fun plus(amount: Long): Duration = standardDuration().plus(amount)
  operator fun plus(amount: ReadableDuration): Duration = standardDuration().plus(amount)
}