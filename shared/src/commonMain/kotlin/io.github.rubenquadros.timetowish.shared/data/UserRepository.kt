package io.github.rubenquadros.timetowish.shared.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOneNotNull
import io.github.rubenquadros.timetowish.core.TimeToWishDb
import io.github.rubenquadros.timetowish.core.session.CurrentUser
import io.github.rubenquadros.timetowish.core.session.UserSession
import io.github.rubenquadros.timetowish.core.session.toCurrentUser
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

internal interface UserRepository {
    suspend fun getIsUserLoggedIn(): Boolean

    fun observeCurrentUser(): Flow<CurrentUser>

    suspend fun saveUser(currentUser: CurrentUser)
}

@Single
internal class UserRepositoryImpl(
    private val userSession: UserSession,
    private val database: TimeToWishDb,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : UserRepository {
    override suspend fun getIsUserLoggedIn(): Boolean {
        return userSession.isUserLoggedIn()
    }

    override fun observeCurrentUser(): Flow<CurrentUser> {
        return database.userQueries.getUser().asFlow().mapToOneNotNull(dispatcher).filterNotNull().map {
            it.toCurrentUser()
        }
    }

    override suspend fun saveUser(currentUser: CurrentUser) {
        userSession.saveUser(currentUser)
    }
}