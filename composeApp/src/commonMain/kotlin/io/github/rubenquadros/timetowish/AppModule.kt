package io.github.rubenquadros.timetowish

import io.github.rubenquadros.timetowish.core.di.TWCoreModule
import io.github.rubenquadros.timetowish.feature.home.di.HomeModule
import io.github.rubenquadros.timetowish.shared.di.TWSharedModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(includes = [TWCoreModule::class, TWSharedModule::class])
@ComponentScan
class CommonModule


@Module(includes = [HomeModule::class])
@ComponentScan
class FeatureModule