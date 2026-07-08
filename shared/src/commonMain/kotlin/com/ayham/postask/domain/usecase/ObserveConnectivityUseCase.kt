package com.ayham.postask.domain.usecase

import com.ayham.postask.domain.repository.ConnectivityRepository
import kotlinx.coroutines.flow.StateFlow

class ObserveConnectivityUseCase(private val connectivityRepository: ConnectivityRepository) {
    operator fun invoke(): StateFlow<Boolean> = connectivityRepository.isOnline
}
