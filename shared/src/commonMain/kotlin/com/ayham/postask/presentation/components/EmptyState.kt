package com.ayham.postask.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import com.ayham.postask.presentation.components.app_icon.AppIcon
import com.ayham.postask.presentation.components.app_text.AppText
import com.ayham.postask.presentation.components.app_text.AppTextStyle
import com.ayham.postask.presentation.theme.AppTheme
import com.ayham.postask.presentation.theme.Dimens

@Composable
fun EmptyState(
    icon: ImageVector,
    message: String,
    modifier: Modifier = Modifier,
    action: (@Composable () -> Unit)? = null,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Dimens.Spacing.XL),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.M, Alignment.CenterVertically),
    ) {
        AppIcon(
            imageVector = icon,
            modifier = Modifier.size(Dimens.Size.Size48),
            tint = AppTheme.extendedColors.textSecondary,
        )
        AppText(
            text = message,
            style = AppTextStyle.Body.Medium,
            color = AppTheme.extendedColors.textSecondary,
            textAlign = TextAlign.Center,
        )
        action?.invoke()
    }
}
