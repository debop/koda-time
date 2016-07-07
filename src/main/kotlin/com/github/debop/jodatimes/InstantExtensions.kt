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

import org.joda.time.Instant
import org.joda.time.ReadableDuration

operator fun Instant.minus(millis: Long): Instant = this.minus(millis)
operator fun Instant.minus(duration: ReadableDuration): Instant = this.minus(duration)
operator fun Instant.minus(builder: DurationBuilder): Instant = this.minus(builder.self.toStandardDuration())

operator fun Instant.plus(millis: Long): Instant = this.plus(millis)
operator fun Instant.plus(duration: ReadableDuration): Instant = this.plus(duration)
operator fun Instant.plus(builder: DurationBuilder): Instant = this.plus(builder.self.toStandardDuration())