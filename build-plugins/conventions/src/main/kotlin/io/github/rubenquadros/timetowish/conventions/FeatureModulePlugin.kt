package io.github.rubenquadros.timetowish.conventions

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class FeatureModulePlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val libs = getCatalog()

        //apply common plugins
        with(plugins) {
            apply(libs.findPlugin("composeModule").get().get().pluginId)
            apply(libs.findPlugin("kotlinxSerialization").get().get().pluginId)
            apply(libs.findPlugin("kspModule").get().get().pluginId)
        }

        extensions.configure<KotlinMultiplatformExtension> {
            sourceSets.run {
                commonMain.dependencies {
                    //library
                    implementation(libs.findLibrary("compose.navigation").get().get())
                    implementation(libs.findLibrary("compose.material.navigation").get().get())
                    implementation(libs.findLibrary("compose.lifecycle.viewmodel").get().get())
                    implementation(libs.findLibrary("koin.core").get().get())
                    implementation(libs.findLibrary("koin.annotations").get().get())
                    implementation(libs.findLibrary("koin.compose").get().get())
                    implementation(libs.findLibrary("koin.compose.viewmodel").get().get())
                    implementation(libs.findLibrary("koin.compose.viewmodel.navigation").get().get())

                    //bundle
                    libs.findBundle("ktor").get().get().forEach {
                        implementation(it)
                    }

                    //projects
                    implementation(project(":foundation:core"))
                    implementation(project(":foundation:design-system"))
                    implementation(project(":foundation:navigation-routes"))
                    implementation(project(":shared"))
                }

                commonMain.configure {
                    kotlin.srcDirs("build/generated/ksp/metadata/commonMain/kotlin")
                }
            }
        }
    }
}