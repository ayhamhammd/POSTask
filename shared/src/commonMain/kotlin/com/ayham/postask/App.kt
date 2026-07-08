package com.ayham.postask

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.collectLatest
import com.ayham.postask.domain.model.SyncFeedback
import com.ayham.postask.presentation.app.AppViewModel
import com.ayham.postask.presentation.app.PosBottomBar
import com.ayham.postask.presentation.app.PosTopBar
import com.ayham.postask.presentation.navigation.PosNavHost
import com.ayham.postask.presentation.theme.AppTheme
import org.jetbrains.compose.resources.getString
import org.koin.compose.viewmodel.koinViewModel
import postask.shared.generated.resources.Res
import postask.shared.generated.resources.orders_synced
import postask.shared.generated.resources.sync_failed_retrying

@Composable
fun App() {
    AppTheme {
        val appViewModel: AppViewModel = koinViewModel()
        val state by appViewModel.state.collectAsStateWithLifecycle()
        val navController = rememberNavController()
        val snackbarHostState = remember { SnackbarHostState() }

        LaunchedEffect(Unit) {
            appViewModel.syncFeedback.collectLatest { feedback ->
                val message = when (feedback) {
                    SyncFeedback.Retrying -> getString(Res.string.sync_failed_retrying)
                    is SyncFeedback.Synced -> getString(Res.string.orders_synced, feedback.count)
                }
                snackbarHostState.showSnackbar(message)
            }
        }

        Scaffold(
            topBar = { PosTopBar(state = state) },
            bottomBar = { PosBottomBar(navController = navController, cartCount = state.cartCount) },
            snackbarHost = { SnackbarHost(snackbarHostState) },
        ) { padding ->
            PosNavHost(
                navController = navController,
                modifier = Modifier.padding(padding),
            )
        }
    }
}
