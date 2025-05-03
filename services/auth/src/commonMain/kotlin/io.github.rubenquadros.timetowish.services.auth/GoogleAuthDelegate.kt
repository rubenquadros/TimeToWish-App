package io.github.rubenquadros.timetowish.services.auth

expect class GoogleAuthDelegate() {

    suspend fun login(clientId: String) : SignInResult

    suspend fun logout(): SignOutResult
}