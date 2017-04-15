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

import com.beust.kobalt.*
import com.beust.kobalt.plugin.packaging.assemble
import com.beust.kobalt.plugin.publish.bintray
import org.apache.maven.model.*

object Versions {
  const val kotlin = "1.1.1"
}

val bs = buildScript {
  repos(localMaven())
  repos("http://jcenter.bintray.com")
}

val `koda-time` = project {

  name = "koda-time"
  group = "com.github.debop"
  artifactId = name
  version = "1.1.2-SNAPSHOT"

  sourceDirectories {
    path("src/main/kotlin")
  }
  sourceDirectoriesTest {
    path("src/test/kotlin")
  }

  dependencies {
    compile("org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}")
    compile("joda-time:joda-time:2.9.9")
    compile("org.joda:joda-convert:1.8.1")
  }

  dependenciesTest {
    compile("org.slf4j:slf4j-api:1.7.24")
    compile("ch.qos.logback:logback-classic:1.2.3")
    compile("junit:junit:4.12")
    compile("org.jetbrains.kotlin:kotlin-test:${Versions.kotlin}")
    compile("org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}")
  }

  assemble {
    jar {
      fatJar = false
    }
  }

  bintray {
    publish = true
    sign = true
  }

  pom = Model().apply {
    name = project.name
    description = "joda-time extentions for Kotlin"
    url = "http://github.com/debop/koda-time"
    licenses = listOf(License().apply {
      name = "Apache 2.0"
      url = "http://www.apache .org/licenses/LICENSE-2.0"
    })
    scm = Scm().apply {
      url = "http://github.com/debop/koda-time"
      connection = "https://github.com/debop/koda-time.git"
      developerConnection = "git@github.com:debop/koda-time.git"
    }
    developers = listOf(Developer().apply {
      name = "Sunghyouk Bae"
      email = "sunghyouk.bae@gmail.com"
    })
  }

}