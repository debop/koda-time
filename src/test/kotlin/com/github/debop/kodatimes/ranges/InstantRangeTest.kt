package com.github.debop.kodatimes.ranges

import com.github.debop.kodatimes.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class InstantRangeTest : AbstractKodaTimesTest() {

  @Test fun `simple creation`() {

    val start = today().toInstant()
    val endInclusive = start + 5.days()
    val range = InstantRange(start, endInclusive)

    assertEquals(start, range.start)
    assertEquals(endInclusive, range.endInclusive)

    assertEquals(start, range.first)
    assertEquals(endInclusive, range.last)
  }

  @Test fun `empty range`() {

    val start = today().toInstant()
    val endInclusive = start - 1.days()

    val range = InstantRange(start, endInclusive)
    assertTrue { range.isEmpty() }

    assertEquals(InstantRange.EMPTY, range)
  }

}