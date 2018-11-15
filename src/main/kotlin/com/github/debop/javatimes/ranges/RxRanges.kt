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

package com.github.debop.javatimes.ranges

import io.reactivex.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.rx2.rxFlowable
import java.time.Duration
import java.time.Instant
import java.time.temporal.Temporal
import java.util.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@ExperimentalCoroutinesApi
suspend fun <T : Date> DateProgression<T>.toFlowable(coroutineContext: CoroutineContext = EmptyCoroutineContext): Flowable<T> {
    return coroutineScope {
        rxFlowable(coroutineContext) {
            this@toFlowable.forEach { send(it) }
        }
    }
}

@ExperimentalCoroutinesApi
suspend fun InstantProgression.toFlowable(coroutineContext: CoroutineContext = EmptyCoroutineContext): Flowable<Instant> {
    return coroutineScope {
        rxFlowable(coroutineContext) {
            this@toFlowable.forEach { send(it) }
        }
    }
}

@ExperimentalCoroutinesApi
suspend fun <T> TemporalProgression<T>.toFlowable(coroutineContext: CoroutineContext = EmptyCoroutineContext): Flowable<T>
where T : Temporal, T : Comparable<T> {
    return coroutineScope {
        rxFlowable(coroutineContext) {
            this@toFlowable.forEach { send(it) }
        }
    }
}

@ExperimentalCoroutinesApi
@JvmOverloads
suspend fun DateRange.toFlowable(coroutineContext: CoroutineContext = EmptyCoroutineContext,
                                 step: Duration = Duration.ofDays(1)): Flowable<Date> {
    return DateProgression
        .fromClosedRange(start, endInclusive, step)
        .toFlowable(coroutineContext)
}

@ExperimentalCoroutinesApi
@JvmOverloads
suspend fun InstantRange.toFlowable(coroutineContext: CoroutineContext = EmptyCoroutineContext,
                                    step: Duration = Duration.ofMillis(1)): Flowable<Instant> {
    return InstantProgression
        .fromClosedRange(start, endInclusive, step)
        .toFlowable(coroutineContext)
}

@ExperimentalCoroutinesApi
@JvmOverloads
suspend fun <T> TemporalRange<T>.toFlowable(coroutineContext: CoroutineContext = EmptyCoroutineContext,
                                            step: Duration = Duration.ofMillis(1)): Flowable<T>
where T : Temporal, T : Comparable<T> {

    return TemporalProgression
        .fromClosedRange(start, endInclusive, step)
        .toFlowable(coroutineContext)
}
