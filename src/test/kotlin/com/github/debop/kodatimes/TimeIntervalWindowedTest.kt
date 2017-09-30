package com.github.debop.kodatimes

import org.assertj.core.api.Assertions
import org.junit.Test

class TimeIntervalWindowedTest : AbstractKodaTimesTest() {

  @Test
  fun `windowed year`() {
    val start = now().startOfYear()
    val endExclusive = start + 5.years()
    log.debug("start=$start, end=$endExclusive")
    val interval = start .. endExclusive

    val windowed = interval.windowedYear(3, 2)
    windowed.forEach { items ->
      log.debug("items = $items")
      Assertions.assertThat(items.first() in interval).isTrue()
      Assertions.assertThat(items.last() in interval).isTrue()
    }
    Assertions.assertThat(windowed.count()).isEqualTo(3)
  }

  @Test
  fun `zipWithNext years`() {
    val start = now().startOfYear()
    val endExclusive = start + 5.years()
    log.debug("start=$start, end=$endExclusive")
    val interval = start .. endExclusive

    val pairs = interval.zipWithNextYear().toList()
    Assertions.assertThat(pairs.size).isEqualTo(4)

    pairs.forEach { (current, next) ->
      log.debug("current=$current, next=$next")
      Assertions.assertThat(current in interval).isTrue()
      Assertions.assertThat(next in interval).isTrue()
    }
  }
}