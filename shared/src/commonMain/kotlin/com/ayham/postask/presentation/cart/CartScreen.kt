package com.ayham.postask.presentation.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ayham.postask.presentation.base.BaseScreen
import com.ayham.postask.presentation.cart.components.CartLineRow
import com.ayham.postask.presentation.cart.components.TotalsSummary
import com.ayham.postask.presentation.components.EmptyState
import com.ayham.postask.presentation.components.app_button.AppButton
import com.ayham.postask.presentation.components.app_button.AppButtonSize
import com.ayham.postask.presentation.theme.Dimens
import com.ayham.postask.presentation.theme.PosIcons
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import postask.shared.generated.resources.Res
import postask.shared.generated.resources.cart_empty
import postask.shared.generated.resources.checkout
import postask.shared.generated.resources.order_saved_offline
import postask.shared.generated.resources.order_saved_online

@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    viewModel: CartViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val savedOfflineText = stringResource(Res.string.order_saved_offline)
    val savedOnlineText = stringResource(Res.string.order_saved_online)

    LaunchedEffect(Unit) {
        viewModel.messages.collect { message ->
            snackbarHostState.showSnackbar(
                when (message) {
                    CartMessage.SavedOffline -> savedOfflineText
                    CartMessage.SavedOnline -> savedOnlineText
                }
            )
        }
    }

    BaseScreen(viewModel = viewModel, modifier = modifier) {
        Box(modifier = Modifier.fillMaxSize()) {
            CartContent(state = state, onEvent = viewModel::onEvent)
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.align(Alignment.BottomCenter),
            )
        }
    }
}

@Composable
private fun CartContent(
    state: CartState,
    onEvent: (CartEvent) -> Unit,
) {
    if (state.isEmpty) {
        EmptyState(
            icon = PosIcons.Cart,
            message = stringResource(Res.string.cart_empty),
        )
        return
    }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(Dimens.Spacing.M),
            verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.M),
        ) {
            items(state.items, key = { it.product.id }) { item ->
                CartLineRow(item = item, onEvent = onEvent)
            }
        }
        Surface(
            tonalElevation = Dimens.Elevation.Small,
            shadowElevation = Dimens.Elevation.Medium,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.Spacing.M),
                verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.M),
            ) {
                TotalsSummary(totals = state.totals)
                AppButton(
                    text = stringResource(Res.string.checkout),
                    onClick = { onEvent(CartEvent.OnCheckout) },
                    modifier = Modifier.fillMaxWidth(),
                    size = AppButtonSize.Large,
                    loading = state.isCheckingOut,
                )
            }
        }
    }
}
