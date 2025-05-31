package io.github.rubenquadros.timetowish.conventions

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

internal fun Project.getCatalog() = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal val Project.moduleName get() = path
    .split(":").last { it.isNotBlank() }.lowercase()

internal val Project.modulePackage get() = path
    .split(":")
    .filter { it.isNotBlank() }
    .joinToString(".") { it.lowercase().replace("-", "") }