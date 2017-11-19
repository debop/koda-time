package com.github.debop.javatimes

import java.time.Instant
import java.util.*

/**
 * An iterator over a sequence of values of type `java.util.Date`.
 */
abstract class JavaDateIterator<T : Date> : Iterator<T> {

  override final fun next(): T = nextJavaDate()

  /** Returns the next value in the sequence without boxing. */
  abstract fun nextJavaDate(): T

}

/**
 * An iterator over a sequence of values of type `java.util.Instant``.
 */
abstract class JavaInstantIterator : Iterator<Instant> {

  override final fun next(): Instant = nextInstant()

  abstract fun nextInstant(): Instant

}