package com.ayham.postask.presentation.orders

import com.ayham.postask.domain.model.OrderModel

data class OrdersState(
    val pending: List<OrderModel> = emptyList(),
    val synced: List<OrderModel> = emptyList(),
    val isOnline: Boolean = false,
    val isSyncing: Boolean = false,
) {
    val isEmpty: Boolean get() = pending.isEmpty() && synced.isEmpty()
    val canSync: Boolean get() = isOnline && pending.isNotEmpty() && !isSyncing
}
