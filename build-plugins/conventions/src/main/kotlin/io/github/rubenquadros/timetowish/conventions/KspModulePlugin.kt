package io.github.rubenquadros.timetowish.conventions

import com.google.devtools.ksp.gradle.KspExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

class KspModulePlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val libs = getCatalog()

        plugins.apply(libs.findPlugin("ksp").get().get().pluginId)

        extensions.configure<KspExtension> {
            arg("KOIN_CONFIG_CHECK","true")
            arg("KOIN_DEFAULT_MODULE","false")
            arg("KOIN_USE_COMPOSE_VIEWMODEL","true")
        }

        dependencies {
            add("kspCommonMainMetadata", libs.findLibrary("koin.ksp.compiler").get().get())
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
    }
}