# kava-time

[![Build Status](https://travis-ci.org/debop/koda-time.png)](https://travis-ci.org/debop/koda-time) 
[![Download](https://api.bintray.com/packages/debop/maven/joda-time/images/download.svg)](https://bintray.com/debop/maven/joda-time/_latestVersion)

Extension methods for [Joda-time](https://www.joda.org/joda-time/) support JVM 1.6

## Features

### Support arithmetic operation (+,-,*,/)

Easy to express manipulation of times 

```kotlin

    val start = now() 
    val end = current + 5.minutes()       // after 5 minutes
    val interval = start .. end           // TemporalInterval (start ~ endInclusive)
```

### Period types

#### Temporal Ranges 

Various range expression (Date, Time, Timestamp, Temporal ...)

* DateRange
* TimeRange
* TimestampRange
* TemporalRange 
    * LocalDateRnage
    * LocalTimeRange
    * LocalDateTimeRange
    * OffsetDateTimeRange
    * ZonedDateTimeRange
    * InstantRange

```kotlin
    val start = Date()
    val end = start + 5.days()
    val period = DateRange.fromClosedRange(start, end) // changed to start .. end
```

#### Extension methods for Ranges

* Chunk

```kotlin
@Test
open fun `chunk in week`() {
    val range = temporalRangeOf(start, (start + 5.weeks()) as T)

    val chunks = range.chunkWeek(3).toList()
    assertEquals(2, chunks.size)
    assertChunkInRange(range, chunks)
}
```

#### Interval

Class for Java 8 times that performs functions such as `Interval` of [joda-time](http://joda.org)

#### Extension methods for Interval

Also provide sequence, chunk, windowed, zipWithNext method for various interval

* Sequence 
```kotlin
@Test
fun `build day sequence`() {
   val start = nowZonedDateTime()
   val end = start + 42.days()

   val interval = temporalIntervalOf(start, end)

   val startDay = start.dayOfYear
   logger.debug { "startDay=$startDay" }

   assertEquals(listOf(startDay, startDay + 1, startDay + 2),
                interval.days(1).take(3).map {
                    logger.debug { "produce $it, dayOfYear=${it.dayOfYear}" }
                    it.dayOfYear
                }.toList())

   assertEquals(listOf(startDay, startDay + 2, startDay + 4),
                interval.days(2).take(3).map {
                    logger.debug { "produce $it, dayOfYear=${it.dayOfYear}" }
                    it.dayOfYear
                }.toList())
}
```

* Chunk
```kotlin
@Test
fun `chunk week and aggregate`() {
    val start = nowZonedDateTime().startOfWeek()
    val endExclusive = start + 5.weekPeriod()

    val interval = start..endExclusive

    val chunks = interval.chunkWeek(3)
        .map { weeks -> weeks.first()..weeks.last() }
        .toList()

    assertEquals(2, chunks.size)
    chunks.forEach {
        assertTrue(it in interval)
    }
}
```

* Windowed
```kotlin
@Test
fun `windowed days`() {
    val start = nowZonedDateTime().startOfDay()
    val endExclusive = start + 5.days()

    val interval = start..endExclusive

    logger.debug { "day interval=$interval" }

    val windowed = interval.windowedDay(3, 2)
    windowed.forEachIndexed { index, items ->
        logger.debug { "index=$index, items=$items" }
        assertTrue { items.first() in interval }
        assertTrue { items.last() in interval }
    }
    assertEquals(3, windowed.count())

    assertFailsWith<IllegalArgumentException> {
        interval.windowedDay(-1, 2)
    }
    assertFailsWith<IllegalArgumentException> {
        interval.windowedDay(1, -2)
    }
}
```


## Setup

#### Gradle

```
repository {
    jcenter()     
}
dependencies {
    compile "com.github.debop:koda-time:2.0.0"
}
``` 

#### Maven

add repository

```xml
<repositories>
    <repository>
        <id>jcenter</id>
        <url>http://jcenter.bintray.com</url>
    </repository>
</repositories>
```

add dependency

```xml
<dependency>
  <groupId>com.github.debop</groupId>
  <artifactId>koda-time</artifactId>
  <version>2.0.0</version>
</dependency>
```


