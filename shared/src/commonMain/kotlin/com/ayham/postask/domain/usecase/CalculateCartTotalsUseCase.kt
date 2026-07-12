package com.ayham.postask.domain.usecase

import com.ayham.postask.domain.model.CartItemModel
import com.ayham.postask.domain.model.CartTotalsModel

class CalculateCartTotalsUseCase {

    operator fun invoke(items: List<CartItemModel>): CartTotalsModel {
        val subtotal = items.sumOf { it.lineTotalCents }
        val taxableSubtotal = items.sumOf { it.taxableCents }
        val tax = applyRate(taxableSubtotal, TAX_RATE_PERCENT)
        val discount = if (subtotal >= DISCOUNT_THRESHOLD_CENTS) {
            applyRate(subtotal, DISCOUNT_RATE_PERCENT)
        } else {
            0L
        }
        return CartTotalsModel(
            subtotalCents = subtotal,
            taxCents = tax,
            discountCents = discount,
            totalCents = subtotal + tax - discount,
        )
    }

    private fun applyRate(amountCents: Long, ratePercent: Long): Long =
        (amountCents * ratePercent + HALF_UP_OFFSET) / PERCENT_BASE
        // 25.94$ gose 2594*10 = 25940+50-> 25990/100 = 259.9 -> 2.59

    private companion object {
        const val TAX_RATE_PERCENT = 10L
        const val DISCOUNT_RATE_PERCENT = 5L
        const val DISCOUNT_THRESHOLD_CENTS = 5_000L
        const val PERCENT_BASE = 100L
        const val HALF_UP_OFFSET = 50L
    }
}
