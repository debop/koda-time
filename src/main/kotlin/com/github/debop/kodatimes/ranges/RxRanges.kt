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

package com.github.debop.kodatimes.ranges

import io.reactivex.Flowable
import kotlinx.coroutines.experimental.DefaultDispatcher
import kotlinx.coroutines.experimental.rx2.rxFlowable
import org.joda.time.*
import kotlin.coroutines.experimental.CoroutineContext


fun DateTimeProgression.toFlowable(coroutineContext: CoroutineContext = DefaultDispatcher): Flowable<DateTime> {
    return rxFlowable(coroutineContext) {
        this@toFlowable.forEach { send(it) }
    }
}


fun InstantProgression.toFlowable(coroutineContext: CoroutineContext = DefaultDispatcher): Flowable<Instant> {
    return rxFlowable(coroutineContext) {
        this@toFlowable.forEach { send(it) }
    }
}

fun DateTimeRange.toFlowable(coroutineContext: CoroutineContext = DefaultDispatcher,
                             step: Duration = Duration.millis(1L)): Flowable<DateTime> {

    return DateTimeProgression
        .fromClosedRange(start, endInclusive, step)
        .toFlowable(coroutineContext)

}

fun InstantRange.toFlowable(coroutineContext: CoroutineContext = DefaultDispatcher,
                            step: Duration = Duration.millis(1L)): Flowable<Instant> {
    return InstantProgression
        .fromClosedRange(start, endInclusive, step)
        .toFlowable(coroutineContext)
}