import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

tasks.withType<KotlinCompile> {
    sourceCompatibility = "1.6"
    kotlinOptions.jvmTarget = "1.6"
}

dependencies {

    implementation(kotlin("stdlib"))

    implementation("joda-time:joda-time:${extra.get("jodatime")}")

    implementation("io.reactivex.rxjava2:rxjava:${extra.get("rxjava2")}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-rx2:${extra.get("coroutines")}")

    implementation("io.github.microutils:kotlin-logging:1.6.22")
    testImplementation("ch.qos.logback:logback-classic:1.2.3")

    testImplementation("org.junit.jupiter:junit-jupiter-api:${extra.get("junitJupiter")}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${extra.get("junitJupiter")}")
}

