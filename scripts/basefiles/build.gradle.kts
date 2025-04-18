import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "moduleName"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            //add your dependencies
        }
        commonTest.dependencies {
            //add your test dependencies
        }
    }
}

compose.resources {
    packageOfResClass = "modulePackage.resources"
}

android {
    namespace = "modulePackage"
    compileSdk = 35
    defaultConfig {
        minSdk = 26
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
