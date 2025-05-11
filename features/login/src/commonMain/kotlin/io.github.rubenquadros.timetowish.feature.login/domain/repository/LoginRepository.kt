package io.github.rubenquadros.timetowish.feature.login.domain.repository

import io.github.rubenquadros.timetowish.feature.login.domain.entity.LoggedInUser
import io.github.rubenquadros.timetowish.feature.login.domain.entity.LoginData

internal interface LoginRepository {
    suspend fun getLoginData(): LoginData

    suspend fun validateLogin(token: String): LoggedInUser
}