import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

tasks.withType<KotlinCompile> {
    sourceCompatibility = "1.6"
    kotlinOptions.jvmTarget = "1.6"
}

dependencies {

    implementation(kotlin("stdlib"))

    compile("joda-time:joda-time:${extra.get("jodatime")}")

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

