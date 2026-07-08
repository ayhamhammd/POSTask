package com.ayham.postask.presentation.orders

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ayham.postask.presentation.base.BaseScreen
import com.ayham.postask.presentation.components.EmptyState
import com.ayham.postask.presentation.components.app_button.AppButton
import com.ayham.postask.presentation.components.app_button.AppButtonSize
import com.ayham.postask.presentation.components.app_button.AppButtonVariant
import com.ayham.postask.presentation.components.app_text.AppText
import com.ayham.postask.presentation.components.app_text.AppTextStyle
import com.ayham.postask.presentation.orders.components.OrderCard
import com.ayham.postask.presentation.theme.Dimens
import com.ayham.postask.presentation.theme.PosIcons
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import postask.shared.generated.resources.Res
import postask.shared.generated.resources.orders_empty
import postask.shared.generated.resources.section_pending
import postask.shared.generated.resources.section_synced
import postask.shared.generated.resources.sync_now

@Composable
fun OrdersScreen(
    modifier: Modifier = Modifier,
    viewModel: OrdersViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    BaseScreen(viewModel = viewModel, modifier = modifier) {
        OrdersContent(state = state, onEvent = viewModel::onEvent)
    }
}

@Composable
private fun OrdersContent(
    state: OrdersState,
    onEvent: (OrdersEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (state.isEmpty) {
        EmptyState(
            icon = PosIcons.Orders,
            message = stringResource(Res.string.orders_empty),
            modifier = modifier,
        )
        return
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(Dimens.Spacing.M),
        verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.M),
    ) {
        if (state.pending.isNotEmpty()) {
            item(key = "pending-header") {
                SectionHeader(
                    title = stringResource(Res.string.section_pending, state.pending.size),
                    action = {
                        AppButton(
                            text = stringResource(Res.string.sync_now),
                            onClick = { onEvent(OrdersEvent.OnSyncClicked) },
                            variant = AppButtonVariant.Text,
                            size = AppButtonSize.Small,
                            enabled = state.canSync,
                            loading = state.isSyncing,
                            startIcon = PosIcons.Sync,
                        )
                    },
                )
            }
            items(state.pending, key = { it.id }) { order ->
                OrderCard(order = order)
            }
        }

        if (state.synced.isNotEmpty()) {
            item(key = "synced-header") {
                SectionHeader(title = stringResource(Res.string.section_synced, state.synced.size))
            }
            items(state.synced, key = { it.id }) { order ->
                OrderCard(order = order)
            }
        }
    }
}

@Composable
private fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier,
    action: (@Composable () -> Unit)? = null,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AppText(text = title, style = AppTextStyle.Title.SmallEmphasized)
        action?.invoke()
    }
}
