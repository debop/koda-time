package com.github.debop.kodatimes

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test

class TimeIntervalWindowedTest : AbstractKodaTimesTest() {

  @Test
  fun `windowed year`() {
    val start = now().startOfYear()
    val endExclusive = start + 5.years()
    val interval = start .. endExclusive
    log.debug("interval=$interval")

    val windowed = interval.windowedYear(3, 2)
    windowed.forEach { items ->
      log.debug("items = $items")
      assertThat(items.first() in interval).isTrue()
      assertThat(items.last() in interval).isTrue()
    }
    assertThat(windowed.count()).isEqualTo(3)

    assertThatThrownBy {
      interval.windowedYear(-1, 2)
    }.isInstanceOf(IllegalArgumentException::class.java)
    assertThatThrownBy {
      interval.windowedYear(2, -2)
    }.isInstanceOf(IllegalArgumentException::class.java)
  }

  @Test
  fun `windowed month`() {
    val start = now().startOfMonth()
    val endExclusive = start + 5.months()
    val interval = start .. endExclusive
    log.debug("interval=$interval")

    val windowed = interval.windowedMonth(3, 2)
    windowed.forEach { items ->
      log.debug("items = $items")
      assertThat(items.all { it in interval }).isTrue()
    }
    assertThat(windowed.count()).isEqualTo(3)

    assertThatThrownBy {
      interval.windowedMonth(-1, 2)
    }.isInstanceOf(IllegalArgumentException::class.java)
    assertThatThrownBy {
      interval.windowedMonth(2, -2)
    }.isInstanceOf(IllegalArgumentException::class.java)
  }

  @Test
  fun `windowed week`() {
    val start = now().startOfWeek()
    val endExclusive = start + 5.weeks()
    val interval = start .. endExclusive
    log.debug("interval=$interval")

    val windowed = interval.windowedWeek(3, 2)
    windowed.forEach { items ->
      log.debug("items = $items")
      assertThat(items.all { it in interval }).isTrue()
    }
    assertThat(windowed.count()).isEqualTo(3)

    assertThatThrownBy {
      interval.windowedWeek(-1, 2)
    }.isInstanceOf(IllegalArgumentException::class.java)
    assertThatThrownBy {
      interval.windowedWeek(2, -2)
    }.isInstanceOf(IllegalArgumentException::class.java)
  }

  @Test
  fun `windowed day`() {
    val start = now().startOfDay()
    val endExclusive = start + 5.days()
    val interval = start .. endExclusive
    log.debug("interval=$interval")

    val windowed = interval.windowedDay(3, 2)
    windowed.forEach { items ->
      log.debug("items = $items")
      assertThat(items.all { it in interval }).isTrue()
    }
    assertThat(windowed.count()).isEqualTo(3)

    assertThatThrownBy {
      interval.windowedDay(-1, 2)
    }.isInstanceOf(IllegalArgumentException::class.java)
    assertThatThrownBy {
      interval.windowedDay(2, -2)
    }.isInstanceOf(IllegalArgumentException::class.java)
  }

  @Test
  fun `windowed hour`() {
    val start = now().trimToHour()
    val endExclusive = start + 5.hours()
    val interval = start .. endExclusive
    log.debug("interval=$interval")

    val windowed = interval.windowedHour(3, 2)
    windowed.forEach { items ->
      log.debug("items = $items")
      assertThat(items.all { it in interval }).isTrue()
    }
    assertThat(windowed.count()).isEqualTo(3)

    assertThatThrownBy {
      interval.windowedHour(-1, 2)
    }.isInstanceOf(IllegalArgumentException::class.java)
    assertThatThrownBy {
      interval.windowedHour(2, -2)
    }.isInstanceOf(IllegalArgumentException::class.java)
  }

  @Test
  fun `windowed minute`() {
    val start = now().trimToMinute()
    val endExclusive = start + 5.minutes()
    val interval = start .. endExclusive
    log.debug("interval=$interval")

    val windowed = interval.windowedMinute(3, 2)
    windowed.forEach { items ->
      log.debug("items = $items")
      assertThat(items.all { it in interval }).isTrue()
    }
    assertThat(windowed.count()).isEqualTo(3)

    assertThatThrownBy {
      interval.windowedMinute(-1, 2)
    }.isInstanceOf(IllegalArgumentException::class.java)
    assertThatThrownBy {
      interval.windowedMinute(2, -2)
    }.isInstanceOf(IllegalArgumentException::class.java)
  }

