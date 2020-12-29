plugins {
    kotlin("jvm") version "1.4.10"
}

group = "com.actualadam.aoc"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    val arrowVersion = "0.11.0"
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("script-runtime"))
    implementation("io.arrow-kt:arrow-core:$arrowVersion")
    implementation("io.arrow-kt:arrow-syntax:$arrowVersion")
}
