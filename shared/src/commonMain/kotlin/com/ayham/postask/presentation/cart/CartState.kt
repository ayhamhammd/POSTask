package com.ayham.postask.presentation.cart

import com.ayham.postask.domain.model.CartItemModel
import com.ayham.postask.domain.model.CartTotalsModel

data class CartState(
    val items: List<CartItemModel> = emptyList(),
    val totals: CartTotalsModel = CartTotalsModel.EMPTY,
    val isCheckingOut: Boolean = false,
) {
    val isEmpty: Boolean get() = items.isEmpty()
}

enum class CartMessage {
    SavedOffline,
    SavedOnline,
}
