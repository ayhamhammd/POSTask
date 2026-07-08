package com.ayham.postask.data.local.mapper

import com.ayham.postask.domain.model.CartTotalsModel
import com.ayham.postask.domain.model.OrderLineModel
import com.ayham.postask.domain.model.OrderModel

data class OrderRow(
    val id: String,
    val createdAt: Long,
    val syncedAt: Long?,
    val subtotalCents: Long,
    val taxCents: Long,
    val discountCents: Long,
    val totalCents: Long,
    val productId: String,
    val productName: String,
    val unitPriceCents: Long,
    val quantity: Long,
    val taxable: Boolean,
)

fun mapOrderRow(
    id: String,
    createdAt: Long,
    syncedAt: Long?,
    subtotalCents: Long,
    taxCents: Long,
    discountCents: Long,
    totalCents: Long,
    productId: String,
    productName: String,
    unitPriceCents: Long,
    quantity: Long,
    taxable: Boolean,
): OrderRow = OrderRow(
    id = id,
    createdAt = createdAt,
    syncedAt = syncedAt,
    subtotalCents = subtotalCents,
    taxCents = taxCents,
    discountCents = discountCents,
    totalCents = totalCents,
    productId = productId,
    productName = productName,
    unitPriceCents = unitPriceCents,
    quantity = quantity,
    taxable = taxable,
)

fun List<OrderRow>.toOrderModels(): List<OrderModel> =
    groupBy { it.id }.values.map { rows ->
        val head = rows.first()
        OrderModel(
            id = head.id,
            createdAtEpochMillis = head.createdAt,
            syncedAtEpochMillis = head.syncedAt,
            totals = CartTotalsModel(
                subtotalCents = head.subtotalCents,
                taxCents = head.taxCents,
                discountCents = head.discountCents,
                totalCents = head.totalCents,
            ),
            lines = rows.map { row ->
                OrderLineModel(
                    productId = row.productId,
                    productName = row.productName,
                    unitPriceCents = row.unitPriceCents,
                    quantity = row.quantity.toInt(),
                    taxable = row.taxable,
                )
            },
        )
    }
