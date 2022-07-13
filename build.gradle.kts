import com.google.cloud.tools.gradle.appengine.appyaml.AppEngineAppYamlExtension

val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val koin_version: String by project

plugins {
    application
    kotlin("jvm") version "1.6.21"
    id("com.github.johnrengelman.shadow") version "7.0.0"
    id("com.google.cloud.tools.appengine") version "2.4.2"
}

group = "com.muchbeer"
version = "0.0.1"
application {
    mainClass.set("io.ktor.server.netty.EngineMain")
  //  mainClass.set("com.muchbeer.ApplicationKt")
    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
    maven { setUrl("https://jitpack.io") }
}

configure<AppEngineAppYamlExtension> {
    stage {
        setArtifact("build/libs/${project.name}-0.0.1-all.jar")
    }
    deploy {
        version = "GCLOUD_CONFIG"
        projectId = "GCLOUD_CONFIG"
    }
}

dependencies {
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
  implementation("ch.qos.logback:logback-classic:$logback_version")
  //  implementation("com.google.cloud:google-cloud-logging-logback:$gce_logback_version")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

    implementation ("io.insert-koin:koin-logger-slf4j:$koin_version")
    implementation("io.insert-koin:koin-ktor:$koin_version")

      implementation ("io.ktor:ktor-server-content-negotiation:$ktor_version")
     implementation ("io.ktor:ktor-serialization-jackson:$ktor_version")

    implementation ("mysql:mysql-connector-java:8.0.29")
    implementation ("org.ktorm:ktorm-core:3.5.0")
    implementation ("org.ktorm:ktorm-support-mysql:3.5.0")

    implementation("io.ktor:ktor-server-call-logging:$ktor_version")

    implementation("io.ktor:ktor-server-default-headers:$ktor_version")
    //Africastalking dependency
  //  implementation ("com.github.AfricasTalkingLtd.africastalking-java:core:3.4.8")

}