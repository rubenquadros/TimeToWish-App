package io.github.rubenquadros.timetowish.core.di

import app.cash.sqldelight.EnumColumnAdapter
import io.github.rubenquadros.timetowish.core.TimeToWishDb
import io.github.rubenquadros.timetowish.core.User
import io.github.rubenquadros.timetowish.core.database.DatabaseDriverFactory
import kotlinx.coroutines.*
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan("io.github.rubenquadros.timetowish.core")
class TWCoreModule {

    @Single
    fun getDatabase(): TimeToWishDb = TimeToWishDb.invoke(
        userAdapter = User.Adapter(EnumColumnAdapter()),
        driver = DatabaseDriverFactory().createDriver()
    )

    @Single
    fun getCoroutineScope(): CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
}