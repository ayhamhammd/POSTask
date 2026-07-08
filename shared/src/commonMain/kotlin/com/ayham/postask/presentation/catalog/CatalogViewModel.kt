package com.ayham.postask.presentation.catalog

import com.ayham.postask.domain.usecase.AddToCartUseCase
import com.ayham.postask.domain.usecase.GetCatalogUseCase
import com.ayham.postask.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CatalogViewModel(
    private val getCatalog: GetCatalogUseCase,
    private val addToCart: AddToCartUseCase,
) : BaseViewModel() {

    private val _state = MutableStateFlow(CatalogState())
    val state: StateFlow<CatalogState> = _state.asStateFlow()

    init {
        getCatalog().collectResult { products ->
            _state.update { it.copy(products = products) }
        }
    }

    fun onEvent(event: CatalogEvent) {
        when (event) {
            is CatalogEvent.OnAddToCart -> addProduct(event.productId)
        }
    }

    private fun addProduct(productId: String) {
        val product = _state.value.products.firstOrNull { it.id == productId } ?: return
        addToCart(product)
    }
}
