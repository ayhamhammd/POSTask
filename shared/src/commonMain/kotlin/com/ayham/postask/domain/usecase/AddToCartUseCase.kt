package com.ayham.postask.domain.usecase

import com.ayham.postask.domain.model.ProductModel
import com.ayham.postask.domain.repository.CartRepository

class AddToCartUseCase(private val cartRepository: CartRepository) {
    operator fun invoke(product: ProductModel) = cartRepository.add(product)
}
