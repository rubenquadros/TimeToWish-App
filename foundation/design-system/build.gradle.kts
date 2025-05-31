plugins {
    alias(libs.plugins.composeModule)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.bundles.coil)

            implementation(libs.kotlinx.datetime)
        }
    }
}

compose.resources {
    packageOfResClass = "io.github.rubenquadros.timetowish.ui.resources"
}
