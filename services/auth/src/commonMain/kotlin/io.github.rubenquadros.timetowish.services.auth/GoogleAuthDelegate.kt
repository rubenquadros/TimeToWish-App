package io.github.rubenquadros.timetowish.services.auth

interface Auth {
    suspend fun login(clientId: String, serverId: String = ""): SignInResult

    suspend fun logout(): SignOutResult
}

expect fun getGoogleAuthDelegate(): Auth