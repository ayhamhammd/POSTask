package com.ayham.postask.presentation.catalog

import com.ayham.postask.domain.model.ProductModel

data class CatalogState(
    val products: List<ProductModel> = emptyList(),
)
