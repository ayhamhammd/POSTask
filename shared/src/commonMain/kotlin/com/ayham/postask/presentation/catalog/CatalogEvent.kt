package com.ayham.postask.presentation.catalog

sealed interface CatalogEvent {
    data class OnAddToCart(val productId: String) : CatalogEvent
}
