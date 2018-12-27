package com.github.debop.javatimes

import java.util.Date

/**
 * An iterator over a sequence of values of type `java.util.Date`.
 */
abstract class DateIterator<out T : Date> : Iterator<T> {

    final override fun next(): T = nextDate()

    /** Returns the next value in the sequence without boxing. */
    abstract fun nextDate(): T

}
