package io.github.rubenquadros.timetowish.core.api

import io.github.rubenquadros.timetowish.core.session.UserSession
import io.github.rubenquadros.timetowish.core.session.getPlatform
import io.ktor.client.HttpClient
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.request.*
import io.ktor.client.statement.HttpResponseContainer
import io.ktor.client.statement.HttpResponsePipeline
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.*
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Single

interface ApiManager {
    val client: HttpClient
}

@Single
internal class ApiManagerImpl(private val userSession: UserSession) : ApiManager {
    override val client: HttpClient by lazy { getHttpClient() }

    private fun getHttpClient(): HttpClient {
        return HttpClient(
            engine = getHttpClientEngine()
        ){
            install(ContentNegotiation) {
                json(Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    encodeDefaults = true
                    explicitNulls = false
                })
            }

            install(Logging) {
                level = LogLevel.ALL
            }

            defaultRequest {
                url {
                    host = "timetowish-server.onrender.com"
                    protocol = URLProtocol.HTTPS
                }
            }
        }.also { httpClient ->
            httpClient.apply {
                addRequestInterceptor(userSession)
                addResponseInterceptor()
            }
        }
    }

    private fun HttpClient.addResponseInterceptor() {
        responsePipeline.intercept(phase = HttpResponsePipeline.Transform) { (info, body) ->
            if (context.response.status != HttpStatusCode.OK) {
                //parse error response and throw exception
                val response = body as ApiError
                throw ApiException(message = response.message)
            }

            proceedWith(HttpResponseContainer(info, body))
        }
    }

    private fun HttpClient.addRequestInterceptor(userSession: UserSession) {
        plugin(HttpSend).intercept { request ->
            execute(request.addDefaultHeaders(userSession))
        }
    }

    private suspend fun HttpRequestBuilder.addDefaultHeaders(userSession: UserSession): HttpRequestBuilder {
        headers.appendAll(
            StringValues.build {
                this["UserId"] = userSession.getUserId()
                this["Platform"] = getPlatform()
            }
        )

        return this
    }
}