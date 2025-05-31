plugins {
    alias(libs.plugins.multiplatformModule)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.kspModule)
}

kotlin {
    cocoapods {
        summary = "This is the auth module"
        homepage = "Link to the Auth Module homepage"
        ios.deploymentTarget = "16.0"
        version = "1.0"
        podfile = project.file("../../iosApp/Podfile")

        pod("GoogleSignIn") {
            version = "7.0.0"
        }

        framework {
            baseName = "auth"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.google.credentials)
            implementation(libs.google.credentials.play.services)
            implementation(libs.google.credentials.id)
        }

        commonMain.dependencies {
            implementation(libs.koin.core)
            implementation(libs.koin.annotations)
            commonMain.configure { kotlin.srcDirs("build/generated/ksp/metadata/commonMain/kotlin") }

            //core
            implementation(projects.foundation.core)
        }
    }
}
