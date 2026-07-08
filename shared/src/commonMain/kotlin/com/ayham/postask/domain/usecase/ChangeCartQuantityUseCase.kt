package com.ayham.postask.domain.usecase

import com.ayham.postask.domain.repository.CartRepository

class ChangeCartQuantityUseCase(private val cartRepository: CartRepository) {
    operator fun invoke(productId: String, quantity: Int) =
        cartRepository.setQuantity(productId, quantity)
}
