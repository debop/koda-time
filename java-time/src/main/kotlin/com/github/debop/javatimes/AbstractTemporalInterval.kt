package com.github.debop.javatimes

import com.github.debop.javatimes.ReadableTemporalInterval.Companion.SEPARATOR
import mu.KLogging
import java.time.Duration
import java.time.Instant
import java.time.Period
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.chrono.ChronoZonedDateTime
import java.time.temporal.ChronoUnit
import java.util.Objects

/**
 * JodaTime's AbstractInterval class 를 구현했습니다.
 *
 * @author debop
 */
abstract class AbstractTemporalInterval : ReadableTemporalInterval {

    companion object : KLogging()

    override val zoneId: ZoneId
        get() = SystemZoneId

    override val startEpochMillis: Long
        get() = start.toEpochMillis()

    override val startDateTime: ZonedDateTime
        get() = ZonedDateTime.ofInstant(start, zoneId)

    override val endEpochMillis: Long
        get() = end.toEpochMillis()

    override val endDateTime: ZonedDateTime
        get() = ZonedDateTime.ofInstant(end, zoneId)

    override val isEmpty: Boolean
        get() = start == end

    /**
     * 두 Interval 이 연속해 있으면 true를 아니면 false 를 반환한다.
     * @param other
     */
    override fun abuts(other: ReadableTemporalInterval): Boolean {
        return start == other.end || end == other.start
    }

    override fun gap(interval: ReadableTemporalInterval): ReadableTemporalInterval? {
        return when {
            overlaps(interval) -> null
            else               -> temporalIntervalOf(maxOf(start, interval.start), minOf(end, interval.end), zoneId)
        }
    }

    override fun overlap(interval: ReadableTemporalInterval): ReadableTemporalInterval? {
        return when {
            overlaps(interval) -> TemporalInterval(maxOf(start, interval.start), minOf(end, interval.end), zoneId)
            else               -> null
        }
    }

    override fun overlaps(other: ReadableTemporalInterval): Boolean {
        return overlaps(other.start) || overlaps(other.end)
    }

    override fun overlaps(moment: ChronoZonedDateTime<*>): Boolean {
        return overlaps(moment.toInstant())
    }

    /**
     * the specific instant in <code>start, end</code>
     * @param instant time instant that is overlaps with temporal interval
     */
    override fun overlaps(instant: Instant): Boolean {
        return instant in start..end
    }

    /**
     * given interval is inner inverval of this interval
     * @param other
     */
    override operator fun contains(other: ReadableTemporalInterval): Boolean {
        return contains(other.start) && contains(other.end)
    }

    /**
     * the specific instant in <code>[start, end)</code>
     * @param instant
     */
    override operator fun contains(instant: Instant): Boolean {
        return start <= instant && instant < end
    }

    override fun contains(datetime: ZonedDateTime): Boolean {
        return contains(datetime.toInstant())
    }

    override fun contains(epochMillis: Long): Boolean {
        return contains(epochMillis.toInstant())
    }

    /**
     * 현 Interval 이 `other` interval 보다 이전인가?
     * @param other
     */
    override fun isBefore(other: ReadableTemporalInterval): Boolean = end < other.end

    /**
     * This interval is before to given instant
     * @param instant
     */
    override fun isBefore(instant: Instant): Boolean = end < instant

    override fun isAfter(other: ReadableTemporalInterval): Boolean = start >= other.start

    override fun isAfter(instant: Instant): Boolean = start >= instant

    override fun withStartMillis(startMillis: Long): ReadableTemporalInterval = when {
        startMillis > endEpochMillis -> temporalIntervalOf(endEpochMillis, startMillis, zoneId)
        else                         -> temporalIntervalOf(startMillis, endEpochMillis, zoneId)
    }

    override fun withStart(start: Instant): ReadableTemporalInterval = when {
        start > this.end -> temporalIntervalOf(end, start, zoneId)
        else             -> temporalIntervalOf(start, end, zoneId)
    }

    override fun withEndMillis(endMillis: Long): ReadableTemporalInterval = when {
        endMillis < startEpochMillis -> temporalIntervalOf(endMillis, startEpochMillis, zoneId)
        else                         -> temporalIntervalOf(startEpochMillis, endMillis, zoneId)
    }

    override fun withEndMillis(end: Instant): ReadableTemporalInterval = when {
        end < this.start -> temporalIntervalOf(end, start, zoneId)
        else             -> temporalIntervalOf(start, end, zoneId)
    }

    override fun toDuration(): Duration = Duration.between(start, end)

    override fun toDurationMillis(): Long = endEpochMillis - startEpochMillis

    override fun toInterval(): ReadableTemporalInterval = temporalIntervalOf(start, end, zoneId)

    override fun toMutableInterval(): MutableTemporalInterval = mutableTemporalIntervalOf(start, end, zoneId)

    override fun toPeriod(): Period = Period.between(start.toLocalDate(), end.toLocalDate())

    override fun toPeriod(unit: ChronoUnit): Period {
        return when(unit) {
            ChronoUnit.DAYS   -> Period.ofDays(toPeriod().days)
            ChronoUnit.WEEKS  -> Period.ofWeeks(toPeriod().days / 7)
            ChronoUnit.MONTHS -> Period.ofDays(toPeriod().months)
            ChronoUnit.YEARS  -> Period.ofDays(toPeriod().years)
            else              -> toPeriod()
        }
    }

    override fun compareTo(other: ReadableTemporalInterval): Int {
        return start.compareTo(other.start)
    }

    override fun equals(other: Any?): Boolean {
        if(other == null) return false
        if(other === this) return true

        return when(other) {
            is ReadableTemporalInterval ->
                start == other.start && end == other.end && zoneId == other.zoneId
            else                        -> false
        }
    }

    override fun hashCode(): Int {
        return Objects.hash(start, end, zoneId)
    }

    override fun toString(): String {
        return "$start$SEPARATOR$end"
    }
}