package com.ayham.postask.presentation.catalog.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ayham.postask.domain.model.ProductModel
import com.ayham.postask.presentation.components.StatusChip
import com.ayham.postask.presentation.components.app_button.AppButton
import com.ayham.postask.presentation.components.app_button.AppButtonSize
import com.ayham.postask.presentation.components.app_text.AppText
import com.ayham.postask.presentation.components.app_text.AppTextStyle
import com.ayham.postask.presentation.format.toMoneyLabel
import com.ayham.postask.presentation.theme.AppTheme
import com.ayham.postask.presentation.theme.Dimens
import com.ayham.postask.presentation.theme.PosIcons
import org.jetbrains.compose.resources.stringResource
import postask.shared.generated.resources.Res
import postask.shared.generated.resources.add_to_cart
import postask.shared.generated.resources.in_stock
import postask.shared.generated.resources.out_of_stock
import postask.shared.generated.resources.tax_badge

@Composable
fun ProductCard(
    product: ProductModel,
    onAddToCart: () -> Unit,
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
            verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.S),
        ) {
            AppText(
                text = product.name,
                style = AppTextStyle.Title.SmallEmphasized,
                maxLines = 2,
            )
            AppText(
                text = product.priceCents.toMoneyLabel(),
                style = AppTextStyle.Title.MediumEmphasized,
                color = MaterialTheme.colorScheme.primary,
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimens.Spacing.S),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (product.taxable) {
                    StatusChip(
                        text = stringResource(Res.string.tax_badge),
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    )
                }
                AppText(
                    text = if (product.inStock) {
                        stringResource(Res.string.in_stock, product.stock)
                    } else {
                        stringResource(Res.string.out_of_stock)
                    },
                    style = AppTextStyle.Body.Small,
                    color = AppTheme.extendedColors.textSecondary,
                )
            }
            AppButton(
                text = stringResource(Res.string.add_to_cart),
                onClick = onAddToCart,
                modifier = Modifier.fillMaxWidth(),
                size = AppButtonSize.Small,
                enabled = product.inStock,
                startIcon = PosIcons.Add,
            )
        }
    }
}
