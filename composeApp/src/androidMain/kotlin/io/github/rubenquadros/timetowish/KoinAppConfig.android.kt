package io.github.rubenquadros.timetowish

import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.dsl.KoinConfiguration
import org.koin.dsl.koinConfiguration

actual fun koinAppConfig(): KoinConfiguration = koinConfiguration {
    androidLogger()
    androidContext(MainApplication.instance ?: error("Application is not initialized"))
}