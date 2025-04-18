package io.github.rubenquadros.timetowish

import org.koin.dsl.KoinConfiguration
import org.koin.dsl.includes
import org.koin.dsl.koinConfiguration
import org.koin.ksp.generated.module

expect fun koinAppConfig(): KoinConfiguration

internal fun koinConfig() = koinConfiguration {
    includes(koinAppConfig())
    //All modules go here
    modules(CommonModule().module, FeatureModule().module)
}