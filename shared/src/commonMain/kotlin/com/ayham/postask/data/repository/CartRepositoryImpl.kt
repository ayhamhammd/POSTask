package com.ayham.postask.data.repository

import com.ayham.postask.domain.model.CartItemModel
import com.ayham.postask.domain.model.ProductModel
import com.ayham.postask.domain.repository.CartRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CartRepositoryImpl : CartRepository {
    private val _items = MutableStateFlow<List<CartItemModel>>(emptyList())
    override val items: StateFlow<List<CartItemModel>> = _items.asStateFlow()

    override fun add(product: ProductModel) {
        if (!product.inStock) return
        _items.update { current ->
            val existing = current.firstOrNull { it.product.id == product.id }
            if (existing == null) {
                current + CartItemModel(product, quantity = 1)
            } else {
                current.map { item ->
                    if (item.product.id == product.id) {
                        item.copy(quantity = (item.quantity + 1).coerceAtMost(product.stock))
                    } else {
                        item
                    }
                }
            }
        }
    }

    override fun setQuantity(productId: String, quantity: Int) {
        _items.update { current ->
            current.map { item ->
                if (item.product.id == productId) {
                    item.copy(quantity = quantity.coerceIn(1, item.product.stock))
                } else {
                    item
                }
            }
        }
    }

    override fun remove(productId: String) {
        _items.update { current -> current.filterNot { it.product.id == productId } }
    }

    override fun clear() {
        _items.value = emptyList()
    }
}
