package com.ayham.postask.domain.repository

import com.ayham.postask.domain.model.OrderModel
import com.ayham.postask.domain.model.SyncFeedback
import com.ayham.postask.domain.model.SyncSummaryModel
import com.ayham.postask.util.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface OrderRepository {
    val syncFeedback: SharedFlow<SyncFeedback>
    fun observeOrders(): Flow<ResultWrapper<List<OrderModel>>>
    fun saveOrder(order: OrderModel): Flow<ResultWrapper<Unit>>
    fun syncPending(): Flow<ResultWrapper<SyncSummaryModel>>
}
