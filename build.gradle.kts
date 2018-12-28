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

import com.jfrog.bintray.gradle.BintrayExtension
import io.gitlab.arturbosch.detekt.detekt
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformJvmPlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    base
    kotlin("jvm") version "1.3.11" apply false
    id("io.gitlab.arturbosch.detekt") version "1.0.0-RC12" apply false

    id("org.jetbrains.dokka") version "0.9.17" apply false
    id("com.jfrog.bintray") version "1.8.4" apply false
}

allprojects {
    val kodaTime: String by extra
    val snapshot: String by extra

    group = "com.github.debop"
    version = kodaTime + snapshot

    repositories {
        mavenCentral()
        jcenter()
    }
}

dependencies {
    subprojects.forEach {
        archives(it)
    }
}

subprojects {
    apply {
        plugin("kotlin")
        plugin("io.gitlab.arturbosch.detekt")
        plugin("jacoco")

        plugin("org.jetbrains.dokka")
        plugin("maven-publish")
        plugin("com.jfrog.bintray")
    }

    val sourceSets = project.the<SourceSetContainer>()

    val sourcesJar by tasks.creating(Jar::class) {
        from(sourceSets["main"].allSource)
        classifier = "sources"
    }

    // Configure existing Dokka task to output HTML to typical Javadoc directory
    tasks.withType<DokkaTask> {
        outputFormat = "html"
        outputDirectory = "$buildDir/javadoc"
    }

    // Create dokka Jar task from dokka task output
    val dokkaJar by tasks.creating(Jar::class) {
        group = JavaBasePlugin.DOCUMENTATION_GROUP
        description = "Assembles Kotlin docs with Dokka"
        classifier = "javadoc"
        // dependsOn(tasks.dokka) not needed; dependency automatically inferred by from(tasks.dokka)
        from(tasks["dokka"])
    }

    tasks.withType<Test> {
        useJUnitPlatform()

        testLogging {
            events("FAILED")
        }

        maxParallelForks = Runtime.getRuntime().availableProcessors()
        setForkEvery(1L)
    }

    detekt {
        description = "Runs a failfast detekt build."

        input = files("src/main/kotlin")
        config = files("../detekt.yml")
        filters = ".*/build/.*"

        reports {
            xml.enabled = false
            html.enabled = true
        }
    }

    configure<PublishingExtension> {
        publications {
            register(project.name, MavenPublication::class) {
                from(components["java"])
                artifact(sourcesJar)
                artifact(dokkaJar)
                groupId = project.group as String
                artifactId = project.name
                version = project.version as String
            }
        }
    }

    // bintray
    configure<BintrayExtension> {
        user = findProperty("BINTRAY_USER") as? String
        key = findProperty("BINTRAY_KEY") as? String
        setPublications(project.name)
        publish = true
        pkg.apply {
            repo = "maven"
            name = "Koda Time"
            desc = "The modelling for success/failure of operations in Kotlin"
            userOrg = "debop"
            websiteUrl = "https://github.com/debop/koda-time"
            vcsUrl = "https://github.com/debop/koda-time"
            setLicenses("Apache-2.0")
            version.apply {
                name = project.version as String
            }
        }
    }

    // jacoco
    configure<JacocoPluginExtension> {
        toolVersion = extra.get("jacoco") as String
    }

    tasks.withType<JacocoReport> {
        reports {
            html.isEnabled = true
            xml.isEnabled = true
            csv.isEnabled = false
        }
    }

    tasks["clean"].doLast {
        delete("./.project")
        delete("./out")
        delete("./bin")
    }
}
