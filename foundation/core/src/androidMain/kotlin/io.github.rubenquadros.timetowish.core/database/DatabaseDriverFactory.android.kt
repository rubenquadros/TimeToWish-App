package io.github.rubenquadros.timetowish.core.database

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import io.github.rubenquadros.timetowish.core.TimeToWishDb
import org.koin.mp.KoinPlatform.getKoin

actual class DatabaseDriverFactory actual constructor(){
    actual fun createDriver(): SqlDriver {
        val context: Context = getKoin().get()
        val schema = TimeToWishDb.Schema.synchronous()
        return AndroidSqliteDriver(
            schema = schema,
            context = context,
            name = "TimeToWish.db",
            callback = object : AndroidSqliteDriver.Callback(schema) {
                override fun onOpen(db: SupportSQLiteDatabase) {
                    super.onOpen(db)
                    db.enableWriteAheadLogging()
                }
            }
        )
    }
}