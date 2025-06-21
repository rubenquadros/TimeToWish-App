plugins {
    alias(libs.plugins.featureModule)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.kotlinx.immutable.collections)
        }
    }
}

compose.resources {
    packageOfResClass = "io.github.rubenquadros.timetowish.features.generatewish.resources"
}
