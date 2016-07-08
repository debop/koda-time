package com.github.debop.jodatimes

import org.assertj.core.api.Assertions.assertThat
import org.joda.time.Interval
import org.junit.Test

class IntervalTest : AbstractJodaTimesTest() {

  @Test fun rangeTest() {
    val start = now()
    val end = start + 1.days()

    val range: Interval = start .. end

    val inRange = range.days().all { day -> start <= day && day <= end }
    assertThat(inRange).isTrue()

    assertThat(range.days().last()).isEqualTo(end)
  }

  @Test fun rangeStepTest() {
    val start = now()
    val end = start + 1.days()

    (start .. end step 1.hours().toPeriod()).forEach { hour ->
      assertThat(start <= hour && hour <= end).isTrue()
    }
  }
}