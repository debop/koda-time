package com.github.debop.javatimes

import java.time.temporal.Temporal

/**
 *  An iterator over a buildSequence of values of type `java.util.Temporal`.
 */
abstract class TemporalIterator<out T : Temporal> : Iterator<T> {

    final override fun next(): T = nextTemporal()

    /**
     *  Returns the next value in the buildSequence without boxing.
     */
    abstract fun nextTemporal(): T
}