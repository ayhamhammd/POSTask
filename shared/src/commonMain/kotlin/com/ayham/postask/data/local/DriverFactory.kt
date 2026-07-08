package com.ayham.postask.data.local

import app.cash.sqldelight.db.SqlDriver

expect class DriverFactory {
    fun create(): SqlDriver
}

fun createDatabase(driverFactory: DriverFactory): PosDatabase {
    return PosDatabase(driverFactory.create())
}
