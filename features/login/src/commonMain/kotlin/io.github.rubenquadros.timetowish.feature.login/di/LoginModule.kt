package io.github.rubenquadros.timetowish.feature.login.di

import io.github.rubenquadros.timetowish.feature.login.presentation.auth.GoogleLoginDelegate
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module

@ComponentScan("io.github.rubenquadros.timetowish.feature.login")
@Module
class LoginModule {

    @Factory
    fun provideGoogleLoginDelegate(): GoogleLoginDelegate = GoogleLoginDelegate()
}