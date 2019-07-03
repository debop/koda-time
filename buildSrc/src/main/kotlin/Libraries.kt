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

const val kotlinVersion: String = "1.3.40"

object BuildPlugins {

    object Versions {
        const val nebula_release = "10.1.2"
        const val detekt = "1.0.0-RC12"
        const val dokka = "0.9.18"
        const val dependencyManagement = "1.0.8.RELEASE"
        const val jooq = "3.0.3"
        const val protobuf = "0.8.9"
    }

    const val nebula_release = "nebula.release"
    const val detekt = "io.gitlab.arturbosch.detekt"
    const val dokka = "org.jetbrains.dokka"
    const val dependency_management = "io.spring.dependency-management"

    const val jooq = "nu.studer.jooq"
    // https://github.com/google/protobuf-gradle-plugin
    const val protobuf = "com.google.protobuf"
}

object Versions {

    const val kotlin = kotlinVersion
    const val kotlinx_coroutines = "1.2.1"

    const val spring_boot = "2.1.6.RELEASE"
    const val resilience4j = "0.16.0"
    const val vavr = "0.10.0"
    const val netty = "4.1.36.Final"

    const val grpc = "1.21.0"
    const val protobuf = "3.8.0"
    const val krotoplus = "0.4.0"
    const val avro = "1.8.2"   // 1.9.0 은 jackson-dataformat-avro 에서 아직 지원하지 않습니다.

    const val reactor = "3.2.9.RELEASE"
    const val jackson = "2.9.9"

    const val random_beans = "3.9.0"
    const val reflectasm = "1.11.9"
    const val mongo_driver = "3.10.2"
    const val lettuce = "5.1.6.RELEASE"
    const val redisson = "3.11.0"

    const val hibernate = "5.4.3.Final"
    const val querydsl = "4.2.1"
    const val jooq = "3.11.11"

    const val kotlin_logging = "1.6.26"
    const val slf4j = "1.7.26"
    const val logback = "1.2.3"

    const val micrometer = "1.1.4"
    const val cache2k = "1.2.2.Final"

    const val koin = "2.0.1"

    const val byte_buddy = "1.9.13"

    const val junit_jupiter = "5.4.2"
    const val junit_platform = "1.4.2"
    const val assertj_core = "3.12.2"
    const val mockk = "1.9.3"
    const val mockito = "2.28.2"
    const val testcontainers = "1.11.3"
}

object Libraries {

    const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion"
    const val kotlin_stdlib_jdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion"
    const val kotlin_stdlib_jdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion"
    const val kotlin_reflect = "org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion"
    const val kotlin_test = "org.jetbrains.kotlin:kotlin-test:$kotlinVersion"
    const val kotlin_test_junit5 = "org.jetbrains.kotlin:kotlin-test-junit5:$kotlinVersion"

