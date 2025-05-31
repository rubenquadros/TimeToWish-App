plugins {
    alias(libs.plugins.featureModule)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.google.credentials)
            implementation(libs.google.credentials.id)
            implementation(libs.google.credentials.play.services)
        }

        commonMain.dependencies {
            implementation(projects.services.auth)
        }
    }
}

compose.resources {
    packageOfResClass = "io.github.rubenquadros.timetowish.feature.login.resources"
}