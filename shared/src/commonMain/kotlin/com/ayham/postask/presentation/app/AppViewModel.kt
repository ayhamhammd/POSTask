package com.ayham.postask.presentation.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayham.postask.domain.model.SyncFeedback
import com.ayham.postask.domain.usecase.ObserveCartUseCase
import com.ayham.postask.domain.usecase.ObserveConnectivityUseCase
import com.ayham.postask.domain.usecase.ObserveSyncFeedbackUseCase
import com.ayham.postask.domain.usecase.SyncPendingOrdersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class AppViewModel(
    private val observeConnectivity: ObserveConnectivityUseCase,
    private val observeCart: ObserveCartUseCase,
    private val syncPendingOrders: SyncPendingOrdersUseCase,
    observeSyncFeedback: ObserveSyncFeedbackUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(AppState())
    val state: StateFlow<AppState> = _state.asStateFlow()

    val syncFeedback: SharedFlow<SyncFeedback> = observeSyncFeedback()

    init {
        observeConnectivity()
            .onEach { online -> _state.update { it.copy(isOnline = online) } }
            .launchIn(viewModelScope)

        observeConnectivity()
            .filter { it }
            .onEach {
                syncPendingOrders().collect()
            }
            .launchIn(viewModelScope)

        observeCart()
            .onEach { items ->
                val count = items.sumOf { it.quantity }
                _state.update { it.copy(cartCount = count) }
            }
            .launchIn(viewModelScope)
    }
}
