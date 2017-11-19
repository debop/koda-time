package com.github.debop.kodatimes

import org.joda.time.DateTime
import org.joda.time.Instant
import org.joda.time.ReadableInstant

/**
 * An iterator over a sequence of values of type `org.joda.time.ReadableInstant`.
 */
abstract class JodaTimeIterator<T : ReadableInstant> : Iterator<T> {

  override final fun next(): T = nextJodaTime()

  /** Returns the next value in the sequence without boxing. */
  public abstract fun nextJodaTime(): T
}

