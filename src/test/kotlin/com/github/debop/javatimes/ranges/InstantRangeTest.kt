package com.github.debop.javatimes.ranges

import com.github.debop.javatimes.AbstractJavaTimesTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset


class InstantRangeTest : AbstractJavaTimesTest() {

  @Test fun `simple creation`() {

    val start = Instant.ofEpochSecond(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))

    val endInclusive = start + Duration.ofDays(5)
    val range = InstantRange(start, endInclusive)

    assertEquals(start, range.start)
    assertEquals(endInclusive, range.endInclusive)

    assertEquals(start, range.first)
    assertEquals(endInclusive, range.last)

    assertEquals("$start..$endInclusive", range.toString())
  }

  @Test fun `empty range`() {

    val start = Instant.ofEpochSecond(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
    val endInclusive = start - Duration.ofDays(1)

    val range = InstantRange(start, endInclusive)
    assertTrue { range.isEmpty() }

    assertEquals(InstantRange.EMPTY, range)
  }

}