package com.github.debop.javatimes.ranges

import com.github.debop.javatimes.AbstractJavaTimesTest
import com.github.debop.javatimes.days
import com.github.debop.javatimes.hours
import com.github.debop.javatimes.minutes
import com.github.debop.javatimes.monthPeriod
import com.github.debop.javatimes.nowInstant
import com.github.debop.javatimes.nowLocalDate
import com.github.debop.javatimes.nowLocalDateTime
import com.github.debop.javatimes.nowLocalTime
import com.github.debop.javatimes.nowOffsetDateTime
import com.github.debop.javatimes.nowZonedDateTime
import com.github.debop.javatimes.seconds
import com.github.debop.javatimes.weeks
import com.github.debop.javatimes.yearPeriod
import mu.KLogging
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldEqualTo
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.ZonedDateTime
import java.time.temporal.Temporal


@Suppress("UNCHECKED_CAST")
abstract class TemporalRangeChunkTest<T> : AbstractJavaTimesTest() where T : Temporal, T : Comparable<T> {

    companion object : KLogging()

    abstract val start: T

    @Test
    open fun `chunk in year`() {
        val range = temporalRangeOf(start, (start + 5.yearPeriod()) as T)
        val chunks = range.chunkYear(3).toList()

        chunks.size shouldEqualTo 2
        assertChunkInRange(range, chunks)
    }

    @Test
    open fun `chunk in month`() {
        val range = temporalRangeOf(start, (start + 5.monthPeriod()) as T)
        val chunks = range.chunkMonth(3).toList()

        chunks.size shouldEqualTo 2
        assertChunkInRange(range, chunks)
    }

    @Test
    open fun `chunk in week`() {
        val range = temporalRangeOf(start, (start + 5.weeks()) as T)
        val chunks = range.chunkWeek(3).toList()

        chunks.size shouldEqualTo 2
        assertChunkInRange(range, chunks)
    }

    @Test
    fun `chunk in day`() {
        val range = temporalRangeOf(start, (start + 5.days()) as T)
        val chunks = range.chunkDay(3).toList()

        chunks.size shouldEqualTo 2
        assertChunkInRange(range, chunks)
    }

    @Test
    fun `chunk in hour`() {
        val range = temporalRangeOf(start, (start + 5.hours()) as T)
        val chunks = range.chunkHour(3).toList()

        chunks.size shouldEqualTo 2
        assertChunkInRange(range, chunks)
    }

    @Test
    fun `chunk in minute`() {
        val range = temporalRangeOf(start, (start + 5.minutes()) as T)
        val chunks = range.chunkMinute(3).toList()

        chunks.size shouldEqualTo 2
        assertChunkInRange(range, chunks)
    }

    @Test
    fun `chunk in second`() {
        val range = temporalRangeOf(start, (start + 5.seconds()) as T)
        val chunks = range.chunkSecond(3).toList()

        chunks.size shouldEqualTo 2
        assertChunkInRange(range, chunks)
    }

    private fun assertChunkInRange(range: TemporalRange<T>, chunks: List<List<T>>) {
        chunks.forEach { chunk ->
            chunk.all { it in range }.shouldBeTrue()
        }
    }
}

class InstantRangeChunkTest : TemporalRangeChunkTest<Instant>() {
    override val start: Instant = nowInstant()

    override fun `chunk in year`() {
        println("Instant not support chunkYear")
    }

    override fun `chunk in month`() {
        println("Instant not support chunkYear")
    }
}

class LocalDateTimeRangeChunkTest : TemporalRangeChunkTest<LocalDateTime>() {
    override val start: LocalDateTime = nowLocalDateTime()
}

class OffsetDateTimeRangeChunkTest : TemporalRangeChunkTest<OffsetDateTime>() {
    override val start: OffsetDateTime = nowOffsetDateTime()
}

class ZonedDateTimeRangeChunkTest : TemporalRangeChunkTest<ZonedDateTime>() {
    override val start: ZonedDateTime = nowZonedDateTime()
}

@Disabled("LocalTime not support range")
class LocalTimeRangeChunkTest : TemporalRangeChunkTest<LocalTime>() {
    override val start: LocalTime = nowLocalTime()
}

@Disabled("LocalDate not support range")
class LocalDateRangeChunkTest : TemporalRangeChunkTest<LocalDate>() {
    override val start: LocalDate = nowLocalDate()
}
