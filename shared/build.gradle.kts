plugins {
    alias(libs.plugins.composeModule)
    alias(libs.plugins.kspModule)
    alias(libs.plugins.dbModule)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.core.ktx)
            implementation(libs.androidx.browser)
        }

        commonMain.dependencies {
            implementation(libs.koin.core)
            implementation(libs.koin.annotations)
            commonMain.configure { kotlin.srcDirs("build/generated/ksp/metadata/commonMain/kotlin") }

            implementation(libs.kotlinx.datetime)

            implementation(projects.foundation.core)
            implementation(projects.foundation.designSystem)
        }
    }
}