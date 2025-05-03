package io.github.rubenquadros.timetowish.feature.login.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ValidateLoginRequest(
    val token: String
)
