package com.ayham.postask.di

import com.ayham.postask.data.local.createDatabase
import org.koin.dsl.module

val databaseModule = module {
    single { createDatabase(get()) }
}
