package com.github.debop.javatimes.ranges

import com.github.debop.javatimes.TemporalIterator
import com.github.debop.javatimes.isPositive
import java.io.Serializable
import java.time.Duration
import java.time.temporal.Temporal
import java.util.Objects

fun <T> temporalProgressionOf(start: T, endInclusive: T, step: Duration = Duration.ofMillis(1)): TemporalProgression<T>
where T : Temporal, T : Comparable<T> {
    return TemporalProgression.fromClosedRange(start, endInclusive, step)
}

/**
 * A Progression of value of [java.time.temporal.Temporal]
 *
 * @autor debop
 * @since 18. 4. 16
 */
open class TemporalProgression<T> internal constructor(start: T,
                                                       endInclusive: T,
                                                       val step: Duration = Duration.ofMillis(1L))
    : Iterable<T>, Serializable where T : Temporal, T : Comparable<T> {

    init {
        require(!step.isZero) { "step must be non-zero" }
    }

    companion object {
        @JvmStatic
        @JvmOverloads
        fun <T> fromClosedRange(start: T, endInclusive: T, step: Duration = Duration.ofMillis(1))
        : TemporalProgression<T> where T : Temporal, T : Comparable<T> {
            require(!step.isZero) { "step must be non-zero" }
            return TemporalProgression(start, endInclusive, step)
        }
    }

    val first: T = start
    val last: T = getProgressionLastElement(start, endInclusive, step)
    open fun isEmpty(): Boolean = if(step.isPositive) first > last else first < last

    override fun equals(other: Any?): Boolean = when(other) {
        null                       -> false
        !is TemporalProgression<*> -> false
        else                       -> (isEmpty() && other.isEmpty()) ||
                                      (first == other.first && last == other.last && step == other.step)
    }

    override fun hashCode(): Int = if(isEmpty()) -1 else Objects.hash(first, last, step)

    override fun toString(): String = when {
        step.isZero     -> "$first..$last"
        step.isPositive -> "$first..$last step $step"
        else            -> "$first downTo $last step ${step.negated()}"
    }

    @Suppress("UNCHECKED_CAST")
    fun sequence(): Sequence<T> = sequence {
        var current = first
        while(current <= last) {
            yield(current)
            current = current.plus(step) as T
        }
    }

    override fun iterator(): Iterator<T> = TemporalProgressionIterator(first, last, step)

    /**
     * An iterator over a progression of values of type [java.time.temporal.Temporal].
     *
     * @property step the number by which the value is incremented on each step.
     */
    internal class TemporalProgressionIterator<T>(first: T,
                                                  last: T,
                                                  val step: Duration = Duration.ofMillis(1L))
        : TemporalIterator<T>() where T : Temporal, T : Comparable<T> {

        private val _finalElement: T = last
        private var _hasNext: Boolean = if(step.isPositive) first <= last else first >= last
        private var _next: T = if(_hasNext) first else _finalElement

        override fun hasNext(): Boolean = _hasNext

        override fun nextTemporal(): T {
            val value = _next
            if(value == _finalElement) {
                if(!_hasNext)
                    throw NoSuchElementException()
                _hasNext = false
            } else {
                @Suppress("UNCHECKED_CAST")
                _next = _next.plus(step) as T
            }
            return value
        }
    }
}