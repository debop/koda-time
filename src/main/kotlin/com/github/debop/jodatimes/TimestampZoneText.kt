package com.github.debop.jodatimes

import org.joda.time.DateTime
import org.joda.time.DateTimeZone

/**
 * [DateTime] 의 TimeZone 정보를 제공합니다
 *
 * @author sunghyouk.bae@gmail.com
 */
open class TimestampZoneText(val datetime: DateTime?) {

  constructor(timestamp: Long, zone: DateTimeZone) : this(DateTime(timestamp, zone))

  constructor(timestamp: Long, zoneId: String) : this(DateTime(timestamp, DateTimeZone.forID(zoneId)))

  val timestamp: Long?
    get() = datetime?.millis

  val zoneId: String?
    get() = datetime?.zone?.id

  val timetext: String?
    get() = datetime?.toIsoFormatHMSString()

  override fun toString(): String {
    return "TimestampZoneText(timestamp=$timestamp, zoneId=$zoneId, timetext=$timetext)"
  }
}