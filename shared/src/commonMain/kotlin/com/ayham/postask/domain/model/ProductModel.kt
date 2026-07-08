package com.ayham.postask.domain.model

data class ProductModel(
    val id: String,
    val name: String,
    val priceCents: Long,
    val taxable: Boolean,
    val stock: Int,
) {
    val inStock: Boolean get() = stock > 0
}
