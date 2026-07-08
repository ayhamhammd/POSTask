package com.ayham.postask.domain.usecase

import com.ayham.postask.domain.model.OrderModel
import com.ayham.postask.domain.repository.OrderRepository
import com.ayham.postask.util.ResultWrapper
import kotlinx.coroutines.flow.Flow

class ObserveOrdersUseCase(private val orderRepository: OrderRepository) {
    operator fun invoke(): Flow<ResultWrapper<List<OrderModel>>> = orderRepository.observeOrders()
}
