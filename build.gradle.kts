import com.jfrog.bintray.gradle.BintrayExtension
import io.gitlab.arturbosch.detekt.detekt
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformJvmPlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        jcenter()
        mavenCentral()
    }

    dependencies {
        classpath(kotlin("gradle-plugin", extra.get("kotlin") as String))
    }
}

plugins {
    id("com.jfrog.bintray") version ("1.8.4") apply (false)
    id("org.jetbrains.dokka") version ("0.9.17") apply (false)
    id("io.gitlab.arturbosch.detekt") version ("1.0.0-RC12") apply (false)
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

subprojects {
    apply {
        plugin<KotlinPlatformJvmPlugin>()

        plugin("io.gitlab.arturbosch.detekt")
        plugin("jacoco")

        plugin("org.jetbrains.dokka")
        plugin("maven-publish")
        plugin("com.jfrog.bintray")
    }

    val sourceSets = project.the<SourceSetContainer>()

    val sourcesJar by tasks.registering(Jar::class) {
        from(sourceSets["main"].allSource)
        classifier = "sources"
    }

    val doc by tasks.creating(Javadoc::class) {
        isFailOnError = false
        source = sourceSets["main"].allJava
    }

    val javadocJar by tasks.creating(Jar::class) {
        dependsOn(doc)
        from(doc)

        classifier = "javadoc"
    }

    tasks.withType<Test> {
        useJUnitPlatform()
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
                artifact(sourcesJar.get())
                artifact(javadocJar)
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
            setLicenses("Apache 2.0")
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
}