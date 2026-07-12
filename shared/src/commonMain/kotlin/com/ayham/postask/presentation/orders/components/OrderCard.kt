package com.ayham.postask.presentation.orders.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ayham.postask.domain.model.OrderModel
import com.ayham.postask.presentation.components.StatusChip
import com.ayham.postask.presentation.components.app_text.AppText
import com.ayham.postask.presentation.components.app_text.AppTextStyle
import com.ayham.postask.util.format.toDateTimeLabel
import com.ayham.postask.util.format.toMoneyLabel
import com.ayham.postask.presentation.theme.AppTheme
import com.ayham.postask.presentation.theme.Dimens
import org.jetbrains.compose.resources.stringResource
import postask.shared.generated.resources.Res
import postask.shared.generated.resources.badge_pending
import postask.shared.generated.resources.badge_synced
import postask.shared.generated.resources.order_items
import postask.shared.generated.resources.order_reference

@Composable
fun OrderCard(
    order: OrderModel,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.Radius.SemiLarge),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = Dimens.Elevation.XSmall),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.Spacing.M),
            verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.SM),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimens.Spacing.S),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.XXS),
                ) {
                    AppText(
                        text = stringResource(Res.string.order_reference, order.id.take(8)),
                        style = AppTextStyle.Title.SmallEmphasized,
                    )
                    AppText(
                        text = order.createdAtEpochMillis.toDateTimeLabel(),
                        style = AppTextStyle.Body.Small,
                        color = AppTheme.extendedColors.textSecondary,
                    )
                }
                OrderStatusChip(order = order)
            }
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AppText(
                    text = stringResource(Res.string.order_items, order.itemCount),
                    style = AppTextStyle.Body.Small,
                    color = AppTheme.extendedColors.textSecondary,
                )
                AppText(
                    text = order.totals.totalCents.toMoneyLabel(),
                    style = AppTextStyle.Title.SmallEmphasized,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
            order.syncedAtEpochMillis?.let { syncedAt ->
                AppText(
                    text = syncedAt.toDateTimeLabel(),
                    style = AppTextStyle.Label.Small,
                    color = AppTheme.extendedColors.success,
                )
            }
        }
    }
}

@Composable
private fun OrderStatusChip(order: OrderModel) {
    if (order.isPending) {
        StatusChip(
            text = stringResource(Res.string.badge_pending),
            containerColor = AppTheme.extendedColors.warningContainer,
            contentColor = AppTheme.extendedColors.warning,
        )
    } else {
        StatusChip(
            text = stringResource(Res.string.badge_synced),
            containerColor = AppTheme.extendedColors.successContainer,
            contentColor = AppTheme.extendedColors.success,
        )
    }
}
