package io.github.rubenquadros.timetowish.conventions

import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class MultiplatformModulePlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val libs = getCatalog()

        with(plugins) {
            apply(libs.findPlugin("kotlinMultiplatform").get().get().pluginId)
            apply(libs.findPlugin("androidLibrary").get().get().pluginId)
        }

        extensions.configure<KotlinMultiplatformExtension> {
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
                    baseName = moduleName
                    isStatic = true
                    binaryOption("bundleId", moduleName)
                }
            }
        }

        extensions.configure<LibraryExtension> {
            namespace = "io.github.rubenquadros.timetowish.$modulePackage"
            compileSdk = libs.findVersion("android.compileSdk").get().requiredVersion.toInt()
            defaultConfig {
                minSdk = libs.findVersion("android.minSdk").get().requiredVersion.toInt()
            }
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }

            buildTypes {
                getByName("release") {
                    isMinifyEnabled = false
                    signingConfig = signingConfigs.findByName("release")
                }

                getByName("debug") {
                    signingConfig = signingConfigs.getByName("debug")
                }
            }
        }
    }
}