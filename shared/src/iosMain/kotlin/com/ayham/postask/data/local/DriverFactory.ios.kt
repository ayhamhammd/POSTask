package com.ayham.postask.data.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver

actual class DriverFactory {
    actual fun create(): SqlDriver {
        return NativeSqliteDriver(PosDatabase.Schema, "pos.db")
    }
}
