package com.ayham.postask.domain.repository

import com.ayham.postask.domain.model.CartItemModel
import com.ayham.postask.domain.model.ProductModel
import kotlinx.coroutines.flow.StateFlow

interface CartRepository {
    val items: StateFlow<List<CartItemModel>>
    fun add(product: ProductModel)
    fun setQuantity(productId: String, quantity: Int)
    fun remove(productId: String)
    fun clear()
}
