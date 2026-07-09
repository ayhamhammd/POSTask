package com.ayham.postask.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.ayham.postask.data.local.PosDatabase
import com.ayham.postask.data.local.mapper.OrderRow
import com.ayham.postask.data.local.mapper.mapOrderRow
import com.ayham.postask.data.local.mapper.toOrderModels
import com.ayham.postask.data.remote.api.OrdersApi
import com.ayham.postask.data.remote.mapper.toSyncRequest
import com.ayham.postask.domain.model.OrderModel
import com.ayham.postask.domain.model.SyncFeedback
import com.ayham.postask.domain.model.SyncSummaryModel
import com.ayham.postask.domain.repository.OrderRepository
import com.ayham.postask.network.model.NetworkResult
import com.ayham.postask.util.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlin.time.Clock

class OrderRepositoryImpl(
    private val database: PosDatabase,
    private val ordersApi: OrdersApi,
) : OrderRepository {

    private val queries = database.pendingOrdersQueries

    private val _syncFeedback = MutableSharedFlow<SyncFeedback>(extraBufferCapacity = 8)
    override val syncFeedback: SharedFlow<SyncFeedback> = _syncFeedback.asSharedFlow()

    override fun observeOrders(): Flow<ResultWrapper<List<OrderModel>>> =
        queries.selectOrdersWithLines(::mapOrderRow)
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map<List<OrderRow>, ResultWrapper<List<OrderModel>>> { rows ->
                ResultWrapper.Success(rows.toOrderModels())
            }
            .onStart { emit(ResultWrapper.Loading) }
            .catch { emit(ResultWrapper.Error(it)) }

    override fun saveOrder(order: OrderModel): Flow<ResultWrapper<Unit>> = flow {
        emit(ResultWrapper.Loading)
        database.transaction {
            queries.insertOrder(
                order.id,
                order.createdAtEpochMillis,
                order.totals.subtotalCents,
                order.totals.taxCents,
                order.totals.discountCents,
                order.totals.totalCents,
            )
            order.lines.forEach { line ->
                queries.insertOrderLine(
                    order.id,
                    line.productId,
                    line.productName,
                    line.unitPriceCents,
                    line.quantity.toLong(),
                    line.taxable,
                )
            }
        }
        emit(ResultWrapper.Success(Unit))
    }.catch { emit(ResultWrapper.Error(it)) }.flowOn(Dispatchers.IO)

    override fun syncPending(): Flow<ResultWrapper<SyncSummaryModel>> = flow {
        emit(ResultWrapper.Loading)
        val pending = queries.selectPendingOrdersWithLines(::mapOrderRow).executeAsList().toOrderModels()
        if (pending.isEmpty()) {
            emit(ResultWrapper.Success(SyncSummaryModel(syncedCount = 0, failedCount = 0)))
            return@flow
        }

        delay(SYNC_LATENCY_MS)
        var synced = 0
        var failed = 0
        for (order in pending) {
            if (syncWithRetry(order)) synced++ else failed++
        }
        if (synced > 0) _syncFeedback.tryEmit(SyncFeedback.Synced(synced))
        emit(ResultWrapper.Success(SyncSummaryModel(syncedCount = synced, failedCount = failed)))
    }.catch { emit(ResultWrapper.Error(it)) }.flowOn(Dispatchers.IO)

    private suspend fun syncWithRetry(order: OrderModel): Boolean {
        repeat(MAX_SYNC_ATTEMPTS) { index ->
            val attempt = index + 1
            when (val result = ordersApi.syncOrder(order.toSyncRequest())) {
                is NetworkResult.Success -> {
                    queries.markSynced(Clock.System.now().toEpochMilliseconds(), order.id)
                    return true
                }

                is NetworkResult.Error -> {
                    if (attempt < MAX_SYNC_ATTEMPTS) {
                        _syncFeedback.tryEmit(SyncFeedback.Retrying)
                        delay(RETRY_DELAY_MS)
                    }
                }
            }
        }
        return false
    }

    private companion object {
        const val MAX_SYNC_ATTEMPTS = 3
        const val RETRY_DELAY_MS = 1_200L
        const val SYNC_LATENCY_MS = 600L
    }
}
