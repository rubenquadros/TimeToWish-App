plugins {
    alias(libs.plugins.multiplatformModule)
    alias(libs.plugins.kspModule)
    alias(libs.plugins.dbModule)
    alias(libs.plugins.kotlinxSerialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines)
            implementation(libs.bundles.ktor)
            implementation(libs.koin.core)
            implementation(libs.koin.annotations)
            commonMain.configure { kotlin.srcDirs("build/generated/ksp/metadata/commonMain/kotlin") }

            implementation(libs.compose.lifecycle.viewmodel)
            implementation(libs.compose.lifecycle.savedstate)

            implementation(libs.okio)

            implementation(libs.bundles.coil)

            implementation(libs.kotlinx.immutable.collections)
        }
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}