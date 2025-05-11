package io.github.rubenquadros.timetowish.feature.login.data.repository

import io.github.rubenquadros.timetowish.core.api.ApiManager
import io.github.rubenquadros.timetowish.core.api.body
import io.github.rubenquadros.timetowish.feature.login.data.datasource.LoginDataMemoryDataSource
import io.github.rubenquadros.timetowish.feature.login.data.mapper.toLoggedInUser
import io.github.rubenquadros.timetowish.feature.login.data.mapper.toLoginData
import io.github.rubenquadros.timetowish.feature.login.data.model.GetLoginDataResponse
import io.github.rubenquadros.timetowish.feature.login.data.model.ValidateLoginRequest
import io.github.rubenquadros.timetowish.feature.login.data.model.ValidateLoginResponse
import io.github.rubenquadros.timetowish.feature.login.domain.entity.LoggedInUser
import io.github.rubenquadros.timetowish.feature.login.domain.entity.LoginData
import io.github.rubenquadros.timetowish.feature.login.domain.repository.LoginRepository
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType
import org.koin.core.annotation.Single

@Single
internal class LoginRepositoryImpl(
    private val loginKeysDataSource: LoginDataMemoryDataSource,
    private val apiManager: ApiManager
) : LoginRepository {
    override suspend fun getLoginData(): LoginData {
        val cachedLoginData = loginKeysDataSource.latest()
        if (cachedLoginData != null) return cachedLoginData.toLoginData()

        //data is not present so call api and cache the data
        val response = apiManager.client.get {
            url {
                appendPathSegments("login/keys")
            }
        }.body<GetLoginDataResponse>().also {
            //cache the keys
            loginKeysDataSource.save(it)
        }

        return response.toLoginData()
    }

    override suspend fun validateLogin(token: String): LoggedInUser {
        val response = apiManager.client.post {
            url {
                appendPathSegments("login/validate")
            }

            setBody(ValidateLoginRequest(token))
        }.body<ValidateLoginResponse>()
        return response.toLoggedInUser()
    }
}