package com.ayham.postask.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.ayham.postask.presentation.components.app_text.AppText
import com.ayham.postask.presentation.components.app_text.AppTextStyle
import com.ayham.postask.presentation.theme.AppTheme
import com.ayham.postask.presentation.theme.Dimens
import org.jetbrains.compose.resources.stringResource
import postask.shared.generated.resources.Res
import postask.shared.generated.resources.status_offline
import postask.shared.generated.resources.status_online

@Composable
fun ConnectivityStatus(
    isOnline: Boolean,
    modifier: Modifier = Modifier,
) {
    val statusColor = if (isOnline) AppTheme.extendedColors.success else AppTheme.extendedColors.textSecondary
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Dimens.Spacing.XS),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(Dimens.Size.Size10)
                .clip(CircleShape)
                .background(statusColor)
        )
        AppText(
            text = stringResource(if (isOnline) Res.string.status_online else Res.string.status_offline),
            style = AppTextStyle.Label.MediumEmphasized,
        )
    }
}
