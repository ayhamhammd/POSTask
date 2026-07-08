package com.ayham.postask.di

import com.ayham.postask.domain.usecase.AddToCartUseCase
import com.ayham.postask.domain.usecase.CalculateCartTotalsUseCase
import com.ayham.postask.domain.usecase.ChangeCartQuantityUseCase
import com.ayham.postask.domain.usecase.CheckoutUseCase
import com.ayham.postask.domain.usecase.GetCatalogUseCase
import com.ayham.postask.domain.usecase.ObserveCartUseCase
import com.ayham.postask.domain.usecase.ObserveConnectivityUseCase
import com.ayham.postask.domain.usecase.ObserveOrdersUseCase
import com.ayham.postask.domain.usecase.ObserveSyncFeedbackUseCase
import com.ayham.postask.domain.usecase.RemoveFromCartUseCase
import com.ayham.postask.domain.usecase.SyncPendingOrdersUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModule = module {
    factoryOf(::ObserveConnectivityUseCase)
    factoryOf(::GetCatalogUseCase)
    factoryOf(::AddToCartUseCase)
    factoryOf(::ObserveCartUseCase)
    factoryOf(::CalculateCartTotalsUseCase)
    factoryOf(::ChangeCartQuantityUseCase)
    factoryOf(::RemoveFromCartUseCase)
    factoryOf(::CheckoutUseCase)
    factoryOf(::ObserveOrdersUseCase)
    factoryOf(::ObserveSyncFeedbackUseCase)
    factoryOf(::SyncPendingOrdersUseCase)
}
