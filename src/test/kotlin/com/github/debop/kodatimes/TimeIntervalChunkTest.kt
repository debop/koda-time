package com.github.debop.kodatimes

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TimeIntervalChunkTest : AbstractKodaTimesTest() {

  @Test fun `chunk years`() {
    val start = now().startOfYear()
    val endExclusive = start + 5.years()
    log.debug("start=$start, end=$endExclusive")
    val interval = start .. endExclusive

    val chunks = interval.chunkYear(4).toList()
    chunks.forEach(::println)

    assertThat(chunks.size).isEqualTo(2)

    chunks.forEach { chunk ->
      log.debug("chunk=$chunk")
      assertThat(chunk.size).isLessThanOrEqualTo(4)
      assertThat(chunk.first() in interval).isTrue()
      assertThat(chunk.last() in interval).isTrue()
    }

    assertThatThrownBy {
      interval.chunkYear(0)
    }.isInstanceOf(IllegalArgumentException::class.java)

    assertThatThrownBy {
      interval.chunkYear(-1)
    }.isInstanceOf(IllegalArgumentException::class.java)
  }

  @Test fun `chunk year and aggregate`() {
    val start = now().startOfYear()
    val endExclusive = start + 5.years()
    log.debug("start=$start, end=$endExclusive")
    val interval = start .. endExclusive

    val intervals = interval.chunkYear(3).map { years -> years.first() .. years.last() }.toList()

    assertEquals(2, intervals.size)

    intervals.forEach {
      assertTrue { interval.contains(interval) }
    }
  }

  @Test fun `chunk months`() {
    val start = now().startOfMonth()
    val endExclusive = start + 13.months()
    log.debug("start=$start, end=$endExclusive")
    val interval = start .. endExclusive

    val chunks = interval.chunkMonth(5).toList()

    chunks.forEach(::println)
    assertThat(chunks.size).isEqualTo(3)

    chunks.forEach {
      log.debug("chunk=$it")
      assertTrue { it.size <= 5 }
      assertTrue { it.first() in interval }
      assertTrue { it.last() in interval }
    }

    assertThatThrownBy {
      interval.chunkMonth(0)
    }.isInstanceOf(IllegalArgumentException::class.java)

    assertThatThrownBy {
      interval.chunkMonth(-1)
    }.isInstanceOf(IllegalArgumentException::class.java)

  }

  @Test fun `chunk month and aggregate`() {
    val start = now().startOfMonth()
    val endExclusive = start + 13.months()
    log.debug("start=$start, end=$endExclusive")
    val interval = start .. endExclusive

    val intervals = interval.chunkMonth(5).map { months -> months.first() .. months.last() }.toList()

    assertEquals(3, intervals.size)

    intervals.forEach {
      assertTrue { interval.contains(interval) }
    }
  }

  @Test fun `chunk week`() {
    val start = now().startOfWeek()
    val endExclusive = start + 5.weeks()
    log.debug("start=$start, end=$endExclusive")
    val interval = start .. endExclusive

    val chunks = interval.chunkWeek(2).toList()

    chunks.forEach(::println)
    assertThat(chunks.size).isEqualTo(3)

    chunks.forEach {
      log.debug("chunk=$it")
      assertTrue { it.size <= 2 }
      assertTrue { it.first() in interval }
      assertTrue { it.last() in interval }
    }

    assertThatThrownBy {
      interval.chunkWeek(0)
    }.isInstanceOf(IllegalArgumentException::class.java)

    assertThatThrownBy {
      interval.chunkWeek(-1)
    }.isInstanceOf(IllegalArgumentException::class.java)

  }

  @Test fun `chunk week and aggregate`() {
    val start = now().startOfWeek()
    val endExclusive = start + 5.weeks()
    log.debug("start=$start, end=$endExclusive")
    val interval = start .. endExclusive

    val intervals = interval.chunkWeek(2).map { it.first() .. it.last() }.toList()

    intervals.forEach(::println)
    assertEquals(3, intervals.size)

    intervals.forEach {
      assertTrue { interval.contains(interval) }
    }
  }

  @Test fun `chunk days`() {
    val start = now().startOfDay()
    val endExclusive = start + 66.days()
    log.debug("start=$start, end=$endExclusive")
    val interval = start .. endExclusive

    val chunks = interval.chunkDay(30).toList()

    chunks.forEach(::println)
    assertThat(chunks.size).isEqualTo(3)

    chunks.forEach {
      log.debug("chunk=$it")
      assertTrue { it.size <= 30 }
      assertTrue { it.first() in interval }
      assertTrue { it.last() in interval }
    }

    assertThatThrownBy {
      interval.chunkDay(0)
    }.isInstanceOf(IllegalArgumentException::class.java)

    assertThatThrownBy {
      interval.chunkDay(-1)
    }.isInstanceOf(IllegalArgumentException::class.java)

  }

  @Test fun `chunk day and aggregate`() {
    val start = now().startOfDay()
    val endExclusive = start + 66.days()
    log.debug("start=$start, end=$endExclusive")
    val interval = start .. endExclusive

    val intervals = interval.chunkDay(30).map { days -> days.first() .. days.last() }.toList()

    intervals.forEach(::println)
    assertEquals(3, intervals.size)

    intervals.forEach {
      assertTrue { interval.contains(interval) }
    }
  }

  @Test fun `chunk hours`() {
    val start = now().trimToHour()
    val endExclusive = start + 66.hours()
    log.debug("start=$start, end=$endExclusive")
    val interval = start .. endExclusive

    val chunks = interval.chunkHour(20).toList()

    chunks.forEach(::println)
    assertThat(chunks.size).isEqualTo(4)

    chunks.forEach {
      log.debug("chunk=$it")
      assertTrue { it.size <= 20 }
      assertTrue { it.first() in interval }
      assertTrue { it.last() in interval }
    }

    assertThatThrownBy {
      interval.chunkHour(0)
    }.isInstanceOf(IllegalArgumentException::class.java)

    assertThatThrownBy {
      interval.chunkHour(-1)
    }.isInstanceOf(IllegalArgumentException::class.java)

  }

  @Test fun `chunk hour and aggregate`() {
    val start = now().trimToHour()
    val endExclusive = start + 66.hours()
    log.debug("start=$start, end=$endExclusive")
    val interval = start .. endExclusive

    val intervals = interval.chunkHour(20).map { it.first() .. it.last() }.toList()

    intervals.forEach(::println)
    assertEquals(4, intervals.size)

    intervals.forEach {
      assertTrue { interval.contains(interval) }
    }
  }

  @Test fun `chunk minute`() {
    val start = now().trimToMinute()
    val endExclusive = start + 33.minutes()
    log.debug("start=$start, end=$endExclusive")
    val interval = start .. endExclusive

    val chunks = interval.chunkMinute(10).toList()

    chunks.forEach(::println)
    assertThat(chunks.size).isEqualTo(4)

    chunks.forEach {
      log.debug("chunk=$it")
      assertTrue { it.size <= 10 }
      assertTrue { it.first() in interval }
      assertTrue { it.last() in interval }
    }

    assertThatThrownBy {
      interval.chunkMinute(0)
    }.isInstanceOf(IllegalArgumentException::class.java)

    assertThatThrownBy {
      interval.chunkMinute(-1)
    }.isInstanceOf(IllegalArgumentException::class.java)

  }

  @Test fun `chunk minute and aggregate`() {
    val start = now().trimToMinute()
    val endExclusive = start + 33.minutes()
    log.debug("start=$start, end=$endExclusive")
    val interval = start .. endExclusive

    val intervals = interval.chunkMinute(10).map { it.first() .. it.last() }.toList()

    intervals.forEach(::println)
    assertEquals(4, intervals.size)

    intervals.forEach {
      assertTrue { interval.contains(interval) }
    }
  }

  @Test fun `chunk second`() {
    val start = now().trimToSecond()
    val endExclusive = start + 33.seconds()
    log.debug("start=$start, end=$endExclusive")
    val interval = start .. endExclusive

    val chunks = interval.chunkSecond(10).toList()

    chunks.forEach(::println)
    assertThat(chunks.size).isEqualTo(4)

    chunks.forEach {
      log.debug("chunk=$it")
      assertTrue { it.size <= 10 }
      assertTrue { it.first() in interval }
      assertTrue { it.last() in interval }
    }

    assertThatThrownBy {
      interval.chunkSecond(0)
    }.isInstanceOf(IllegalArgumentException::class.java)

    assertThatThrownBy {
      interval.chunkSecond(-1)
    }.isInstanceOf(IllegalArgumentException::class.java)

  }

  @Test fun `chunk second and aggregate`() {
    val start = now().trimToSecond()
    val endExclusive = start + 33.seconds()
    log.debug("start=$start, end=$endExclusive")
    val interval = start .. endExclusive

    val intervals = interval.chunkSecond(10).map { it.first() .. it.last() }.toList()

    intervals.forEach(::println)
    assertEquals(4, intervals.size)

    intervals.forEach {
      assertTrue { interval.contains(interval) }
    }
  }
}