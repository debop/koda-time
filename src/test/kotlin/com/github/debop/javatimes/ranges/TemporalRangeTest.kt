package com.github.debop.javatimes.ranges

import com.github.debop.javatimes.AbstractJavaTimesTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.time.*
import java.time.temporal.Temporal

abstract class TemporalRangeTest<T> : AbstractJavaTimesTest() where T : Temporal, T : Comparable<T> {

  abstract val start: T
  val endInclusive: T get() = (start + Duration.ofDays(1)) as T
  abstract val range: TemporalRange<T>

  @Test fun `simple constructor`() {
    val range = TemporalRange.of(start, endInclusive)

    assertEquals(start, range.start)
    assertEquals(endInclusive, range.endInclusive)
    assertEquals(start, range.first)
    assertEquals(endInclusive, range.last)
  }

  @Test fun `empty range`() {
    val range = TemporalRange.of(endInclusive, start)

    assertTrue(range.isEmpty())
    assertEquals(TemporalRange.EMPTY, range)
  }

  @Test fun `create by rangeTo`() {
    val range1 = range
    val range2 = TemporalRange.of(start, endInclusive)
    assertEquals(range2, range1)
  }
}

class LocalDateTimeRangeTest : TemporalRangeTest<LocalDateTime>() {
  override val start: LocalDateTime = LocalDateTime.now()
  override val range: TemporalRange<LocalDateTime> = start .. endInclusive
}

class OffsetDateTimeRangeTest : TemporalRangeTest<OffsetDateTime>() {
  override val start: OffsetDateTime = OffsetDateTime.now()
  override val range: TemporalRange<OffsetDateTime> = start .. endInclusive
}

class ZonedDateTimeRangeTest : TemporalRangeTest<ZonedDateTime>() {
  override val start: ZonedDateTime = ZonedDateTime.now()
  override val range: TemporalRange<ZonedDateTime> = start .. endInclusive
}

@Disabled("Cannot support range")
class LocalDateRangeTest : TemporalRangeTest<LocalDate>() {
  override val start: LocalDate = LocalDate.now()
  override val range: TemporalRange<LocalDate> = start .. endInclusive
}

@Disabled("Cannot support range")
class LocalTimeRangeTest : TemporalRangeTest<LocalTime>() {
  override val start: LocalTime = LocalTime.now()
  override val range: TemporalRange<LocalTime> = start .. endInclusive
}
