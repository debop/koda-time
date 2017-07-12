# koda-time 
[![Build Status](https://travis-ci.org/debop/koda-time.png)](https://travis-ci.org/debop/koda-time) 
[![Download](https://api.bintray.com/packages/debop/maven/koda-time/images/download.svg)](https://bintray.com/debop/maven/koda-time/_latestVersion)

[Joda Time](http://joda.org) Extensions in Kotlin

supply arithmetic operation (+,-,*,/) for joda-time `DateTime` in Kotlin language  

### Usage 
 
```kotlin

    val now = DateTime.now()
    val start = now + 5.minutes()
    val end = now + 15.minutes()
    val interval = start .. end
```

and supply Extension functions of `DateTime`, `Duration`, `Instance`, `Period`

### Setup

##### Maven

add dependency

```xml
<dependency>
  <groupId>com.github.debop</groupId>
  <artifactId>koda-time</artifactId>
  <version>1.2.0-SNAPSHOT</version>
</dependency>
```

add repository

```xml
<repositories>
    <repository>
        <id>jcenter</id>
        <url>http://jcenter.bintray.com</url>
    </repository>
</repositories>
```
or
```xml
<repositories>
    <repository>
        <id>debop-releases-bintray</id>
        <url>http://dl.bintray.com/debop/maven</url>
    </repository>
</repositories>
```

##### Gradle

```
repository {
    jcenter()     
}
dependencies {
    compile "com.github.debop:koda-time:1.2.0-SNAPSHOT"
}
```



### Build

build by Gradle

```
$ ./gradlew clean build
```