  @Test
  fun `windowed second`() {
    val start = now().trimToSecond()
    val endExclusive = start + 5.seconds()
    val interval = start .. endExclusive
    log.debug("interval=$interval")

    val windowed = interval.windowedSecond(3, 2)
    windowed.forEach { items ->
      log.debug("items = $items")
      assertThat(items.all { it in interval }).isTrue()
    }
    assertThat(windowed.count()).isEqualTo(3)

    assertThatThrownBy {
      interval.windowedSecond(-1, 2)
    }.isInstanceOf(IllegalArgumentException::class.java)
    assertThatThrownBy {
      interval.windowedSecond(2, -2)
    }.isInstanceOf(IllegalArgumentException::class.java)
  }

  @Test
  fun `zipWithNext years`() {
    val start = now().startOfYear()
    val endExclusive = start + 5.years()
    val interval = start .. endExclusive
    log.debug("interval=$interval")

    val pairs = interval.zipWithNextYear().toList()
    assertThat(pairs.size).isEqualTo(4)

    pairs.forEach { (current, next) ->
      log.debug("current=$current, next=$next")
      assertThat(current in interval).isTrue()
      assertThat(next in interval).isTrue()
    }
  }

  @Test
  fun `zipWithNext months`() {
    val start = now().startOfMonth()
    val endExclusive = start + 5.months()
    val interval = start .. endExclusive
    log.debug("interval=$interval")

    val pairs = interval.zipWithNextMonth().toList()
    assertThat(pairs.size).isEqualTo(4)

    pairs.forEach { (current, next) ->
      log.debug("current=$current, next=$next")
      assertThat(current in interval).isTrue()
      assertThat(next in interval).isTrue()
    }
  }

  @Test
  fun `zipWithNext weeks`() {
    val start = now().startOfWeek()
    val endExclusive = start + 5.weeks()
    val interval = start .. endExclusive
    log.debug("interval=$interval")

    val pairs = interval.zipWithNextWeek().toList()
    assertThat(pairs.size).isEqualTo(4)

    pairs.forEach { (current, next) ->
      log.debug("current=$current, next=$next")
      assertThat(current in interval).isTrue()
      assertThat(next in interval).isTrue()
    }
  }

  @Test
  fun `zipWithNext days`() {
    val start = now().startOfDay()
    val endExclusive = start + 5.days()
    val interval = start .. endExclusive
    log.debug("interval=$interval")

    val pairs = interval.zipWithNextDay().toList()
    assertThat(pairs.size).isEqualTo(4)

    pairs.forEach { (current, next) ->
      log.debug("current=$current, next=$next")
      assertThat(current in interval).isTrue()
      assertThat(next in interval).isTrue()
    }
  }

  @Test
  fun `zipWithNext hours`() {
    val start = now().trimToHour()
    val endExclusive = start + 5.hours()
    val interval = start .. endExclusive
    log.debug("interval=$interval")

    val pairs = interval.zipWithNextHour().toList()
    assertThat(pairs.size).isEqualTo(4)

    pairs.forEach { (current, next) ->
      log.debug("current=$current, next=$next")
      assertThat(current in interval).isTrue()
      assertThat(next in interval).isTrue()
    }
  }

  @Test
  fun `zipWithNext minutes`() {
    val start = now().trimToMinute()
    val endExclusive = start + 5.minutes()
    val interval = start .. endExclusive
    log.debug("interval=$interval")

    val pairs = interval.zipWithNextMinute().toList()
    assertThat(pairs.size).isEqualTo(4)

    pairs.forEach { (current, next) ->
      log.debug("current=$current, next=$next")
      assertThat(current in interval).isTrue()
      assertThat(next in interval).isTrue()
    }
  }

  @Test
  fun `zipWithNext seconds`() {
    val start = now().trimToSecond()
    val endExclusive = start + 5.seconds()
    val interval = start .. endExclusive
    log.debug("interval=$interval")

    val pairs = interval.zipWithNextSecond().toList()
    assertThat(pairs.size).isEqualTo(4)

    pairs.forEach { (current, next) ->
      log.debug("current=$current, next=$next")
      assertThat(current in interval).isTrue()
      assertThat(next in interval).isTrue()
    }
  }
}