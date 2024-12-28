package io.github.rubenquadros.timetowish.core.usecase

import io.github.rubenquadros.timetowish.core.api.ApiException
import io.ktor.client.network.sockets.SocketTimeoutException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

abstract class BaseUseCase<REQUEST, RESPONSE>(
    open val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(request: REQUEST): UseCaseResult<RESPONSE> {
        return runCatching {
            withContext(dispatcher) {
                UseCaseResult.Success(execute(request))
            }
        }.getOrElse { exception ->
            getUseCaseResultFromThrowable(exception)
        }
    }

    abstract suspend fun execute(request: REQUEST): RESPONSE
}

abstract class BaseFlowUseCase<REQUEST, RESPONSE>(
    open val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    operator fun invoke(request: REQUEST): Flow<UseCaseResult<RESPONSE>> {
        return flow {
            val result = runCatching {
                UseCaseResult.Success(execute(request))
            }.getOrElse { exception ->
                getUseCaseResultFromThrowable(exception)
            }
            emit(result)
        }.flowOn(dispatcher)
    }

    abstract suspend fun execute(request: REQUEST): RESPONSE
}

private fun <DATA>getUseCaseResultFromThrowable(exception: Throwable): UseCaseResult<DATA> {
    return when (exception) {
        is CancellationException -> throw exception
        is SocketTimeoutException -> UseCaseResult.NetworkError
        is ApiException -> UseCaseResult.Error(message = exception.message)
        else -> UseCaseResult.Unknown
    }
}