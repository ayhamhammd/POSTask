package com.ayham.postask.presentation.cart.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ayham.postask.domain.model.CartTotalsModel
import com.ayham.postask.presentation.components.app_text.AppText
import com.ayham.postask.presentation.components.app_text.AppTextStyle
import com.ayham.postask.presentation.components.app_text.TextStyleConfig
import com.ayham.postask.presentation.format.toMoneyLabel
import com.ayham.postask.presentation.theme.AppTheme
import com.ayham.postask.presentation.theme.Dimens
import org.jetbrains.compose.resources.stringResource
import postask.shared.generated.resources.Res
import postask.shared.generated.resources.discount
import postask.shared.generated.resources.subtotal
import postask.shared.generated.resources.tax
import postask.shared.generated.resources.total

@Composable
fun TotalsSummary(
    totals: CartTotalsModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.S),
    ) {
        TotalRow(
            label = stringResource(Res.string.subtotal),
            value = totals.subtotalCents.toMoneyLabel(),
        )
        TotalRow(
            label = stringResource(Res.string.tax),
            value = totals.taxCents.toMoneyLabel(),
        )
        if (totals.discountCents > 0) {
            TotalRow(
                label = stringResource(Res.string.discount),
                value = (-totals.discountCents).toMoneyLabel(),
                valueColor = AppTheme.extendedColors.success,
            )
        }
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        TotalRow(
            label = stringResource(Res.string.total),
            value = totals.totalCents.toMoneyLabel(),
            labelStyle = AppTextStyle.Title.MediumEmphasized,
            valueStyle = AppTextStyle.Title.MediumEmphasized,
        )
    }
}

@Composable
private fun TotalRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    labelStyle: TextStyleConfig = AppTextStyle.Body.Medium,
    valueStyle: TextStyleConfig = AppTextStyle.Body.MediumEmphasized,
    valueColor: Color = Color.Unspecified,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        AppText(text = label, style = labelStyle)
        AppText(text = value, style = valueStyle, color = valueColor)
    }
}
