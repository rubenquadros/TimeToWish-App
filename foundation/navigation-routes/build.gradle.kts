plugins {
    alias(libs.plugins.composeModule)
    alias(libs.plugins.kotlinxSerialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.navigation)
            implementation(libs.compose.material.navigation)
            implementation(libs.kotlinx.serialization)
        }
    }
}
