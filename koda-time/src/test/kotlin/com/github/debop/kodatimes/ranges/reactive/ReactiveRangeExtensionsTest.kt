package com.github.debop.kodatimes.ranges.reactive

import com.github.debop.kodatimes.AbstractKodaTimesTest
import com.github.debop.kodatimes.plus
import com.github.debop.kodatimes.ranges.DateTimeProgression
import com.github.debop.kodatimes.ranges.InstantProgression
import com.github.debop.kodatimes.seconds
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import mu.KLogging
import org.joda.time.DateTime
import org.joda.time.Duration
import org.junit.Test
import kotlin.test.assertEquals

/**
 * ReactiveRangeExtensionsTest
 *
 * @author debop
 * @since 18. 12. 13
 */
@ExperimentalCoroutinesApi
class ReactiveRangeExtensionsTest : AbstractKodaTimesTest() {

    companion object : KLogging()

    @Test
    fun `datetime progression to flowable`() = runBlocking(Dispatchers.Default) {

        val start = DateTime.now()
        val end = start + 42.seconds()

        val progression = DateTimeProgression.fromClosedRange(start, end, Duration.standardSeconds(5))

        val flow = progression.toFlowable()

        flow.blockingSubscribe {
            logger.debug { "produce date=$it" }
        }

        val count = flow.count().blockingGet()
        assertEquals(42 / 5 + 1, count)
    }

    @Test
    fun `instant progression to flowable`() = runBlocking(Dispatchers.Default) {

        val start = DateTime.now().toInstant()
        val end = start + 42.seconds()

        val progression = InstantProgression.fromClosedRange(start, end, Duration.standardSeconds(5))

        val flow = progression.toFlowable()

        flow.blockingSubscribe {
            logger.debug { "produce date=$it" }
        }

        val count = flow.count().blockingGet()
        assertEquals(42 / 5 + 1, count)
    }
}