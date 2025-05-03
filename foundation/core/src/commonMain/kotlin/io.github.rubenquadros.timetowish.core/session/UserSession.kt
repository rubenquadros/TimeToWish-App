package io.github.rubenquadros.timetowish.core.session

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOneOrNull
import io.github.rubenquadros.timetowish.core.TimeToWishDb
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.koin.core.annotation.Single
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface UserSession {
    /**
     * If the userId is not available, then this method suspends till it becomes available.
     */
    suspend fun getUserId(): String
    /**
     * If the login information is not available, then this method suspends till it becomes available.
     */
    suspend fun isUserLoggedIn(): Boolean

    /**
     * This method does not guarentee the correct information till the time [CurrentUser] becomes available.
     * Use this method if you are sure the flow will access the login iformation post it's availability.
     */
    val isLoggedIn: Boolean

    /**
     * This method returns a null value till the time [CurrentUser] becomes available.
     * Use this method if you are sure the flow will access the user iformation post it's availability.
     */
    val currentUser: CurrentUser?
}

@Single
internal class UserSessionImpl(
    private val database: TimeToWishDb,
    private val appScope: CoroutineScope,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : UserSession {

    init {
        appScope.launch {
            getCurrentUser().collect()
        }
    }

    private var _currentUser: CurrentUser? = null
    private val _currentUserFlow: MutableStateFlow<CurrentUser?> = MutableStateFlow(null)

    override suspend fun getUserId(): String {
        return _currentUserFlow.filterNotNull().stateIn(appScope).first().id
    }

    override suspend fun isUserLoggedIn(): Boolean {
        return _currentUserFlow.filterNotNull().stateIn(appScope).first().isLoggedIn
    }

    override val isLoggedIn: Boolean
        get() = _currentUser?.isLoggedIn ?: false
    override val currentUser: CurrentUser?
        get() = _currentUser

    private fun getCurrentUser(): Flow<CurrentUser> {
        return database.userQueries.getUser().asFlow().mapToOneOrNull(dispatcher).onEach { user ->
            if (user == null) {
                insertNonLoggedInUser()
            }
        }.filterNotNull().map { user ->
            val currentUser = user.toCurrentUser()
            updateUserData(currentUser)
            currentUser
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    private suspend fun insertNonLoggedInUser() {
        database.userQueries.insertNonLoggedInUser(
            id = Uuid.toString()
        )
    }

    private fun updateUserData(newUser: CurrentUser) {
        with(newUser) {
            _currentUser = this
            _currentUserFlow.value = this
        }
    }
}