package com.github.debop.javatimes

import java.io.Serializable
import java.time.Duration
import java.time.Instant
import java.time.Period
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.chrono.ChronoZonedDateTime
import java.time.temporal.ChronoUnit

/**
 * JodaTime 의 `ReadableTemporalInterval` 과 같은 기능을 수행합니다.
 *
 * @author debop
 */
interface ReadableTemporalInterval : Comparable<ReadableTemporalInterval>, Serializable {

    companion object {
        const val SEPARATOR = "~"
        val EMPTY_INTERVAL = temporalIntervalOf(0L, 0L)
    }

    val zoneId: ZoneId

    val start: Instant
    val startEpochMillis: Long
    val startDateTime: ZonedDateTime

    val end: Instant
    val endEpochMillis: Long
    val endDateTime: ZonedDateTime

    val isEmpty: Boolean

    fun abuts(other: ReadableTemporalInterval): Boolean
    fun gap(interval: ReadableTemporalInterval): ReadableTemporalInterval?
    fun overlap(interval: ReadableTemporalInterval): ReadableTemporalInterval?

    fun overlaps(other: ReadableTemporalInterval): Boolean
    fun overlaps(moment: ChronoZonedDateTime<*>): Boolean
    fun overlaps(instant: Instant): Boolean

    operator fun contains(other: ReadableTemporalInterval): Boolean
    operator fun contains(instant: Instant): Boolean
    operator fun contains(datetime: ZonedDateTime): Boolean
    operator fun contains(epochMillis: Long): Boolean

    fun isBefore(other: ReadableTemporalInterval): Boolean
    fun isBefore(instant: Instant): Boolean

    fun isAfter(other: ReadableTemporalInterval): Boolean
    fun isAfter(instant: Instant): Boolean

    fun withStartMillis(startMillis: Long): ReadableTemporalInterval
    fun withStart(start: Instant): ReadableTemporalInterval

    fun withEndMillis(endMillis: Long): ReadableTemporalInterval
    fun withEndMillis(end: Instant): ReadableTemporalInterval

    fun toDuration(): Duration
    fun toDurationMillis(): Long

    fun toInterval(): ReadableTemporalInterval
    fun toMutableInterval(): MutableTemporalInterval

    fun toPeriod(): Period
    fun toPeriod(unit: ChronoUnit): Period
}