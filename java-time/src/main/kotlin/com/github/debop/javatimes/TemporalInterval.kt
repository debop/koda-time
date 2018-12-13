package com.github.debop.javatimes

import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAmount

/**
 * joda-time의 Interval을 구현한 클래스입니다.
 *
 * 참고: https://gist.github.com/simon04/26f68a3f21f76dc0bc1ff012676432c9
 *
 * @autor debop
 * @since 18. 4. 16
 */
class TemporalInterval @JvmOverloads constructor(
    override val start: Instant,
    override val end: Instant,
    override val zoneId: ZoneId = UtcZoneId) : AbstractTemporalInterval() {

    init {
        check(start <= end) { "The end instant[$end] must be greater than the start instant[$start]." }
    }

    companion object {
        @JvmStatic
        fun parse(str: String): TemporalInterval {
            val (leftStr, rightStr) = str.split(ReadableTemporalInterval.SEPARATOR, limit = 2)

            val start = ZonedDateTime.parse(leftStr.trim())
            val end = ZonedDateTime.parse(rightStr.trim())

            return temporalIntervalOf(start, end)
        }

        @JvmStatic
        fun parseWithOffset(str: CharSequence): TemporalInterval {
            val (leftStr, rightStr) = str.split(ReadableTemporalInterval.SEPARATOR, limit = 2)

            val start = ZonedDateTime.parse(leftStr.trim(), DateTimeFormatter.ISO_OFFSET_DATE_TIME)
            val end = ZonedDateTime.parse(rightStr.trim(), DateTimeFormatter.ISO_OFFSET_DATE_TIME)

            return temporalIntervalOf(start, end)
        }

    }

    @JvmOverloads
    constructor(startMillis: Long, endMillis: Long, zoneId: ZoneId = UtcZoneId)
    : this(Instant.ofEpochMilli(startMillis), Instant.ofEpochMilli(endMillis), zoneId)

    constructor(start: ZonedDateTime, end: ZonedDateTime)
    : this(start.toInstant(), end.toInstant(), start.zone) {
        require(start.zone == end.zone) { "start timezone is different with end timezone" }
    }

    @JvmOverloads
    constructor(start: Instant, duration: TemporalAmount, zoneId: ZoneId = UtcZoneId) : this(start, start + duration, zoneId)

    @JvmOverloads
    constructor(duration: TemporalAmount, end: Instant, zoneId: ZoneId = UtcZoneId) : this(end - duration, end, zoneId)


    fun withAmountAfterStart(duration: TemporalAmount): TemporalInterval {
        return temporalIntervalOf(start, start + duration, zoneId)
    }

    fun withAmountBeforeEnd(duration: TemporalAmount): TemporalInterval {
        return temporalIntervalOf(end - duration, end, zoneId)
    }
}