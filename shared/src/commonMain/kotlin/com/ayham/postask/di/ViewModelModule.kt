package com.ayham.postask.di

import com.ayham.postask.presentation.app.AppViewModel
import com.ayham.postask.presentation.cart.CartViewModel
import com.ayham.postask.presentation.catalog.CatalogViewModel
import com.ayham.postask.presentation.orders.OrdersViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::AppViewModel)
    viewModelOf(::CatalogViewModel)
    viewModelOf(::CartViewModel)
    viewModelOf(::OrdersViewModel)
}
