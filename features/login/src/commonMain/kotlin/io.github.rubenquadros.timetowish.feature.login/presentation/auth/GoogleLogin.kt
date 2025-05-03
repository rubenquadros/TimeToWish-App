package io.github.rubenquadros.timetowish.feature.login.presentation.auth

expect class GoogleLoginDelegate() {
    suspend fun login(clientId: String)
}