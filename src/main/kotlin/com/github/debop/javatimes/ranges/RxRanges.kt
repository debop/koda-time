package com.github.debop.javatimes.ranges

import com.github.debop.javatimes.plus
import io.reactivex.Flowable
import kotlinx.coroutines.experimental.DefaultDispatcher
import kotlinx.coroutines.experimental.rx2.rxFlowable
import java.time.Duration
import java.time.temporal.Temporal
import java.util.*
import kotlin.coroutines.experimental.CoroutineContext


@JvmOverloads
fun DateRange.toFlowable(coroutineContext: CoroutineContext = DefaultDispatcher,
                         step: Duration = Duration.ofDays(1)): Flowable<Date> {
  return rxFlowable(coroutineContext) {
    var current = this@toFlowable.start
    while (current <= this@toFlowable.endInclusive) {
      send(current)
      current = current.plus(step.toMillis())
    }
  }
}

fun <T> TemporalRange<T>.toFlowable(coroutineContext: CoroutineContext = DefaultDispatcher,
                                    step: Duration = Duration.ofMillis(1)): Flowable<T>
    where T : Temporal, T : Comparable<T> {

  return rxFlowable(coroutineContext) {
    var current = this@toFlowable.start
    while (current <= this@toFlowable.endInclusive) {
      send(current)
      @Suppress("UNCHECKED_CAST")
      current = current.plus(step) as T
    }
  }
}
