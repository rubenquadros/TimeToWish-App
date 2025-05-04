package io.github.rubenquadros.timetowish.services.auth

sealed interface SignInResult {

    data class Success(val token: String) : SignInResult

    data object Failure : SignInResult

}

sealed interface SignOutResult {
    data object Success: SignOutResult

    data object Failure: SignOutResult
}