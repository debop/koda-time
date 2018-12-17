package com.github.debop.javatimes

import java.time.Instant
import java.time.ZoneId

/**
 * Mutable [AbstractTemporalInterval]
 *
 * @author debop
 */
class MutableTemporalInterval(_start: Instant, _end: Instant,
                              override val zoneId: ZoneId = UtcZoneId) : AbstractTemporalInterval() {

    constructor(other: ReadableTemporalInterval) : this(other.start, other.end, other.zoneId)

    override var start: Instant = _start
        set(value) {
            if(value.isAfter(end)) {
                field = end
                end = value
            } else {
                field = value
            }
        }

    override var end: Instant = _end
        set(value) {
            if(value.isBefore(start)) {
                field = start
                this.start = value

            } else {
                field = value
            }
        }

    override fun toMutableInterval(): MutableTemporalInterval {
        return MutableTemporalInterval(this)
    }

    override fun withStartMillis(startMillis: Long): MutableTemporalInterval = mutableTemporalIntervalOf(startMillis, this.endEpochMillis)
    override fun withStart(start: Instant): MutableTemporalInterval = mutableTemporalIntervalOf(start, this.end)
    override fun withEndMillis(endMillis: Long): MutableTemporalInterval = mutableTemporalIntervalOf(this.startEpochMillis, endMillis)
    override fun withEndMillis(end: Instant): MutableTemporalInterval = mutableTemporalIntervalOf(this.start, end)

}