package com.github.debop.javatimes.ranges.reactive

import com.github.debop.javatimes.ranges.DateProgression
import com.github.debop.javatimes.ranges.TemporalProgression
import io.reactivex.Flowable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.rx2.rxFlowable
import java.time.temporal.Temporal
import java.util.*
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
suspend fun <T : Date> DateProgression<T>.toFlowable(context: CoroutineContext = Dispatchers.Default): Flowable<T> =
    GlobalScope.rxFlowable(context) {
        this@toFlowable.forEach { send(it) }
    }

@ExperimentalCoroutinesApi
suspend fun <T> TemporalProgression<T>.toFlowable(context: CoroutineContext = Dispatchers.Default): Flowable<T>
where T : Temporal, T : Comparable<T> {
    return GlobalScope.rxFlowable(context) {
        this@toFlowable.forEach { send(it) }
    }
}

