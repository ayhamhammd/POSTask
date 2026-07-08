package com.ayham.postask.data.remote.mapper

import com.ayham.postask.data.remote.dto.OrderLineDto
import com.ayham.postask.data.remote.dto.OrderSyncRequestDto
import com.ayham.postask.domain.model.OrderLineModel
import com.ayham.postask.domain.model.OrderModel

fun OrderModel.toSyncRequest(): OrderSyncRequestDto = OrderSyncRequestDto(
    id = id,
    createdAt = createdAtEpochMillis,
    subtotalCents = totals.subtotalCents,
    taxCents = totals.taxCents,
    discountCents = totals.discountCents,
    totalCents = totals.totalCents,
    lines = lines.map { it.toDto() },
)

private fun OrderLineModel.toDto(): OrderLineDto = OrderLineDto(
    productId = productId,
    productName = productName,
    unitPriceCents = unitPriceCents,
    quantity = quantity,
    taxable = taxable,
)
