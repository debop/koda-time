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

import org.joda.time.DateTime
import org.joda.time.Interval
import org.joda.time.Period
import org.joda.time.ReadableDuration
import java.sql.Timestamp

fun DateTime.startOfDay(): DateTime = this.withTimeAtStartOfDay()

operator fun DateTime.minus(millis: Long): DateTime = this.minus(millis)
operator fun DateTime.minus(duration: ReadableDuration): DateTime = this.minus(duration)
operator fun DateTime.minus(period: Period): DateTime = this.minus(period)
operator fun DateTime.minus(builder: DurationBuilder): DateTime = this.minus(builder.self)

operator fun DateTime.plus(millis: Long): DateTime = this.plus(millis)
operator fun DateTime.plus(duration: ReadableDuration): DateTime = this.plus(duration)
operator fun DateTime.plus(period: Period): DateTime = this.plus(period)
operator fun DateTime.plus(builder: DurationBuilder): DateTime = this.plus(builder.self)


fun DateTime.toTimestamp(): Timestamp = Timestamp(this.millis)

infix fun DateTime.min(that: DateTime): DateTime {
  return if (this.compareTo(that) < 0) this else that
}

infix fun DateTime.max(that: DateTime): DateTime {
  return if (this.compareTo(that) > 0) this else that
}

fun DateTime.monthInterval(): Interval {
  val start = this.withDayOfMonth(1).withTimeAtStartOfDay()
  return Interval(start, start + 1.months())
}

fun DateTime.dayInterval(): Interval {
  val start = this.startOfDay()
  return Interval(start, start + 1.days())
}