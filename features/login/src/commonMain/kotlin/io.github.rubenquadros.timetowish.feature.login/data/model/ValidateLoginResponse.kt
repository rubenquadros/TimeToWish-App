package io.github.rubenquadros.timetowish.feature.login.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ValidateLoginResponse(
    val id: String,
    val name: String,
    val email: String,
    val profilePic: String?
)
