package com.github.debop.javatimes

import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.OffsetTime
import java.time.ZonedDateTime
import java.time.chrono.ChronoLocalDate
import java.time.temporal.ChronoUnit
import java.time.temporal.Temporal

fun Temporal.durationTo(endExclusive: Temporal): Duration = Duration.between(this, endExclusive)

@Suppress("UNCHECKED_CAST")
fun <T : Temporal> T.trimToMillis(millis: Long = 0L): T {
    return when(this) {
        is Instant         -> truncatedTo(ChronoUnit.MILLIS).plusMillis(millis) as T
        is LocalTime       -> truncatedTo(ChronoUnit.MILLIS).plusNanos(millis * NANO_PER_SECOND) as T
        is ChronoLocalDate -> this
        is LocalDateTime   -> truncatedTo(ChronoUnit.MILLIS).plusNanos(millis * NANO_PER_SECOND) as T
        is OffsetTime      -> truncatedTo(ChronoUnit.MILLIS).plusNanos(millis * NANO_PER_SECOND) as T
        is OffsetDateTime  -> truncatedTo(ChronoUnit.MILLIS).plusNanos(millis * NANO_PER_SECOND) as T
        is ZonedDateTime   -> truncatedTo(ChronoUnit.MILLIS).plusNanos(millis * NANO_PER_SECOND) as T

        else               -> throw UnsupportedOperationException("Not supported for $javaClass")
    }
}


@Suppress("UNCHECKED_CAST")
fun <T : Temporal> T.trimToSecond(seconds: Long = 0L): T {
    return when(this) {
        is LocalTime      -> truncatedTo(ChronoUnit.SECONDS).plusSeconds(seconds) as T
        is LocalDate      -> this
        is LocalDateTime  -> truncatedTo(ChronoUnit.SECONDS).plusSeconds(seconds) as T
        is OffsetTime     -> truncatedTo(ChronoUnit.SECONDS).plusSeconds(seconds) as T
        is OffsetDateTime -> truncatedTo(ChronoUnit.SECONDS).plusSeconds(seconds) as T
        is ZonedDateTime  -> truncatedTo(ChronoUnit.SECONDS).plusSeconds(seconds) as T

        else              -> throw UnsupportedOperationException("Not supported for $javaClass")
    }
}

@Suppress("UNCHECKED_CAST")
fun <T : Temporal> T.trimToMinute(minutes: Long = 0L): T {
    return when(this) {
        is LocalTime      -> truncatedTo(ChronoUnit.MINUTES).plusMinutes(minutes) as T
        is LocalDate      -> this
        is LocalDateTime  -> truncatedTo(ChronoUnit.MINUTES).plusMinutes(minutes) as T
        is OffsetTime     -> truncatedTo(ChronoUnit.MINUTES).plusMinutes(minutes) as T
        is OffsetDateTime -> truncatedTo(ChronoUnit.MINUTES).plusMinutes(minutes) as T
        is ZonedDateTime  -> truncatedTo(ChronoUnit.MINUTES).plusMinutes(minutes) as T

        else              -> throw UnsupportedOperationException("Not supported for $javaClass")
    }
}

@Suppress("UNCHECKED_CAST")
fun <T : Temporal> T.trimToHour(hours: Long = 0L): T {
    return when(this) {
        is LocalTime      -> truncatedTo(ChronoUnit.HOURS).plusHours(hours) as T
        is LocalDate      -> this
        is LocalDateTime  -> truncatedTo(ChronoUnit.HOURS).plusHours(hours) as T
        is OffsetTime     -> truncatedTo(ChronoUnit.HOURS).plusHours(hours) as T
        is OffsetDateTime -> truncatedTo(ChronoUnit.HOURS).plusHours(hours) as T
        is ZonedDateTime  -> truncatedTo(ChronoUnit.HOURS).plusHours(hours) as T

        else              -> throw UnsupportedOperationException("Not supported for $javaClass")
    }
}