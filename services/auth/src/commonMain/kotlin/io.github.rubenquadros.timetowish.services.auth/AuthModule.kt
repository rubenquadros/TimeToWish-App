package io.github.rubenquadros.timetowish.services.auth

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@ComponentScan("io.github.rubenquadros.timetowish.services.auth")
@Module
class AuthModule {

    @Single
    fun provideGoogleAuthDelegate() = GoogleAuthDelegate()
}