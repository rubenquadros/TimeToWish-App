package io.github.rubenquadros.timetowish.core.database

import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import io.github.rubenquadros.timetowish.core.TimeToWishDb

actual class DatabaseDriverFactory actual constructor() {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            schema = TimeToWishDb.Schema.synchronous(),
            name = "TimeToWish.db",
            maxReaderConnections = 4
        )
    }
}