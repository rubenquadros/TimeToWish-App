package io.github.rubenquadros.timetowish.core.base.usecase

import io.github.rubenquadros.timetowish.core.api.ApiException
import io.ktor.client.network.sockets.SocketTimeoutException
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

/**
 * Extend your UseCase classes with [UseCase] if you do not want to wrap your result with [UseCaseResult]
 */
abstract class UseCase<in REQUEST, out RESPONSE> {

    operator fun invoke(request: REQUEST): RESPONSE? {
        return runCatching {
            execute(request)
        }.getOrNull()
    }

    abstract fun execute(request: REQUEST): RESPONSE
}

/**
 * Extend your UseCase classes with [BaseUseCase] for a single shot resposne
 * @see UseCaseResult
 */
abstract class BaseUseCase<in REQUEST, out RESPONSE>(
    open val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(request: REQUEST): UseCaseResult<RESPONSE> {
        return runCatching {
            withContext(dispatcher) {
                UseCaseResult.Success(execute(request))
            }
        }.getOrElse { exception ->
            currentCoroutineContext().ensureActive()
            getUseCaseResultFromThrowable(exception)
        }
    }

    abstract suspend fun execute(request: REQUEST): RESPONSE
}

/**
 * Extend your UseCase with [BaseFlowUseCase] if you want to wrap the response in a [Flow]
 * @see UseCaseResult
 */
abstract class BaseFlowUseCase<in REQUEST, out RESPONSE>(
    open val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    operator fun invoke(request: REQUEST): Flow<UseCaseResult<RESPONSE>> {
        return flow {
            val result = runCatching {
                UseCaseResult.Success(execute(request))
            }.getOrElse { exception ->
                currentCoroutineContext().ensureActive()
                getUseCaseResultFromThrowable(exception)
            }
            emit(result)
        }.flowOn(dispatcher)
    }

    abstract suspend fun execute(request: REQUEST): RESPONSE
}

/**
 * Extend your UseCase with [BaseFlowResponseUseCase] when you are expecitng a [Flow] response from the data source
 * @see UseCaseResult
 */
abstract class BaseFlowResponseUseCase<in REQUEST, out RESPONSE>(
    open val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    operator fun invoke(request: REQUEST): Flow<UseCaseResult<RESPONSE>> {
        return runCatching {
            execute(request).map {
                UseCaseResult.Success(it)
            }
        }.getOrElse { exception ->
            flow {
                currentCoroutineContext().ensureActive()
                getUseCaseResultFromThrowable<RESPONSE>(exception)
            }
        }.flowOn(dispatcher)
    }

    abstract fun execute(request: REQUEST): Flow<RESPONSE>
}

private fun <DATA>getUseCaseResultFromThrowable(exception: Throwable): UseCaseResult<DATA> {
    return when (exception) {
        is CancellationException -> throw exception
        is SocketTimeoutException -> UseCaseResult.NetworkError
        is ApiException -> UseCaseResult.Error(message = exception.message)
        else -> UseCaseResult.Unknown
    }
}