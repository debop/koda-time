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

import org.joda.time.Period


fun Int.millis(): DurationBuilder = DurationBuilder(Period.millis(this))
fun Int.seconds(): DurationBuilder = DurationBuilder(Period.seconds(this))
fun Int.minutes(): DurationBuilder = DurationBuilder(Period.minutes(this))
fun Int.hours(): DurationBuilder = DurationBuilder(Period.hours(this))
fun Int.days(): Period = Period.days(this)
fun Int.weeks(): Period = Period.weeks(this)
fun Int.months(): Period = Period.months(this)
fun Int.years(): Period = Period.years(this)