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
}

group = "one.atpc"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    api(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.3.0")
    implementation("org.jetbrains:annotations:16.0.3")
    // TODO Hide SwingX with implementation (all the SwingX functionality should be assimilated into Fusion)
    api("org.swinglabs.swingx:swingx-core:1.6.5-1")

    testCompile("junit", "junit", "4.12")
    testImplementation("org.reflections:reflections:0.9.11")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

val kotlinCompilerArgs = arrayOf("-Xuse-experimental=kotlin.ExperimentalUnsignedTypes")

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
    kotlinOptions.freeCompilerArgs += kotlinCompilerArgs
}