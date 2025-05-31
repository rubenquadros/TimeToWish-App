plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.kotlin.gradle)
    compileOnly(libs.android.gradle)
    compileOnly(libs.compose.gradle)
    compileOnly(libs.ksp.gradle)
    compileOnly(libs.sqldelight.gradle)
}

gradlePlugin {
    plugins {
        register("multiplatformModule") {
            id = libs.plugins.multiplatformModule.get().pluginId
            implementationClass = "io.github.rubenquadros.timetowish.conventions.MultiplatformModulePlugin"
        }

        register("composeModule") {
            id = libs.plugins.composeModule.get().pluginId
            implementationClass = "io.github.rubenquadros.timetowish.conventions.ComposeModulePlugin"
        }

        register("kspModule") {
            id = libs.plugins.kspModule.get().pluginId
            implementationClass = "io.github.rubenquadros.timetowish.conventions.KspModulePlugin"
        }

        register("dbModule") {
            id = libs.plugins.dbModule.get().pluginId
            implementationClass = "io.github.rubenquadros.timetowish.conventions.DatabaseModulePlugin"
        }

        register("featureModule") {
            id = libs.plugins.featureModule.get().pluginId
            implementationClass = "io.github.rubenquadros.timetowish.conventions.FeatureModulePlugin"
        }
    }
}
