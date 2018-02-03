package com.github.debop.javatimes.ranges

import com.github.debop.javatimes.AbstractJavaTimesTest
import com.github.debop.javatimes.hours
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.time.*
import java.time.temporal.Temporal

abstract class TemporalProgressionTest<T> : AbstractJavaTimesTest() where T : Temporal, T : Comparable<T> {

  abstract val start: T
  val endInclusive: T get() = (start + Duration.ofDays(1)) as T

  @Test fun `construcot LocalDateTimeProgression`() {

    val progression = TemporalProgression.fromClosedRange(start, endInclusive, 1.hours)

    assertEquals(start, progression.first)
    assertEquals(endInclusive, progression.last)
    assertEquals(1.hours, progression.step)

    val elements = progression.toList()
    assertEquals(25, elements.count())
  }

  @Test fun `zero step`() {
    val temporal = start
    assertThrows(IllegalArgumentException::class.java) {
      TemporalProgression.fromClosedRange(temporal, temporal, Duration.ZERO)
    }
  }

  @Test fun `step greater than range`() {
    val progression = TemporalProgression.fromClosedRange(start, endInclusive, Duration.ofDays(7))

    assertEquals(start, progression.first)
    // last is not endInclusive, step over endInclusive, so last is equal to start
    assertEquals(start, progression.last)
    assertEquals(Duration.ofDays(7), progression.step)

    assertEquals(1, progression.toList().size)
  }

  @Test fun `stepping not exact endInclusive`() {
    val progression = TemporalProgression.fromClosedRange(start, endInclusive, Duration.ofHours(5))

    assertEquals(start, progression.first)
    assertEquals(start + Duration.ofHours(20), progression.last)
    assertNotEquals(endInclusive, progression.last)

    val elements = progression.toList()
    assertEquals(5, elements.size)
  }

  @Test fun `downTo progression`() {

    val step = Duration.ofHours(-1)
    val progression = TemporalProgression.fromClosedRange(endInclusive, start, step)

    assertEquals(endInclusive, progression.first)
    assertEquals(start, progression.last)

    assertEquals("$endInclusive downTo $start step ${step.negated()}", progression.toString())

    val elements = progression.toList()
    assertEquals(25, elements.size)
    assertEquals(endInclusive, elements.first())
    assertEquals(start, elements.last())
  }
}

class LocalDateTimeProgressionTest : TemporalProgressionTest<LocalDateTime>() {
  override val start: LocalDateTime = LocalDateTime.now()
}

@Disabled("Cannot support range")
class LocalDateProgressionTest : TemporalProgressionTest<LocalDate>() {
  override val start: LocalDate = LocalDate.now()
}

@Disabled("Cannot support range")
class LocalTimeProgressionTest : TemporalProgressionTest<LocalTime>() {
  override val start: LocalTime = LocalTime.now()
}

class OffsetDateTimeProgressionTest : TemporalProgressionTest<OffsetDateTime>() {
  override val start: OffsetDateTime = OffsetDateTime.now()
}

class ZonedDateTimeProgressionTest : TemporalProgressionTest<ZonedDateTime>() {
  override val start: ZonedDateTime = ZonedDateTime.now()
}