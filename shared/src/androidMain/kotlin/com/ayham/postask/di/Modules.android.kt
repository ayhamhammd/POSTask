package com.ayham.postask.di

import com.ayham.postask.data.local.DriverFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single { DriverFactory(androidContext()) }
}
