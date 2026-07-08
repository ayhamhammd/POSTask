package com.ayham.postask.data.repository

import com.ayham.postask.domain.repository.ConnectivityRepository
import dev.jordond.connectivity.Connectivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ConnectivityRepositoryImpl : ConnectivityRepository {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private val connectivity = Connectivity { autoStart = true }

    override val isOnline: StateFlow<Boolean> =
        connectivity.statusUpdates
            .map { it.isConnected }
            .stateIn(scope, SharingStarted.Eagerly, false)
}
