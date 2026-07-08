package com.ayham.postask.di

import org.koin.core.module.Module

expect val platformModule: Module

fun appModules(): List<Module> = listOf(
    platformModule,
    networkModule,
    databaseModule,
    repositoryModule,
    useCaseModule,
    viewModelModule
)
