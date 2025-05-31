package io.github.rubenquadros.timetowish.conventions

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class ComposeModulePlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val libs = getCatalog()

        with(plugins) {
            apply(libs.findPlugin("multiplatformModule").get().get().pluginId)
            apply(libs.findPlugin("composeMultiplatform").get().get().pluginId)
            apply(libs.findPlugin("composeCompiler").get().get().pluginId)
        }

        val compose = extensions.getByType<ComposeExtension>()

        extensions.configure<KotlinMultiplatformExtension> {
            sourceSets.run {
                commonMain.dependencies {
                    implementation(compose.dependencies.runtime)
                    implementation(compose.dependencies.foundation)
                    implementation(compose.dependencies.material3)
                    implementation(compose.dependencies.ui)
                    implementation(compose.dependencies.components.resources)
                    implementation(compose.dependencies.components.uiToolingPreview)
                }

                androidMain.dependencies {
                    libs.findBundle("androidx.compose").get().get().forEach {
                        implementation(it)
                    }
                }
            }
        }
    }
}