package com.github.debop.javatimes.ranges.reactive


import com.github.debop.javatimes.AbstractJavaTimesTest
import com.github.debop.javatimes.nowInstant
import com.github.debop.javatimes.plus
import com.github.debop.javatimes.ranges.dateProgressionOf
import com.github.debop.javatimes.ranges.temporalProgressionOf
import com.github.debop.javatimes.seconds
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import mu.KLogging
import org.amshove.kluent.shouldEqualTo
import org.junit.jupiter.api.Test
import java.util.Date

@ExperimentalCoroutinesApi
class ReactiveRangesTest : AbstractJavaTimesTest() {

    companion object : KLogging()

    @Test
    fun `date progression to Flowable`() = runBlocking<Unit>(Dispatchers.Default) {

        val start = Date()
        val end = start + 42.seconds()
        val progression = dateProgressionOf(start, end, 5.seconds())

        val flow = progression.toFlowable()

        flow.blockingSubscribe {
            logger.debug { "produce date=$it" }
        }

        val count = flow.count().blockingGet()
        count shouldEqualTo 42 / 5 + 1
    }

    @Test
    fun `TemporalProgress to Flowable`() = runBlocking<Unit>(Dispatchers.Default) {

        val start = nowInstant()
        val end = start + 42.seconds()
        val progression = temporalProgressionOf(start, end, 5.seconds())

        val flow = progression.toFlowable()

        flow.blockingSubscribe {
            logger.debug { "produce instant=$it" }
        }
        val count = flow.count().blockingGet()
        count shouldEqualTo 42 / 5 + 1
    }
}