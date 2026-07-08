package com.ayham.postask.data.remote.api

import com.ayham.postask.data.remote.dto.ProductDto
import com.ayham.postask.network.api.ApiService
import com.ayham.postask.network.constant.Endpoints
import com.ayham.postask.network.model.NetworkResult
import io.ktor.http.HttpMethod

class CatalogApi(private val apiService: ApiService) {
    suspend fun getProducts(): NetworkResult<List<ProductDto>> =
        apiService.request<Unit, List<ProductDto>>(
            method = HttpMethod.Get,
            endpoint = Endpoints.Catalog.PRODUCTS,
        )
}