    const val kotlinx_coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinx_coroutines}"
    const val kotlinx_coroutines_jdk7 = "org.jetbrains.kotlinx:kotlinx-coroutines-jdk7:${Versions.kotlinx_coroutines}"
    const val kotlinx_coroutines_jdk8 = "org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:${Versions.kotlinx_coroutines}"
    const val kotlinx_coroutines_reactor = "org.jetbrains.kotlinx:kotlinx-coroutines-reactor:${Versions.kotlinx_coroutines}"
    const val kotlinx_coroutines_rx2 = "org.jetbrains.kotlinx:kotlinx-coroutines-rx2:${Versions.kotlinx_coroutines}"

    // javax api
    const val activation_api = "javax.activation:javax.activation-api:1.2.0"
    const val annotation_api = "javax.annotation:javax.annotation-api:1.3.2"
    const val cache_api = "javax.cache:cache-api:1.1.1"
    const val javax_inject = "javax.inject:javax.inject:1"
    const val validation_api = "javax.validation:validation-api:2.0.1.Final"

    // Apache Commons
    const val commons_collections4 = "org.apache.commons:commons-collections4:4.3"
    const val commons_lang3 = "org.apache.commons:commons-lang3:3.9"
    const val commons_compress = "org.apache.commons:commons-compress:1.18"
    const val commons_codec = "commons-codec:commons-codec:1.12"
    const val commons_csv = "org.apache.commons:commons-csv:1.7"
    const val commons_math3 = "org.apache.commons:commons-math3:3.6.1"
    const val commons_pool2 = "org.apache.commons:commons-pool2:2.6.2"
    const val commons_text = "org.apache.commons:commons-text:1.6"
    const val commons_exec = "org.apache.commons:commons-exec:1.3"
    const val commons_io = "commons-io:commons-io:2.6"
    const val commons_validator = "commons-validator:commons-validator:1.6"

    const val kotlin_logging = "io.github.microutils:kotlin-logging:${Versions.kotlin_logging}"
    const val slf4j_api = "org.slf4j:slf4j-api:${Versions.slf4j}"
    const val slf4j_simple = "org.slf4j:slf4j-simple:${Versions.slf4j}"
    const val slf4j_log4j12 = "org.slf4j:slf4j-log4j12:${Versions.slf4j}"
    const val jcl_over_slf4j = "org.slf4j:jcl_over_slf4j:${Versions.slf4j}"
    const val jul_over_slf4j = "org.slf4j:jul_over_slf4j:${Versions.slf4j}"
    const val log4j_over_slf4j = "org.slf4j:log4j_over_slf4j:${Versions.slf4j}"
    const val logback = "ch.qos.logback:logback-classic:${Versions.logback}"

    const val findbugs = "com.google.code.findbugs:jsr305:3.0.2"
    const val guava = "com.google.guava:guava:28.0-jre"
    const val joda_time = "joda-time:joda-time:2.10.2"

    const val fst = "de.ruedigermoeller:fst:2.57"
    const val kryo = "com.esotericsoftware:kryo:4.0.2"
    const val kryo_serializers = "de.javakaffee:kryo-serializers:0.45"

    // Resilience4j
    const val resilience4j_annotations = "io.github.resilience4j:resilience4j-annotations:${Versions.resilience4j}"
    const val resilience4j_bulkhead = "io.github.resilience4j:resilience4j-bulkhead:${Versions.resilience4j}"
    const val resilience4j_circuitbreaker = "io.github.resilience4j:resilience4j-circuitbreaker:${Versions.resilience4j}"
    const val resilience4j_core = "io.github.resilience4j:resilience4j-core:${Versions.resilience4j}"
    const val resilience4j_framework_common = "io.github.resilience4j:resilience4j-framework-common:${Versions.resilience4j}"
    const val resilience4j_micrometer = "io.github.resilience4j:resilience4j-micrometer:${Versions.resilience4j}"
    const val resilience4j_ratelimiter = "io.github.resilience4j:resilience4j-ratelimiter:${Versions.resilience4j}"
    const val resilience4j_reactor = "io.github.resilience4j:resilience4j-reactor:${Versions.resilience4j}"
    const val resilience4j_retry = "io.github.resilience4j:resilience4j-retry:${Versions.resilience4j}"
    const val resilience4j_spring = "io.github.resilience4j:resilience4j-spring:${Versions.resilience4j}"
    const val resilience4j_spring_boot2 = "io.github.resilience4j:resilience4j-spring-boot2:${Versions.resilience4j}"
    const val resilience4j_spring_boot_common = "io.github.resilience4j:resilience4j-spring-boot-common:${Versions.resilience4j}"

    // Vavr
    const val vavr = "io.vavr:vavr:${Versions.vavr}"
    const val vavr_jackson = "io.vavr:vavr-jackson:${Versions.vavr}"
    const val vavr_kotlin = "io.vavr:vavr-kotlin:${Versions.vavr}"
    const val vavr_match = "io.vavr:vavr-match:${Versions.vavr}"
    const val vavr_test = "io.vavr:vavr-test:${Versions.vavr}"

    // Netty
    const val netty_all = "io.netty:netty-all:${Versions.netty}"
    const val netty_common = "io.netty:netty-common:${Versions.netty}"
    const val netty_buffer = "io.netty:netty-buffer:${Versions.netty}"
    const val netty_codec = "io.netty:netty-codec:${Versions.netty}"
    const val netty_codec_dns = "io.netty:netty-codec-dns:${Versions.netty}"
    const val netty_codec_http = "io.netty:netty-codec-http:${Versions.netty}"
    const val netty_codec_http2 = "io.netty:netty-codec-http2:${Versions.netty}"
    const val netty_codec_socks = "io.netty:netty-codec-socks:${Versions.netty}"
    const val netty_handler = "io.netty:netty-handler:${Versions.netty}"
    const val netty_handler_proxy = "io.netty:netty-handler-proxy:${Versions.netty}"
    const val netty_resolver = "io.netty:netty-resolver:${Versions.netty}"
    const val netty_resolver_dns = "io.netty:netty-resolver-dns:${Versions.netty}"
    const val netty_transport = "io.netty:netty-transport:${Versions.netty}"
    const val netty_transport_native_epoll = "io.netty:netty-transport-native-epoll:${Versions.netty}"
    const val netty_transport_native_kqueue = "io.netty:netty-transport-native-kqueue:${Versions.netty}"

    // gRPC
    const val grpc_protobuf = "io.grpc:grpc-protobuf:${Versions.grpc}"
    const val grpc_stub = "io.grpc:grpc-stub:${Versions.grpc}"
    const val grpc_netty = "io.grpc:grpc-netty:${Versions.grpc}"
    const val grpc_netty_shaded = "io.grpc:grpc-netty-shaded:${Versions.grpc}"
    const val grpc_protoc_gen_grpc_java = "io.grpc:protoc-gen-grpc-java:${Versions.grpc}"
    const val grpc_testing = "io.grpc:grpc-testing:${Versions.grpc}"

    const val protobuf_protoc = "com.google.protobuf:protoc:${Versions.protobuf}"
    const val protobuf_java = "com.google.protobuf:protobuf-java:${Versions.protobuf}"
    const val protobuf_java_util = "com.google.protobuf:protobuf-java-util:${Versions.protobuf}"

    const val avro = "org.apache.avro:avro:${Versions.avro}"
    const val avro_ipc = "org.apache.avro:avro-ipc:${Versions.avro}"
    const val avro_ipc_netty = "org.apache.avro:avro-ipc-netty:${Versions.avro}"
    const val avro_compiler = "org.apache.avro:avro-compiler:${Versions.avro}"
    const val avro_protobuf = "org.apache.avro:avro-protobuf:${Versions.avro}"

    // kroto-plus
    const val kroto_plus_coroutines = "com.github.marcoferrer.krotoplus:kroto-plus-coroutines:${Versions.krotoplus}"
    const val kroto_plus_message = "com.github.marcoferrer.krotoplus:kroto-plus-message:${Versions.krotoplus}"
    const val kroto_plus_protoc_gen = "com.github.marcoferrer.krotoplus:protoc-gen-kroto-plus:${Versions.krotoplus}"
    const val kroto_plus_test = "com.github.marcoferrer.krotoplus:kroto-plus-test:${Versions.krotoplus}"
    const val kroto_plus_protoc_gen_grpc_coroutines = "com.github.marcoferrer.krotoplus:protoc-gen-grpc-coroutines:${Versions.krotoplus}"

    // Jackson
    const val jackson_annotations = "com.fasterxml.jackson.core:jackson-annotations:${Versions.jackson}"
    const val jackson_core = "com.fasterxml.jackson.core:jackson-core:${Versions.jackson}"
    const val jackson_databind = "com.fasterxml.jackson.core:jackson-databind:${Versions.jackson}"

    const val jackson_datatype_jsr310 = "com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${Versions.jackson}"
    const val jackson_datatype_jsr353 = "com.fasterxml.jackson.datatype:jackson-datatype-jsr353:${Versions.jackson}"
    const val jackson_datatype_jdk8 = "com.fasterxml.jackson.datatype:jackson-datatype-jdk8:${Versions.jackson}"
    const val jackson_datatype_joda = "com.fasterxml.jackson.datatype:jackson-datatype-joda:${Versions.jackson}"
    const val jackson_datatype_guava = "com.fasterxml.jackson.datatype:jackson-datatype-guava:${Versions.jackson}"

    const val jackson_dataformat_avro = "com.fasterxml.jackson.dataformat:jackson-dataformat-avro:${Versions.jackson}"
    const val jackson_dataformat_protobuf = "com.fasterxml.jackson.dataformat:jackson-dataformat-protobuf:${Versions.jackson}"
    const val jackson_dataformat_csv = "com.fasterxml.jackson.dataformat:jackson-dataformat-csv:${Versions.jackson}"
    const val jackson_dataformat_properties = "com.fasterxml.jackson.dataformat:jackson-dataformat-properties:${Versions.jackson}"
    const val jackson_dataformat_yaml = "com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:${Versions.jackson}"

    const val jackson_module_kotlin = "com.fasterxml.jackson.module:jackson-module-kotlin:${Versions.jackson}"
    const val jackson_module_paranamer = "com.fasterxml.jackson.module:jackson-module-paranamer:${Versions.jackson}"
    const val jackson_module_parameter_names = "com.fasterxml.jackson.module:jackson-module-parameter-names:${Versions.jackson}"
    const val jackson_module_scala = "com.fasterxml.jackson.module:jackson-module-scala:${Versions.jackson}"
    const val jackson_module_afterburner = "com.fasterxml.jackson.module:jackson-module-afterburner:${Versions.jackson}"

    const val gson = "com.google.code.gson:gson:2.8.5"

    // Compression
    const val snappy_java = "org.xerial.snappy:snappy-java:1.1.7.3"
    const val lz4_java = "org.lz4:lz4-java:1.3.0"

    // Reactor
    const val reactor_core = "io.projectreactor:reactor-core:${Versions.reactor}"
    const val reactor_test = "io.projectreactor:reactor-test:${Versions.reactor}"
    const val reactor_netty = "io.projectreactor.netty:reactor-netty:0.8.9.RELEASE"

    // RxJava2
    const val rxjava2 = "io.reactivex.rxjava2:rxjava:2.2.10"
    const val rxkotlin = "io.reactivex.rxjava2:rxkotlin:2.3.0"

    // Metrics
    const val micrometer_core = "io.micrometer:micrometer-core:${Versions.micrometer}"
    const val micrometer_test = "io.micrometer:micrometer-test:${Versions.micrometer}"
    const val micrometer_registry = "io.micrometer:micrometer-registry:${Versions.micrometer}"
    const val micrometer_registry_prometheus = "io.micrometer:micrometer-registry-prometheus:${Versions.micrometer}"
    const val micrometer_registry_graphite = "io.micrometer:micrometer-registry-graphite:${Versions.micrometer}"
    const val micrometer_registry_jmx = "io.micrometer:micrometer-registry-jmx:${Versions.micrometer}"

    const val latencyUtils = "org.latencyutils:LatencyUtils:2.0.3"
    const val hdrHistogram = "org.hdrhistogram:HdrHistogram:2.1.11"

    const val random_beans: String = "io.github.benas:random-beans:${Versions.random_beans}"
    const val reflectasm: String = "com.esotericsoftware:reflectasm:${Versions.reflectasm}"

    // MongoDB
    const val mongo_java_driver = "org.mongodb:mongo-java-driver:${Versions.mongo_driver}"
    const val mongo_bson = "org.mongodb:bson:${Versions.mongo_driver}"
    const val mongo_driver = "org.mongodb:mongodb-driver:${Versions.mongo_driver}"
    const val mongo_driver_async = "org.mongodb:mongodb-driver-async:${Versions.mongo_driver}"
    const val mongo_driver_core = "org.mongodb:mongodb-driver-core:${Versions.mongo_driver}"
    const val mongo_driver_reactivestreams = "org.mongodb:mongodb-driver-reactivestreams:1.11.0"

    // Redis
    const val lettuceCore = "io.lettuce:lettuce-core:${Versions.lettuce}"
    const val redisson = "org.redisson:redisson:${Versions.redisson}"
    const val redisson_spring_boot_starter = "org.redisson:redisson-spring-boot-starter:${Versions.redisson}"
    const val redisson_spring_data_21 = "org.redisson:redisson-spring-data-21:${Versions.redisson}"

    // Hibernate
    const val hibernate_core = "org.hibernate:hibernate-core:${Versions.hibernate}"
    const val hibernate_jpa_2_1_api = "org.hibernate.javax.persistence:hibernate-jpa-2.1-api:1.0.2.Final"
    const val javassist = "org.javassist:javassist:3.25.0-GA"

    // Validators
    const val hibernate_validator = "org.hibernate:hibernate-validator:6.0.17.Final"
    const val hibernate_validator_annotation_processor = "org.hibernate:hibernate-validator-annotation-processor:6.0.17.Final"

    // Expression
    const val javax_el_api = "javax.el:javax.el-api:3.0.0"
    const val javax_el = "org.glassfish:javax.el:3.0.1-b11"

    const val querydsl_apt = "com.querydsl:querydsl-apt:${Versions.querydsl}"
    const val querydsl_jpa = "com.querydsl:querydsl-jpa:${Versions.querydsl}"

    const val hikaricp = "com.zaxxer:HikariCP:3.3.1"
    const val mysqlConnectorJava = "mysql:mysql-connector-java:8.0.16"
    const val mariadbJavaClient = "org.mariadb.jdbc:mariadb-java-client:2.4.1"
    const val h2 = "com.h2database:h2:1.4.199"

    const val jooq = "org.jooq:jooq:${Versions.jooq}"
    const val jooq_checker = "org.jooq:jooq-checker:${Versions.jooq}"
    const val jooq_meta = "org.jooq:jooq-meta:${Versions.jooq}"
    const val jooq_meta_extensions = "org.jooq:jooq-meta-extensions:${Versions.jooq}"
    const val jooq_codegen = "org.jooq:jooq-codegen:${Versions.jooq}"

    const val cache2kAll = "org.cache2k:cache2k-all:${Versions.cache2k}"
    const val cache2k_spring = "org.cache2k:cache2k-spring:${Versions.cache2k}"
    const val cache2k_jcache = "org.cache2k:cache2k-jcache:${Versions.cache2k}"

    const val dagger = "com.google.dagger:dagger:2.23.1"
    const val dagger_compiler = "com.google.dagger:dagger-compiler:2.23.1"

    const val koin_core = "org.koin:koin-core:${Versions.koin}"
    const val koin_core_ext = "org.koin:koin-core-ext:${Versions.koin}"
    const val koin_test = "org.koin:koin-test:${Versions.koin}"

    const val byte_buddy = "net.bytebuddy:byte-buddy:${Versions.byte_buddy}"
    const val byte_buddy_agent = "net.bytebuddy:byte-buddy-agent:${Versions.byte_buddy}"

    const val objenesis = "org.objenesis:objenesis:3.0.1"
    const val ow2_asm = "org.ow2.asm:asm:7.1"

    // junit 5.4+ 부터는 junit-jupiter 만 있으면 됩니다.
    const val junit_jupiter = "org.junit.jupiter:junit-jupiter:${Versions.junit_jupiter}"
    const val junit_jupiter_api = "org.junit.jupiter:junit-jupiter-api:${Versions.junit_jupiter}"
    const val junit_jupiter_engine = "org.junit.jupiter:junit-jupiter-engine:${Versions.junit_jupiter}"
    const val junit_jupiter_params = "org.junit.jupiter:junit-jupiter-params:${Versions.junit_jupiter}"

    const val junit_platform_commons = "org.junit.platform:junit-platform-commons:${Versions.junit_platform}"
    const val junit_platform_engine = "org.junit.platform:junit-platform-engine:${Versions.junit_platform}"

    const val kluent = "org.amshove.kluent:kluent:1.49"
    const val assertj_core: String = "org.assertj:assertj-core:3.12.2"
    const val mockk: String = "io.mockk:mockk:${Versions.mockk}"
    const val mockito_core = "org.mockito:mockito-core:${Versions.mockito}"
    const val mockito_junit_jupiter = "org.mockito:mockito-junit-jupiter:${Versions.mockito}"
    const val mockito_kotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:2.1.0"

    const val testcontainers: String = "org.testcontainers:testcontainers:${Versions.testcontainers}"
    const val testcontainers_junit_jupiter: String = "org.testcontainers:junit-jupiter:${Versions.testcontainers}"
    const val testcontainers_mariadb: String = "org.testcontainers:mysql:${Versions.testcontainers}"
    const val testcontainers_mysql: String = "org.testcontainers:mysql:${Versions.testcontainers}"
    const val testcontainers_postgresql: String = "org.testcontainers:postgresql:${Versions.testcontainers}"

}