package com.ayham.postask.domain.model

data class CartItemModel(
    val product: ProductModel,
    val quantity: Int,
) {
    val lineTotalCents: Long get() = product.priceCents * quantity
    val taxableCents: Long get() = if (product.taxable) lineTotalCents else 0L
}
