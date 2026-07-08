package com.ayham.postask.presentation.cart

import androidx.lifecycle.viewModelScope
import com.ayham.postask.domain.usecase.CalculateCartTotalsUseCase
import com.ayham.postask.domain.usecase.ChangeCartQuantityUseCase
import com.ayham.postask.domain.usecase.CheckoutUseCase
import com.ayham.postask.domain.usecase.ObserveCartUseCase
import com.ayham.postask.domain.usecase.ObserveConnectivityUseCase
import com.ayham.postask.domain.usecase.RemoveFromCartUseCase
import com.ayham.postask.domain.usecase.SyncPendingOrdersUseCase
import com.ayham.postask.presentation.base.BaseViewModel
import com.ayham.postask.util.ResultWrapper
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CartViewModel(
    private val observeCart: ObserveCartUseCase,
    private val calculateTotals: CalculateCartTotalsUseCase,
    private val changeQuantity: ChangeCartQuantityUseCase,
    private val removeFromCart: RemoveFromCartUseCase,
    private val checkout: CheckoutUseCase,
    private val syncPendingOrders: SyncPendingOrdersUseCase,
    private val observeConnectivity: ObserveConnectivityUseCase,
) : BaseViewModel() {

    private val _state = MutableStateFlow(CartState())
    val state: StateFlow<CartState> = _state.asStateFlow()

    private val _messages = Channel<CartMessage>(Channel.BUFFERED)
    val messages: Flow<CartMessage> = _messages.receiveAsFlow()

    init {
        observeCart()
            .onEach { items ->
                _state.update { it.copy(items = items, totals = calculateTotals(items)) }
            }
            .launchIn(viewModelScope)
    }

    fun onEvent(event: CartEvent) {
        when (event) {
            is CartEvent.OnIncrease -> changeQuantityBy(event.productId, delta = 1)
            is CartEvent.OnDecrease -> changeQuantityBy(event.productId, delta = -1)
            is CartEvent.OnRemove -> removeFromCart(event.productId)
            CartEvent.OnCheckout -> runCheckout()
        }
    }

    private fun changeQuantityBy(productId: String, delta: Int) {
        val item = _state.value.items.firstOrNull { it.product.id == productId } ?: return
        changeQuantity(productId, item.quantity + delta)
    }

    private fun runCheckout() {
        if (_state.value.isEmpty || _state.value.isCheckingOut) return
        viewModelScope.launch {
            _state.update { it.copy(isCheckingOut = true) }
            when (val result = checkout()) {
                is ResultWrapper.Success -> {
                    if (observeConnectivity().value) {
                        syncPendingOrders().collect()
                        _messages.send(CartMessage.SavedOnline)
                    } else {
                        _messages.send(CartMessage.SavedOffline)
                    }
                }
                is ResultWrapper.Error -> emitError(result.error)
                ResultWrapper.Loading -> Unit
            }
            _state.update { it.copy(isCheckingOut = false) }
        }
    }
}
