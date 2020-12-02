plugins {
    kotlin("jvm") version "1.4.10"
}

group = "com.actualadam.aoc"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("script-runtime"))
}
