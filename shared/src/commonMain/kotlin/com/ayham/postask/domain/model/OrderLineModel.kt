package com.ayham.postask.domain.model

data class OrderLineModel(
    val productId: String,
    val productName: String,
    val unitPriceCents: Long,
    val quantity: Int,
    val taxable: Boolean,
)
