package com.ayham.postask.domain.usecase

import com.ayham.postask.domain.model.CartItemModel
import com.ayham.postask.domain.repository.CartRepository
import kotlinx.coroutines.flow.StateFlow

class ObserveCartUseCase(private val cartRepository: CartRepository) {
    operator fun invoke(): StateFlow<List<CartItemModel>> = cartRepository.items
}
