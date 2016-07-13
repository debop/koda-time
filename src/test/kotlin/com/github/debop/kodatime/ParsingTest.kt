package com.github.debop.kodatime

import org.assertj.core.api.Assertions.assertThat
import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.joda.time.LocalTime
import org.junit.Test

/**
 * @author sunghyouk.bae@gmail.com
 */
class ParsingTest : AbstractJodaTimesTest() {

  @Test fun stringToDate() {
    val expected = DateTime("2016-08-08")

    assertThat("2016-08-08".toDateTime()).isEqualTo(expected)
    assertThat("2016-08-08".toDateTime("yyyy-MM-dd")).isEqualTo(expected)

    assertThat("".toDateTime()).isNull()
  }

  @Test fun stringToLocalDate() {
    val expected = LocalDate("2016-08-08")

    assertThat("2016-08-08".toLocalDate()).isEqualTo(expected)
    assertThat("2016-08-08".toLocalDate("yyyy-MM-dd")).isEqualTo(expected)

    assertThat("".toLocalDate()).isNull()
  }

  @Test fun stringToLocalTime() {
    val expected = LocalTime("17:55:34")

    assertThat("17:55:34".toLocalTime()).isEqualTo(expected)
    assertThat("17:55:34".toLocalTime("HH:mm:ss")).isEqualTo(expected)

    // hh : [0~12], HH: [0~24]
    assertThat("17:55:34".toLocalTime("hh:mm:ss")).isNull()

    assertThat("".toLocalTime()).isNull()
    assertThat("".toLocalTime("HH:mm:ss")).isNull()
  }
}