package io.github.rubenquadros.timetowish.conventions

import app.cash.sqldelight.gradle.SqlDelightExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class DatabaseModulePlugin : Plugin<Project> {

    override fun apply(target: Project): Unit = with(target) {
        val libs = getCatalog()

        plugins.apply(libs.findPlugin("sqldelight").get().get().pluginId)

        extensions.configure<KotlinMultiplatformExtension> {
            sourceSets.run {
                commonMain.dependencies {
                    implementation(libs.findLibrary("sqldelight.coroutines").get().get())
                }

                androidMain.dependencies {
                    implementation(libs.findLibrary("sqldelight.android").get().get())
                }

                iosMain.dependencies {
                    implementation(libs.findLibrary("sqldelight.ios").get().get())
                }
            }
        }

        extensions.configure<SqlDelightExtension> {
            databases.apply {
                create("TimeToWishDb") {
                    packageName.set("io.github.rubenquadros.timetowish.$moduleName")
                    schemaOutputDirectory.set(file("${rootProject.projectDir}/db-schema"))
                    generateAsync.set(true)

                }
            }
        }
    }
}