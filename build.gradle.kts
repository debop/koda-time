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

import io.gitlab.arturbosch.detekt.detekt
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformJvmPlugin

plugins {
    base
    `maven-publish`
    kotlin("jvm") version Versions.kotlin apply false

    id(BuildPlugins.nebula_release) version BuildPlugins.Versions.nebula_release
    id(BuildPlugins.detekt) version BuildPlugins.Versions.detekt apply false
    id(BuildPlugins.dokka) version BuildPlugins.Versions.dokka apply false
    id(BuildPlugins.dependency_management) version BuildPlugins.Versions.dependencyManagement
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
        plugin<JavaLibraryPlugin>()
        plugin<KotlinPlatformJvmPlugin>()

        plugin("io.gitlab.arturbosch.detekt")
        plugin("org.jetbrains.dokka")
        plugin("io.spring.dependency-management")
        plugin("jacoco")
        plugin("nebula.nebula-release")
        plugin("maven-publish")
        //        plugin("com.jfrog.bintray")
    }

    rootProject.tasks["release"].finalizedBy(tasks["publish"])

    val sourceSets = project.the<SourceSetContainer>()

    val sourcesJar by tasks.creating(Jar::class) {
        from(sourceSets["main"].allSource)
        archiveClassifier.set("sources")
    }

    // Configure existing Dokka task to output HTML to typical Javadoc directory
    val dokka by tasks.getting(DokkaTask::class) {
        outputFormat = "html"
        outputDirectory = "$buildDir/javadoc"
    }

    // Create dokka Jar task from dokka task output
    val dokkaJar by tasks.creating(Jar::class) {
        group = JavaBasePlugin.DOCUMENTATION_GROUP
        description = "Assembles Kotlin docs with Dokka"
        archiveClassifier.set("javadoc")
        // dependsOn(tasks.dokka) not needed; dependency automatically inferred by from(tasks.dokka)
        from(dokka)
    }

    tasks.withType<Test> {
        useJUnitPlatform()

        testLogging {
            events("FAILED")
        }

        //        maxParallelForks = Runtime.getRuntime().availableProcessors()
        //        setForkEvery(1L)
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

    // `./gradlew publish` 를 수행하면 maven local 에 publish 됩니다.
    // `./gradlew devSnapshot` 을 수행하면 snapshot version 을 mavenLocal에 publish 됩니다.

    publishing {
        publications {
            register("Maven", MavenPublication::class) {
                from(components["java"])
                artifact(sourcesJar)
                artifact(dokkaJar)
                groupId = project.group as String
                artifactId = project.name
                version = project.version as String
            }
        }
        repositories {
            mavenLocal()
        }
    }

    // bintray
    //    configure<BintrayExtension> {
    //        user = findProperty("BINTRAY_USER") as? String
    //        key = findProperty("BINTRAY_KEY") as? String
    //        setPublications(project.name)
    //        publish = true
    //        pkg.apply {
    //            repo = "maven"
    //            name = "Koda Time"
    //            desc = "The modelling for success/failure of operations in Kotlin"
    //            userOrg = "debop"
    //            websiteUrl = "https://github.com/debop/koda-time"
    //            vcsUrl = "https://github.com/debop/koda-time"
    //            setLicenses("Apache-2.0")
    //            version.apply {
    //                name = project.version as String
    //            }
    //        }
    //    }

    // jacoco
    configure<JacocoPluginExtension> {
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

    dependencyManagement {
        dependencies {
            dependency(Libraries.kotlin_stdlib)
            dependency(Libraries.kotlin_stdlib_jdk7)
            dependency(Libraries.kotlin_stdlib_jdk8)
            dependency(Libraries.kotlin_reflect)
            dependency(Libraries.kotlin_test)
            dependency(Libraries.kotlin_test_junit5)

            dependency(Libraries.kotlinx_coroutines_core)
            dependency(Libraries.kotlinx_coroutines_jdk7)
            dependency(Libraries.kotlinx_coroutines_jdk8)
            dependency(Libraries.kotlinx_coroutines_reactor)
            dependency(Libraries.kotlinx_coroutines_rx2)

            dependency(Libraries.junit_jupiter)
            dependency(Libraries.junit_jupiter_api)
            dependency(Libraries.junit_jupiter_engine)
            dependency(Libraries.junit_jupiter_params)

            dependency(Libraries.junit_platform_commons)
            dependency(Libraries.junit_platform_engine)

            dependency(Libraries.kluent)
            dependency(Libraries.assertj_core)
        }
    }

    dependencies {
        val api by configurations
        val compile by configurations
        val implementation by configurations
        val testImplementation by configurations
        val testRuntimeOnly by configurations

        implementation(Libraries.kotlin_stdlib)
        implementation(Libraries.kotlin_reflect)

        implementation(Libraries.kotlinx_coroutines_jdk8)

        api(Libraries.kotlin_logging)
        api(Libraries.slf4j_api)
        testImplementation(Libraries.logback)

        testImplementation(Libraries.junit_jupiter)
        testRuntimeOnly(Libraries.junit_platform_engine)
        testImplementation(Libraries.kluent)
    }
}
