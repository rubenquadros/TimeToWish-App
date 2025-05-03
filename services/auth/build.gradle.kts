import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.ksp)
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
            baseName = "auth"
            isStatic = true
        }
    }

    cocoapods {
        summary = "This is the auth module"
        homepage = "Link to the Auth Module homepage"
        ios.deploymentTarget = "16.0"
        version = "1.0"
        podfile = project.file("../../iosApp/Podfile")

        pod("GoogleSignIn") {
            version = "7.0.0"
        }

        framework {
            baseName = "auth"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.google.credentials)
            implementation(libs.google.credentials.play.services.auth)
            implementation(libs.google.credentials.id)
        }

        commonMain.dependencies {
            implementation(libs.koin.core)
            implementation(libs.koin.annotations)
            commonMain.configure { kotlin.srcDirs("build/generated/ksp/metadata/commonMain/kotlin") }

            //core
            implementation(projects.foundation.core)
        }
    }
}

// KSP Tasks
dependencies {
    add("kspCommonMainMetadata", libs.koin.ksp.compiler)
}

// WORKAROUND: ADD this dependsOn("kspCommonMainKotlinMetadata") instead of above dependencies
tasks.withType<KotlinCompilationTask<*>>().configureEach {
    if (name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}

afterEvaluate {
    tasks.filter {
        it.name.contains("SourcesJar", true)
    }.forEach {
        it.dependsOn("kspCommonMainKotlinMetadata")
    }
}

ksp {
    arg("KOIN_CONFIG_CHECK","true")
    arg("KOIN_DEFAULT_MODULE","false")
}

android {
    namespace = "io.github.rubenquadros.timetowish.services.auth"
    compileSdk = 35
    defaultConfig {
        minSdk = 26
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
