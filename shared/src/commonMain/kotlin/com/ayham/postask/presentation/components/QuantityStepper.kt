package com.ayham.postask.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import com.ayham.postask.presentation.components.app_icon.AppIcon
import com.ayham.postask.presentation.components.app_text.AppText
import com.ayham.postask.presentation.components.app_text.AppTextStyle
import com.ayham.postask.presentation.theme.Dimens
import com.ayham.postask.presentation.theme.PosIcons

@Composable
fun QuantityStepper(
    quantity: Int,
    onDecrease: () -> Unit,
    onIncrease: () -> Unit,
    modifier: Modifier = Modifier,
    canIncrease: Boolean = true,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Dimens.Spacing.SM),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        StepperButton(icon = PosIcons.Decrease, onClick = onDecrease, enabled = quantity > 1)
        AppText(
            text = quantity.toString(),
            style = AppTextStyle.Title.SmallEmphasized,
            textAlign = TextAlign.Center,
            modifier = Modifier.widthIn(min = Dimens.Size.Size24),
        )
        StepperButton(icon = PosIcons.Increase, onClick = onIncrease, enabled = canIncrease)
    }
}

@Composable
private fun StepperButton(
    icon: ImageVector,
    onClick: () -> Unit,
    enabled: Boolean,
) {
    val contentAlpha = if (enabled) Dimens.Opacity.FULL else Dimens.Opacity.DISABLED
    Row(
        modifier = Modifier
            .size(Dimens.Size.Size32)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .border(BorderStroke(Dimens.BorderWidth.Small, MaterialTheme.colorScheme.outlineVariant), CircleShape)
            .clickable(enabled = enabled, onClick = onClick),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AppIcon(
            imageVector = icon,
            modifier = Modifier.size(Dimens.Icon.Small).alpha(contentAlpha),
            tint = MaterialTheme.colorScheme.primary,
        )
    }
}
