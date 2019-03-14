/*
 * Copyright (c) 2019  Thomas Orlando, ATPC
 *
 * This file is part of Fusion.
 *
 * Fusion is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Fusion is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Fusion.  If not, see <https://www.gnu.org/licenses/>.
 */

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-library`
    kotlin("jvm") version "1.3.11"
    id("org.jetbrains.dokka") version "0.9.17"
    `maven-publish`
}

group = "one.atpc"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

tasks {
    "test"(Test::class) {
        // Enable JUnit 5 support
        useJUnitPlatform()
    }
}

dependencies {
    api(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.3.0")
    implementation("org.jetbrains:annotations:16.0.3")
    // TODO Hide SwingX with implementation (all the SwingX functionality should be assimilated into Fusion)
    api("org.swinglabs.swingx:swingx-core:1.6.5-1")

    // To support legacy test code (JUnit 4)
    testCompile("junit", "junit", "4.12")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:5.4.0")

    // kotlintest
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.2.1")
    
    testImplementation("org.reflections:reflections:0.9.11")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

val RUN_DEMO = "runDemo"
val runDemo = task(RUN_DEMO, type = JavaExec::class) {
    group = "run"
    description = "Runs the demo classes."

    dependsOn("build")
    // Only build demos if this task is running
    main = if (project.gradle.startParameter.taskNames.contains(RUN_DEMO)) {
        sourceSets["main"].java {
            srcDir("src/demo/kotlin")
        }
        
        project.property("demoClass") as String
    }
    else {
        "<<[undefined]>>"
    }

    classpath = sourceSets["main"].runtimeClasspath
}


val sourcesJar = task("sourcesJar", type = Jar::class) {
    classifier = "sources"
    from(sourceSets.getByName("main").allSource)
}


configure<PublishingExtension> {
    publications {
        create<MavenPublication>("maven") {
            from(components.getByName("java"))
            artifact(sourcesJar)
            pom {
                name.set("Fusion")
                description.set("Blablabla... (TODO)")
                url.set("https://github.com/atpc/fusion")
                licenses {
                    license {
                        name.set("GNU Lesser General Public License, Version 3.0")
                        url.set("https://www.gnu.org/licenses/#LGPL")
                    }
                }
                developers {
                    developer {
                        id.set("thomorl")
                        name.set("Thomas Orlando")
                        email.set("thomas.orlando@atpc.one")
                    }
                    developer {
                        id.set("atpc")
                        name.set("ATPC")
                        email.set("contact@atpc.one")
                    }
                }
            }
        }
    }
}


val kotlinCompilerArgs = arrayOf("-Xuse-experimental=kotlin.ExperimentalUnsignedTypes")

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
    kotlinOptions.freeCompilerArgs += kotlinCompilerArgs
}