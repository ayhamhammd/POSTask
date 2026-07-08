package com.ayham.postask.domain.repository

import com.ayham.postask.domain.model.ProductModel
import com.ayham.postask.util.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface CatalogRepository {
    fun getProducts(): Flow<ResultWrapper<List<ProductModel>>>
}
