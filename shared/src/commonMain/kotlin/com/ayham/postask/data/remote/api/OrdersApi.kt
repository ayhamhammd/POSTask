package com.ayham.postask.data.remote.api

import com.ayham.postask.data.remote.dto.OrderSyncRequestDto
import com.ayham.postask.data.remote.dto.OrderSyncResponseDto
import com.ayham.postask.network.api.ApiService
import com.ayham.postask.network.constant.Endpoints
import com.ayham.postask.network.model.NetworkResult
import io.ktor.http.HttpMethod

class OrdersApi(private val apiService: ApiService) {
    suspend fun syncOrder(request: OrderSyncRequestDto): NetworkResult<OrderSyncResponseDto> =
        apiService.request<OrderSyncRequestDto, OrderSyncResponseDto>(
            method = HttpMethod.Post,
            endpoint = Endpoints.Orders.SYNC,
            body = request,
        )
}
