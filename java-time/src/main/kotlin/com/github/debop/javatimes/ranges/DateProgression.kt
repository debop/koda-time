package com.github.debop.javatimes.ranges

import com.github.debop.javatimes.DateIterator
import com.github.debop.javatimes.isPositive
import com.github.debop.javatimes.plus
import java.time.Duration
import java.util.*

/**
 * Create [DateProgression] instance
 */
@JvmOverloads
fun <T : Date> dateProgressionOf(start: T, endInclusive: T, step: Duration = Duration.ofMillis(1)): DateProgression<T> =
    DateProgression.fromClosedRange(start, endInclusive, step)

/**
 * A progression of value of `java.util.Date` subclass
 *
 * @property first first value of progression
 * @property last  last value of progression
 * @property step  progression step
 */
open class DateProgression<out T : Date> internal constructor(start: T, endInclusive: T, val step: Duration) : Iterable<T> {
    init {
        require(step != Duration.ZERO) { "step must be non-zero" }
    }

    companion object {
        @JvmStatic
        @JvmOverloads
        fun <T : Date> fromClosedRange(start: T, endInclusive: T, step: Duration = Duration.ofMillis(1L)): DateProgression<T> {
            require(step != Duration.ZERO) { "step must be non-zero" }
            return DateProgression(start, endInclusive, step)
        }
    }

    val first: T = start

    @Suppress("UNCHECKED_CAST")
    val last: T = getProgressionLastElement(start, endInclusive, step) as T

    open fun isEmpty(): Boolean = if(step > Duration.ZERO) first > last else first < last

    override fun iterator(): Iterator<T> = DateProgressionIterator(first, last, step)

    override fun equals(other: Any?): Boolean =
        other is DateProgression<*> &&
        ((isEmpty() && other.isEmpty()) ||
         (first == other.first && last == other.last && step == other.step))

    override fun hashCode(): Int =
        if(isEmpty()) -1
        else Objects.hash(first, last, step)

    override fun toString(): String =
        if(step > Duration.ZERO) "$first..$last step $step"
        else "$first downTo $last step ${step.negated()}"

    /**
     * An iterator over a progression of values of type [java.util.Date]
     *
     * @property step the number by which the value is incremented on each step.
     */
    internal class DateProgressionIterator<T : Date>(first: T, last: T, val step: Duration) : DateIterator<T>() {

        private val stepMillis = step.toMillis()
        private val _finalElement: T = last
        private var _hasNext: Boolean = if(step.isPositive) first <= last else first >= last
        private var _next: T = if(_hasNext) first else _finalElement

        override fun hasNext(): Boolean = _hasNext

        override fun nextDate(): T {
            val value = _next
            if(value == _finalElement) {
                if(!_hasNext)
                    throw NoSuchElementException()
                _hasNext = false
            } else {
                @Suppress("UNCHECKED_CAST")
                _next = _next.plus(stepMillis) as T
            }
            return value
        }
    }
}