// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    id("org.jetbrains.kotlin.kapt") version "2.0.20"
    id("com.google.dagger.hilt.android") version "2.41" apply false
}