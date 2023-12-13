
buildscript {

    repositories {
        google()
        mavenCentral()
    }
    dependencies {

        classpath("com.android.tools.build:gradle:8.1.4")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.30")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.44")
        classpath ("com.google.gms:google-services:4.4.0")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    kotlin("kapt") version "1.8.10"
    id ("com.android.library") version "8.0.1" apply false
    id("com.android.application") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.google.dagger.hilt.android") version "2.48.1" apply false
}