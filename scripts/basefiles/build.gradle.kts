plugins {
    alias(libs.plugins.featureModule)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            //add your dependencies
        }
        commonTest.dependencies {
            //add your test dependencies
        }
    }
}

compose.resources {
    packageOfResClass = "modulePackage.resources"
}
