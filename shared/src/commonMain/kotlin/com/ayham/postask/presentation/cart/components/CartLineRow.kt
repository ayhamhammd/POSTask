package com.ayham.postask.presentation.cart.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ayham.postask.domain.model.CartItemModel
import com.ayham.postask.presentation.cart.CartEvent
import com.ayham.postask.presentation.components.QuantityStepper
import com.ayham.postask.presentation.components.app_icon.AppIcon
import com.ayham.postask.presentation.components.app_text.AppText
import com.ayham.postask.presentation.components.app_text.AppTextStyle
import com.ayham.postask.presentation.format.toMoneyLabel
import com.ayham.postask.presentation.theme.AppTheme
import com.ayham.postask.presentation.theme.Dimens
import com.ayham.postask.presentation.theme.PosIcons
import org.jetbrains.compose.resources.stringResource
import postask.shared.generated.resources.Res
import postask.shared.generated.resources.remove

@Composable
fun CartLineRow(
    item: CartItemModel,
    onEvent: (CartEvent) -> Unit,
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
                        text = item.product.name,
                        style = AppTextStyle.Title.SmallEmphasized,
                        maxLines = 1,
                    )
                    AppText(
                        text = item.product.priceCents.toMoneyLabel(),
                        style = AppTextStyle.Body.Small,
                        color = AppTheme.extendedColors.textSecondary,
                    )
                }
                AppIcon(
                    imageVector = PosIcons.Remove,
                    modifier = Modifier
                        .size(Dimens.Icon.Large)
                        .clickable { onEvent(CartEvent.OnRemove(item.product.id)) },
                    tint = MaterialTheme.colorScheme.error,
                    contentDescription = stringResource(Res.string.remove),
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                QuantityStepper(
                    quantity = item.quantity,
                    onDecrease = { onEvent(CartEvent.OnDecrease(item.product.id)) },
                    onIncrease = { onEvent(CartEvent.OnIncrease(item.product.id)) },
                    canIncrease = item.quantity < item.product.stock,
                )
                AppText(
                    text = item.lineTotalCents.toMoneyLabel(),
                    style = AppTextStyle.Title.MediumEmphasized,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}
