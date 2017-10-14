package com.github.debop.kodatimes

import org.joda.time.Duration
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class InstantProgressionTest : AbstractKodaTimesTest() {

  @Test fun `create simple`() {
    val start = today().toInstant()
    val endInclusive = start + 1.days()

    val progression = InstantProgression.fromClosedRange(start, endInclusive, standardHours(1))

    assertEquals(start, progression.first)
    assertEquals(endInclusive, progression.last)
    assertEquals(standardHours(1), progression.step)

    val list = progression.toList()
    assertEquals(25, list.count())
  }

  @Test fun `zero step`() {
    val instant = now().toInstant()

    assertThrows(IllegalArgumentException::class.java) {
      InstantProgression.fromClosedRange(instant, instant, Duration(0))
    }
  }

  @Test fun `step greater than range`() {
    val start = today().toInstant()
    val endInclusive = start + 1.days()

    val progression = InstantProgression.fromClosedRange(start, endInclusive, standardDays(7))

    assertEquals(start, progression.first)
    // last is not endInclusive, step over endInclusive, so last is equal to start
    assertEquals(start, progression.last)
    assertEquals(7.dayDuration(), progression.step)

    val list = progression.toList()
    assertEquals(1, list.count())
  }

  @Test fun `stepping not exact endInclusive`() {
    val start = today().toInstant()
    val endInclusive = start + 1.days()

    val progression = InstantProgression.fromClosedRange(start, endInclusive, 5.hourDuration())

    assertEquals(start, progression.first)
    assertEquals(start + 20.hours(), progression.last)
    assertNotEquals(endInclusive, progression.last)

    println("progression=$progression")

    val list = progression.toList()
    assertEquals(5, list.count())

    // Instant 는 GMT 기준이라 Local time과는 시간차가 있습니다. 그래서 해당 Timezone 으로 변경해야 합니다.
    assertEquals(listOf(0, 5, 10, 15, 20), list.map { it.toDateTime().hourOfDay })
  }

  @Test fun `downTo progression`() {
    val start = today().toInstant()
    val endInclusive = start - 5.days()
    val step = dayDurationOf(-1)

    val progression = InstantProgression.fromClosedRange(start, endInclusive, step)

    assertEquals(start, progression.first)
    assertEquals(endInclusive, progression.last)

    assertEquals("$start downTo $endInclusive step ${-step}", progression.toString())

    val list = progression.toList()
    assertEquals(6, list.count())
    assertEquals(start, list.first())
    assertEquals(endInclusive, list.last())
  }

}