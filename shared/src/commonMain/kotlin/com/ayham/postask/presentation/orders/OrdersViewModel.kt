package com.ayham.postask.presentation.orders

import androidx.lifecycle.viewModelScope
import com.ayham.postask.domain.usecase.ObserveConnectivityUseCase
import com.ayham.postask.domain.usecase.ObserveOrdersUseCase
import com.ayham.postask.domain.usecase.SyncPendingOrdersUseCase
import com.ayham.postask.presentation.base.BaseViewModel
import com.ayham.postask.util.ResultWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class OrdersViewModel(
    observeOrders: ObserveOrdersUseCase,
    observeConnectivity: ObserveConnectivityUseCase,
    private val syncPendingOrders: SyncPendingOrdersUseCase,
) : BaseViewModel() {

    private val isSyncing = MutableStateFlow(false)

    private val orders = observeOrders()
        .onEach { result ->
            when (result) {
                ResultWrapper.Loading -> setLoading(true)
                is ResultWrapper.Success -> setLoading(false)
                is ResultWrapper.Error -> {
                    setLoading(false)
                    emitError(result.error)
                }
            }
        }
        .map { result ->
            when (result) {
                is ResultWrapper.Success -> result.data
                is ResultWrapper.Error -> emptyList()
                ResultWrapper.Loading -> emptyList()
            }
        }

    val state: StateFlow<OrdersState> = combine(
        orders,
        observeConnectivity(),
        isSyncing,
    ) { orders, online, syncing ->
        OrdersState(
            pending = orders.filter { it.isPending },
            synced = orders.filterNot {
                it.isPending },
            isOnline = online,
            isSyncing = syncing,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), OrdersState())

    fun onEvent(event: OrdersEvent) {
        when (event) {
            OrdersEvent.OnSyncClicked -> sync()
        }
    }

    private fun sync() {
        if (isSyncing.value) return
        syncPendingOrders()
            .onStart { isSyncing.value = true }
            .onEach { if (it is ResultWrapper.Error) emitError(it.error) }
            .onCompletion { isSyncing.value = false }
            .launchIn(viewModelScope)
    }
}