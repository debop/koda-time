package com.github.debop.javatimes.ranges

import com.github.debop.javatimes.AbstractJavaTimesTest
import com.github.debop.javatimes.toDate
import com.github.debop.kodatimes.today
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.Duration

class DateRangeTest : AbstractJavaTimesTest() {

  @Test fun `simple creation`() {

    val start = today().toDate()
    val endInclusive = (start.toInstant() + Duration.ofDays(5)).toDate()
    val range = DateRange(start, endInclusive)

    Assertions.assertEquals(start, range.start)
    Assertions.assertEquals(endInclusive, range.endInclusive)

    Assertions.assertEquals(start, range.first)
    Assertions.assertEquals(endInclusive, range.last)

    Assertions.assertEquals("$start..$endInclusive", range.toString())
  }

  @Test fun `empty range`() {

    val start = today().toDate()
    val endInclusive = (start.toInstant() - Duration.ofDays(1)).toDate()

    val range = DateRange(start, endInclusive)
    Assertions.assertTrue { range.isEmpty() }

    Assertions.assertEquals(DateRange.EMPTY, range)
  }
}