# koda-time [![Build Status](https://travis-ci.org/debop/koda-time.png)](https://travis-ci.org/debop/koda-time)

[Joda Time](http://joda.org) Extensions in Kotlin

joda-time 의 `DateTime` 에 대한 사칙연산 (+,-,*,/) 를 Number 수형과 유사하게 표현할 수 있도록
kotlin 의 operatator overloading 을 제공합니다

```kotlin

    val now = DateTime.now()
    val start = now + 5.minutes()
    val end = now + 15.minutes()
    val interval = start .. end
```

`DateTime`, `Duration`, `Instant`, `Period` 등에 대한 여러가지 Extension Function 을 제공합니다.

### 사용

##### Maven

Maven 사용 시

```xml
<dependency>
  <groupId>com.github.debop</groupId>
  <artifactId>koda-time</artifactId>
  <version>0.1</version>
</dependency>
```

##### Gradle
Gradle 사용 시
```
dependencies {
    compile "com.github.debop:koda-time:0.1"
}
```

### 빌드

gradle 빌드 툴을 사용합니다.

```
$ ./gradlew clean install
```