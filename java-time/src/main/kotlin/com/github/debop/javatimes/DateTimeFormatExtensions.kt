/*
 * Copyright (c) 2016. Sunghyouk Bae <sunghyouk.bae@gmail.com>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.debop.javatimes

import java.time.Instant
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor


/**
 * 날짜를 ISO 형식의 문자열로 만듭니다.
 * 예: DateTime : '2011-12-03T10:15:30',
 *     OffsetDateTime:'2011-12-03T10:15:30+01:00',
 *     ZonedDateTime: '2011-12-03T10:15:30+01:00[Europe/Paris]'.
 */
fun TemporalAccessor.toIsoString(): String = when (this) {
    is Instant -> DateTimeFormatter.ISO_DATE_TIME.format(this.toLocalDateTime())
    else       -> DateTimeFormatter.ISO_DATE_TIME.format(this)
}

/**
 * 일자를 ISO 형식의 문자열로 만듭니다.
 * 예: Date: '2011-12-03', OffsetDate: '2011-12-03+01:00'.
 */
fun TemporalAccessor.toIsoDateString(): String = when (this) {
    is Instant -> DateTimeFormatter.ISO_DATE.format(this.toLocalDateTime())
    else       -> DateTimeFormatter.ISO_DATE.format(this)
}

/**
 * 시각을 ISO 형식의 문자열로 만듭니다.
 * 예: '10:15', '10:15:30', OffsetTime: '10:15:30+01:00'.
 */
fun TemporalAccessor.toIsoTimeString(): String = when (this) {
    is Instant -> DateTimeFormatter.ISO_TIME.format(this.toLocalDateTime())
    else       -> DateTimeFormatter.ISO_TIME.format(this)
}

/**
 * 날짜를 ISO 형식의 로컬형식으로 표현합니다.
 * 예: '2011-12-03T10:15:30'
 */
fun TemporalAccessor.toLocalIsoString(): String = when (this) {
    is Instant -> DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(this.toLocalDateTime())
    else       -> DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(this)
}

/**
 * 일자을 ISO 형식의 로컬형식으로 표현합니다.
 * 예: Date: '2011-12-03'
 */
fun TemporalAccessor.toIsoLocalDateString(): String = when (this) {
    is Instant -> DateTimeFormatter.ISO_LOCAL_DATE.format(this.toLocalDateTime())
    else       -> DateTimeFormatter.ISO_LOCAL_DATE.format(this)
}

/**
 * 시각을 ISO 형식의 로컬형식으로 표현합니다.
 * 예: '10:15', '10:15:30'
 */
fun TemporalAccessor.toIsoLocalTimeString(): String = when (this) {
    is Instant -> DateTimeFormatter.ISO_LOCAL_TIME.format(this.toLocalDateTime())
    else       -> DateTimeFormatter.ISO_LOCAL_TIME.format(this)
}


fun TemporalAccessor.toIsoOffsetDateTimeString(): String = when (this) {
    is Instant -> DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(this.toOffsetDateTime())
    else       -> DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(this)
}

fun TemporalAccessor.toIsoOffsetDateString(): String = when (this) {
    is Instant -> DateTimeFormatter.ISO_OFFSET_DATE.format(this.toOffsetDateTime())
    else       -> DateTimeFormatter.ISO_OFFSET_DATE.format(this)
}

fun TemporalAccessor.toIsoOffsetTimeString(): String = when (this) {
    is Instant -> DateTimeFormatter.ISO_OFFSET_TIME.format(this.toOffsetDateTime())
    else       -> DateTimeFormatter.ISO_OFFSET_TIME.format(this)
}

fun TemporalAccessor.toIsoZonedDateTimeString(): String = when (this) {
    is Instant -> DateTimeFormatter.ISO_ZONED_DATE_TIME.format(this.toZonedDateTime())
    else       -> DateTimeFormatter.ISO_ZONED_DATE_TIME.format(this)
}


