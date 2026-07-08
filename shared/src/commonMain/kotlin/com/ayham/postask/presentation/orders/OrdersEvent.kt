package com.ayham.postask.presentation.orders

sealed interface OrdersEvent {
    data object OnSyncClicked : OrdersEvent
}
