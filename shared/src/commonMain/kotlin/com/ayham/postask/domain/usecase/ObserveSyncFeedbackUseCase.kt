package com.ayham.postask.domain.usecase

import com.ayham.postask.domain.model.SyncFeedback
import com.ayham.postask.domain.repository.OrderRepository
import kotlinx.coroutines.flow.SharedFlow

class ObserveSyncFeedbackUseCase(private val orderRepository: OrderRepository) {
    operator fun invoke(): SharedFlow<SyncFeedback> = orderRepository.syncFeedback
}
