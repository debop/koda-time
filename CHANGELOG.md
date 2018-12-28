# koda-times change Logs

# 2.0.0

> not specified

* Split koda-time (joda-time extensios), java-time (java.time extensions)
* Bump up kotlin-stdlib 1.3.11
* Bump up kotlinx-coroutines-core 1.0.1

# 1.2.2

> release 2018-03-24

* Bump up kotlin-stdlib 1.2.30
* Bump up kotlinx-coroutines 0.22.5
* add TemporalRange, TemporalProgression
* add RxJava2 Flowable convert method for DateTimeRange, InstanceRange ...
* add java 8 LocalDateTime

#1.2.1

> release 2017-11-19

* add DateTimeRange, InstantRange for joda-time
* add DateTimeProgression, InstantProgression for joda-time
* add DateRange, InstantRange for java 8 time
* add DateProgression, InstantProgression for java 8 time 

# 1.2

* add ReadableInterval.chunk(), windowed(), zipWithNext() methods
* Upgrade kotlin standard library 1.1.51
* Improve ReadableInterval.buildSequence

# 1.1.3

* Upgrade kotlin standard library 1.1.3
* Change Target JVM to 1.8
* Support Java 8 Time (see Javatimex.kt)
* Change build tool to Gradle

# 1.1.2

* Change build tool to [Kobalt](http://beust.com/kobalt) (Build system with Kotlin syntax)

# 1.1.1

* Upgrade kotlin standard library to 1.1.1
* Upgrade gradle version (3.5)
* Remove assertj-core, use kotlin test lib. 

# 1.1.0

* Add arithmatic operation for Period, Instace, LocalDateTime, LocalDate, LocalTime
* Remove DurationBuilder class
* Upgrade kotlin library to 1.1 

# 1.0.0

> First release

*   Supply joda-time DateTime extension functions