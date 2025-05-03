package io.github.rubenquadros.timetowish.feature.login.data.repository

import io.github.rubenquadros.timetowish.core.api.ApiManager
import io.github.rubenquadros.timetowish.core.api.body
import io.github.rubenquadros.timetowish.feature.login.data.datasource.LoginDataMemoryDataSource
import io.github.rubenquadros.timetowish.feature.login.data.mapper.toLoginData
import io.github.rubenquadros.timetowish.feature.login.data.model.GetLoginDataResponse
import io.github.rubenquadros.timetowish.feature.login.domain.entity.LoginData
import io.github.rubenquadros.timetowish.feature.login.domain.repository.LoginRepository
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments
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
                appendPathSegments("login")
            }
        }.body<GetLoginDataResponse>().also {
            //cache the keys
            loginKeysDataSource.save(it)
        }

        println("Login response: $response")

        return response.toLoginData()
    }
}