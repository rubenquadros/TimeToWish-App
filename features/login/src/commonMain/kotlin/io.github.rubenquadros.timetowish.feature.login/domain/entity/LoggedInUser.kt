package io.github.rubenquadros.timetowish.feature.login.domain.entity

internal data class LoggedInUser(
    val id: String,
    val name: String,
    val email: String,
    val profilePic: String?
)
