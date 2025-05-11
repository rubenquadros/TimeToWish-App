import com.android.build.gradle.BaseExtension
import java.util.Properties

plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.kotlinCocoapods) apply false
    alias(libs.plugins.kotlinxSerialization) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.sqldelight) apply false
}

subprojects {
    plugins.withId("com.android.application") {
        configure<BaseExtension> {
            applySigningConfigs(this)
        }
    }

    plugins.withId("com.android.library") {
        configure<BaseExtension> {
            applySigningConfigs(this)
        }
    }
}

fun Project.applySigningConfigs(android: BaseExtension) {
    android.signingConfigs {
        val keystore = getKeyStore()

        getByName("debug") {
            keyAlias = keystore.debug.keyAlias
            keyPassword = keystore.debug.keyPassword
            storeFile = file(keystore.debug.storePath)
            storePassword = keystore.debug.storePassword
        }

        create("release") {
            keyAlias = keystore.release.keyAlias
            keyPassword = keystore.release.keyPassword
            storeFile = file(keystore.release.storePath)
            storePassword = keystore.release.storePassword
        }
    }

    android.buildTypes {
        getByName("release") {
            signingConfig = android.signingConfigs.findByName("release")
        }

        getByName("debug") {
            signingConfig = android.signingConfigs.getByName("debug")
        }
    }
}

data class TWKeyStore(
    val debug: KeyStoreProps,
    val release: KeyStoreProps
)

data class KeyStoreProps(
    val keyAlias: String,
    val keyPassword: String,
    val storePath: String,
    val storePassword: String
)

internal fun getKeyStore(): TWKeyStore {
    return runCatching {
        val properties = Properties().apply {
            File("/Users/rquadros/Documents/Ruben/git_tree/TimeToWish-App/local.properties")
                .inputStream()
                .use { load(it) }
        }

        TWKeyStore(
            debug = KeyStoreProps(
                keyAlias = properties.getProperty("debug.keyAlias"),
                keyPassword = properties.getProperty("debug.keyPass"),
                storePath = properties.getProperty("debug.storePath"),
                storePassword = properties.getProperty("debug.storePass")
            ),
            release = KeyStoreProps(
                keyAlias = properties.getProperty("release.keyAlias"),
                keyPassword = properties.getProperty("release.keyPass"),
                storePath = properties.getProperty("release.storePath"),
                storePassword = properties.getProperty("release.storePass")
            )
        )
    }.getOrElse {
        TWKeyStore(
            debug = defaultDebugKeyStore,
            release = defaultReleaseKeyStore
        )
    }
}

//TODO::Add github secrets
private val defaultDebugKeyStore = KeyStoreProps(
    keyAlias = "",
    keyPassword = "",
    storePath = "",
    storePassword = ""
)

//TODO::Add github secrets
private val defaultReleaseKeyStore = KeyStoreProps(
    keyAlias = "",
    keyPassword = "",
    storePath = "",
    storePassword = ""
)