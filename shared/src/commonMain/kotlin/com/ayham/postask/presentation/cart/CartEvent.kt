package com.ayham.postask.presentation.cart

sealed interface CartEvent {
    data class OnIncrease(val productId: String) : CartEvent
    data class OnDecrease(val productId: String) : CartEvent
    data class OnRemove(val productId: String) : CartEvent
    data object OnCheckout : CartEvent
}
