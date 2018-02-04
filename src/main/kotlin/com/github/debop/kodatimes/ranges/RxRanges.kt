package com.github.debop.kodatimes.ranges

import io.reactivex.Flowable
import kotlinx.coroutines.experimental.DefaultDispatcher
import kotlinx.coroutines.experimental.rx2.rxFlowable
import org.joda.time.DateTime
import org.joda.time.Duration
import org.joda.time.Instant
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