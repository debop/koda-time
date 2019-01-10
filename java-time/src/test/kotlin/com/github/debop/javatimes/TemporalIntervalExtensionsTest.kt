package com.github.debop.javatimes

import mu.KLogging
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test

class TemporalIntervalExtensionsTest : AbstractJavaTimesTest() {

    companion object : KLogging()

    @Test
    fun `build year sequence`() {
        val start = nowZonedDateTime()
        val end = start + 42.yearPeriod()
        val interval = temporalIntervalOf(start, end)
        val startYear = start.year

        interval.years(1).take(3).map { it.year }.toList() shouldEqual listOf(startYear, startYear + 1, startYear + 2)
        interval.years(2).take(3).map { it.year }.toList() shouldEqual listOf(startYear, startYear + 2, startYear + 4)
    }

    @Test
    fun `build month sequece`() {
        val start = nowZonedDateTime().startOfYear()
        val end = start + 42.monthPeriod()
        val interval = temporalIntervalOf(start, end)
        val startMonth = start.month

        interval.months(1).take(3).map { it.month }.toList() shouldEqual listOf(startMonth, startMonth + 1, startMonth + 2)
        interval.months(2).take(3).map { it.month }.toList() shouldEqual listOf(startMonth, startMonth + 2, startMonth + 4)
    }

    @Test
    fun `build day sequence`() {
        val start = nowZonedDateTime().startOfYear()
        val end = start + 42.days()
        val interval = temporalIntervalOf(start, end)
        val startDay = start.dayOfYear

        interval.days(1).take(3).map { it.dayOfYear }.toList() shouldEqual listOf(startDay, startDay + 1, startDay + 2)
        interval.days(2).take(3).map { it.dayOfYear }.toList() shouldEqual listOf(startDay, startDay + 2, startDay + 4)
    }

    @Test
    fun `build hour sequence`() {
        val start = nowZonedDateTime()
        val end = start + 42.hours()
        val interval = temporalIntervalOf(start, end)

        interval.hours(1).take(24).toList().size shouldEqual 24
        interval.hours(2).take(12).toList().size shouldEqual 12
    }

    @Test
    fun `build minutes sequence`() {
        val start = nowZonedDateTime()
        val end = start + 42.minutes()
        val interval = temporalIntervalOf(start, end)

        interval.minutes(1).take(24).toList().size shouldEqual 24
        interval.minutes(2).take(12).toList().size shouldEqual 12
    }
}