package com.github.debop.javatimes

import java.time.temporal.Temporal
import java.util.*

/**
 * An iterator over a sequence of values of type `java.util.Date`.
 */
abstract class DateIterator<out T : Date> : Iterator<T> {

    final override fun next(): T = nextDate()

    /** Returns the next value in the sequence without boxing. */
    abstract fun nextDate(): T

}

/**
 * An iterator over a sequence of values of type `java.util.Temporal`.
 */
abstract class TemporalIterator<out T : Temporal> : Iterator<T> {

    final override fun next(): T = nextTemporal()

    /** Returns the next value in the sequence without boxing. */
    abstract fun nextTemporal(): T
}
