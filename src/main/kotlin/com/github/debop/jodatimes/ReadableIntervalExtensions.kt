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
import org.joda.time.ReadableInterval

fun ReadableInterval.millis(): Long = this.toDurationMillis()

fun ReadableInterval.days(): List<DateTime> {

  tailrec fun recur(acc: MutableList<DateTime>, curr: DateTime, target: DateTime): MutableList<DateTime> {
    if (curr.startOfDay() == target.startOfDay()) {
      return acc
    } else {
      acc += curr
      return recur(acc, curr.nextDay(), target)
    }
  }
  return recur(mutableListOf(), start, end)
}