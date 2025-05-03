package io.github.rubenquadros.timetowish.feature.login.data.mapper

import io.github.rubenquadros.timetowish.feature.login.data.model.ValidateLoginResponse
import io.github.rubenquadros.timetowish.feature.login.domain.entity.LoggedInUser

internal fun ValidateLoginResponse.toLoggedInUser(): LoggedInUser {
    return LoggedInUser(
        id = id,
        name = name,
        email = email,
        profilePic = profilePic
    )
}