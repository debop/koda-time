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

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

tasks.withType<KotlinCompile> {
    sourceCompatibility = "1.8"
    kotlinOptions.jvmTarget = "1.8"
}

dependencies {

    implementation(kotlin("stdlib"))

    compileOnly("io.reactivex.rxjava2:rxjava:${extra.get("rxjava2")}")
    compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-rx2:${extra.get("coroutines")}")

    testImplementation("io.reactivex.rxjava2:rxjava:${extra.get("rxjava2")}")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-rx2:${extra.get("coroutines")}")

    compile("io.github.microutils:kotlin-logging:1.6.22")
    compile("org.slf4j:slf4j-api:1.7.25")
    testImplementation("ch.qos.logback:logback-classic:1.2.3")

    testImplementation("org.junit.jupiter:junit-jupiter-api:${extra.get("junitJupiter")}")
    testImplementation("org.junit.jupiter:junit-jupiter-params:${extra.get("junitJupiter")}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${extra.get("junitJupiter")}")

    testImplementation("org.amshove.kluent:kluent:1.45")
}