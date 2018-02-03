package com.github.debop.javatimes.ranges

import com.github.debop.javatimes.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.time.Duration
import java.time.Instant

class InstantProgressionTest : AbstractJavaTimesTest() {   

  @Test fun `create simple`() {
    val start = Instant.now()
    val endInclusive = start + Duration.ofDays(1)

    val progression = InstantProgression.fromClosedRange(start, endInclusive, 1.hours)

    assertEquals(start, progression.first)
    assertEquals(endInclusive, progression.last)
    assertEquals(1.hours, progression.step)

    val list = progression.toList()
    assertEquals(25, list.count())
  }

  @Test fun `zero step`() {
    val instant = Instant.now()

    assertThrows(IllegalArgumentException::class.java) {
      InstantProgression.fromClosedRange(instant, instant, Duration.ZERO)
    }
  }

  @Test fun `step greater than range`() {
    val start = Instant.now()
    val endInclusive = start + Duration.ofDays(1).toMillis()

    val progression = InstantProgression.fromClosedRange(start, endInclusive, Duration.ofDays(7))

    assertEquals(start, progression.first)
    // last is not endInclusive, step over endInclusive, so last is equal to start
    assertEquals(start, progression.last)
    assertEquals(Duration.ofDays(7), progression.step)

    val list = progression.toList()
    assertEquals(1, list.count())
  }

  @Test fun `stepping not exact endInclusive`() {
    val start = Instant.now().with(2017, 10, 14)
    val endInclusive = start + Duration.ofDays(1).toMillis()

    val progression = InstantProgression.fromClosedRange(start, endInclusive, Duration.ofHours(5))

    assertEquals(start, progression.first)
    assertEquals(start + Duration.ofHours(20).toMillis(), progression.last)
    assertNotEquals(endInclusive, progression.last)

    val list: List<Instant> = progression.toList()
    assertEquals(5, list.count())

    logger.debug { "$list" }
    assertEquals(listOf(0, 5, 10, 15, 20), list.map { it.toLocalDateTime().hour })
  }

  @Test fun `downTo progression`() {
    val start = Instant.now()
    val endInclusive = start - Duration.ofDays(5).toMillis()
    val step = Duration.ofDays(-1L)

    val progression = InstantProgression.fromClosedRange(start, endInclusive, step)

    assertEquals(start, progression.first)
    assertEquals(endInclusive, progression.last)

    assertEquals("$start downTo $endInclusive step ${step.negated()}", progression.toString())

    val list = progression.toList()
    assertEquals(6, list.count())
    assertEquals(start, list.first())
    assertEquals(endInclusive, list.last())
  }
}