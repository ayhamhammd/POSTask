package com.ayham.postask.domain.usecase

import com.ayham.postask.domain.model.ProductModel
import com.ayham.postask.domain.repository.CatalogRepository
import com.ayham.postask.util.ResultWrapper
import kotlinx.coroutines.flow.Flow

class GetCatalogUseCase(private val catalogRepository: CatalogRepository) {
    operator fun invoke(): Flow<ResultWrapper<List<ProductModel>>> = catalogRepository.getProducts()
}
