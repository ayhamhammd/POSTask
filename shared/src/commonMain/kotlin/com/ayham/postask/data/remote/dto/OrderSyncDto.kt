package com.ayham.postask.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class OrderSyncRequestDto(
    val id: String,
    val createdAt: Long,
    val subtotalCents: Long,
    val taxCents: Long,
    val discountCents: Long,
    val totalCents: Long,
    val lines: List<OrderLineDto>,
)

@Serializable
data class OrderLineDto(
    val productId: String,
    val productName: String,
    val unitPriceCents: Long,
    val quantity: Int,
    val taxable: Boolean,
)

@Serializable
data class OrderSyncResponseDto(
    val id: String,
    val status: String,
)
