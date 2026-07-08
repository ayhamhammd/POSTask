package com.ayham.postask.domain.model

data class CartTotalsModel(
    val subtotalCents: Long,
    val taxCents: Long,
    val discountCents: Long,
    val totalCents: Long,
) {
    companion object {
        val EMPTY = CartTotalsModel(
            subtotalCents = 0,
            taxCents = 0,
            discountCents = 0,
            totalCents = 0,
        )
    }
}
