package com.ayham.postask.domain.usecase

import com.ayham.postask.domain.repository.CartRepository

class RemoveFromCartUseCase(private val cartRepository: CartRepository) {
    operator fun invoke(productId: String) = cartRepository.remove(productId)
}
