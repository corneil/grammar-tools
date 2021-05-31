import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.32"
    `maven-publish`
}

group = "org.jetbrains.kotlin.spec.grammar.tools"
version = "0.1-OPEN-JUMPCO"

val jar: Jar by tasks

repositories {
    mavenLocal()
    mavenCentral()
}
val fatjar by configurations.creating

dependencies {
    compileOnly(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlin.spec.grammar:kotlin-grammar-parser:0.1-OPEN-JUMPCO")
    fatjar("org.jetbrains.kotlin.spec.grammar:kotlin-grammar-parser:0.1-OPEN-JUMPCO")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

jar.from(configurations["fatjar"].files.map { if (it.isDirectory) it else zipTree(it) })
