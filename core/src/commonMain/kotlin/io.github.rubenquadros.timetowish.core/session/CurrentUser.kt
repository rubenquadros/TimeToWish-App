package io.github.rubenquadros.timetowish.core.session

import io.github.rubenquadros.timetowish.core.User

data class CurrentUser(
    val id: String,
    val name: String,
    val email: String,
    val profilePic: String,
    val isLoggedIn: Boolean
)

fun User.toCurrentUser(): CurrentUser {
    return CurrentUser(
        id = id,
        name = name.orEmpty(),
        email = email.orEmpty(),
        profilePic = profilePic.orEmpty(),
        isLoggedIn = loginStatus.isLoggedIn()
    )
}

enum class LoginStatus {
    Y, N;

    fun isLoggedIn(): Boolean = this == Y
}