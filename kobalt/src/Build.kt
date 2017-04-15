import com.beust.kobalt.*
import com.beust.kobalt.plugin.packaging.assemble

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
        }
    }
}