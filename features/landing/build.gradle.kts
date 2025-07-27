plugins {
    alias(libs.plugins.featureModule)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.features.home)
            implementation(projects.features.generateWish)

            implementation(libs.kotlinx.immutable.collections)
        }
    }
}

compose.resources {
    packageOfResClass = "io.github.rubenquadros.timetowish.feature.landing.resources"
}
