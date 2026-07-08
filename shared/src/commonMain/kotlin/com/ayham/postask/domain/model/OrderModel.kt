package com.ayham.postask.domain.model

data class OrderModel(
    val id: String,
    val createdAtEpochMillis: Long,
    val syncedAtEpochMillis: Long?,
    val totals: CartTotalsModel,
    val lines: List<OrderLineModel>,
) {
    val isPending: Boolean get() = syncedAtEpochMillis == null
    val itemCount: Int get() = lines.sumOf { it.quantity }
}
