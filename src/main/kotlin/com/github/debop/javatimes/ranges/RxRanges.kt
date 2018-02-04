package com.github.debop.javatimes.ranges

import io.reactivex.Flowable
import kotlinx.coroutines.experimental.DefaultDispatcher
import kotlinx.coroutines.experimental.rx2.rxFlowable
import java.time.Duration
import java.time.Instant
import java.time.temporal.Temporal
import java.util.*
import kotlin.coroutines.experimental.CoroutineContext

fun <T : Date> DateProgression<T>.toFlowable(coroutineContext: CoroutineContext = DefaultDispatcher): Flowable<T> {
  return rxFlowable(coroutineContext) {
    this@toFlowable.forEach { send(it) }
  }
}

fun InstantProgression.toFlowable(coroutineContext: CoroutineContext = DefaultDispatcher): Flowable<Instant> {
  return rxFlowable(coroutineContext) {
    this@toFlowable.forEach { send(it) }
  }
}

fun <T> TemporalProgression<T>.toFlowable(coroutineContext: CoroutineContext = DefaultDispatcher): Flowable<T>
    where T : Temporal, T : Comparable<T> {
  return rxFlowable(coroutineContext) {
    this@toFlowable.forEach { send(it) }
  }
}

@JvmOverloads
fun DateRange.toFlowable(coroutineContext: CoroutineContext = DefaultDispatcher,
                         step: Duration = Duration.ofDays(1)): Flowable<Date> {
  return DateProgression
      .fromClosedRange(start, endInclusive, step)
      .toFlowable(coroutineContext)
}

@JvmOverloads
fun InstantRange.toFlowable(coroutineContext: CoroutineContext = DefaultDispatcher,
                            step: Duration = Duration.ofMillis(1)): Flowable<Instant> {
  return InstantProgression
      .fromClosedRange(start, endInclusive, step)
      .toFlowable(coroutineContext)
}

@JvmOverloads
fun <T> TemporalRange<T>.toFlowable(coroutineContext: CoroutineContext = DefaultDispatcher,
                                    step: Duration = Duration.ofMillis(1)): Flowable<T>
    where T : Temporal, T : Comparable<T> {

  return TemporalProgression
      .fromClosedRange(start, endInclusive, step)
      .toFlowable(coroutineContext)

}
