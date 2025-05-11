package io.github.rubenquadros.timetowish.services.auth

import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class AuthModule {

    @Single
    fun provideGoogleAuthDelegate(): Auth = getGoogleAuthDelegate()
}