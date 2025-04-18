package io.github.rubenquadros.timetowish

import org.koin.dsl.KoinConfiguration
import org.koin.dsl.koinConfiguration

actual fun koinAppConfig(): KoinConfiguration = koinConfiguration {
    printLogger()
}