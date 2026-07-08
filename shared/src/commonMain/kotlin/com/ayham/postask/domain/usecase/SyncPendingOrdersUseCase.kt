package com.ayham.postask.domain.usecase

import com.ayham.postask.domain.model.SyncSummaryModel
import com.ayham.postask.domain.repository.OrderRepository
import com.ayham.postask.util.ResultWrapper
import kotlinx.coroutines.flow.Flow

class SyncPendingOrdersUseCase(private val orderRepository: OrderRepository) {
    operator fun invoke(): Flow<ResultWrapper<SyncSummaryModel>> = orderRepository.syncPending()
}
