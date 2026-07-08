package com.ayham.postask.di

import com.ayham.postask.data.repository.CatalogRepositoryImpl
import com.ayham.postask.data.repository.CartRepositoryImpl
import com.ayham.postask.data.repository.ConnectivityRepositoryImpl
import com.ayham.postask.data.repository.OrderRepositoryImpl
import com.ayham.postask.domain.repository.CatalogRepository
import com.ayham.postask.domain.repository.CartRepository
import com.ayham.postask.domain.repository.ConnectivityRepository
import com.ayham.postask.domain.repository.OrderRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::ConnectivityRepositoryImpl) bind ConnectivityRepository::class
    singleOf(::CatalogRepositoryImpl) bind CatalogRepository::class
    singleOf(::CartRepositoryImpl) bind CartRepository::class
    singleOf(::OrderRepositoryImpl) bind OrderRepository::class
}
