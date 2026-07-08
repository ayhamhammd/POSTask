package com.ayham.postask.data.repository

import com.ayham.postask.data.remote.api.CatalogApi
import com.ayham.postask.data.remote.mapper.toDomain
import com.ayham.postask.domain.model.ProductModel
import com.ayham.postask.domain.repository.CatalogRepository
import com.ayham.postask.util.ResultWrapper
import com.ayham.postask.util.tryRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CatalogRepositoryImpl(private val catalogApi: CatalogApi) : CatalogRepository {
    override fun getProducts(): Flow<ResultWrapper<List<ProductModel>>> = flow {
        emit(ResultWrapper.Loading)
        emit(
            tryRequest(
                request = { catalogApi.getProducts() },
                dataToDomain = { dtos -> dtos.orEmpty().map { it.toDomain() } },
            )
        )
    }
}
