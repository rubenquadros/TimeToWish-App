plugins {
    alias(libs.plugins.featureModule)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.immutable.collections)

            implementation(libs.kotlinx.datetime)
        }
    }
}

compose.resources {
    packageOfResClass = "io.github.rubenquadros.timetowish.feature.home.resources"
}