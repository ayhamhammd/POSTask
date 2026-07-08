package com.ayham.postask.di

import com.ayham.postask.data.remote.api.CatalogApi
import com.ayham.postask.data.remote.api.OrdersApi
import com.ayham.postask.network.api.ApiService
import com.ayham.postask.network.client.HttpClientFactory
import com.ayham.postask.network.mock.FakePosServer
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

private const val BASE_URL = "https://pos.mock"

val networkModule = module {
    singleOf(::FakePosServer)
    single { HttpClientFactory.create(baseUrl = BASE_URL, engine = get<FakePosServer>().engine()) }
    single { ApiService(get()) }
    singleOf(::CatalogApi)
    singleOf(::OrdersApi)
}
