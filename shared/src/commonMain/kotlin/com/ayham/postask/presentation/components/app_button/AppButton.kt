package com.ayham.postask.presentation.components.app_button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import com.ayham.postask.presentation.components.app_icon.AppIcon
import com.ayham.postask.presentation.components.app_text.AppText
import com.ayham.postask.presentation.components.app_text.AppTextStyle
import com.ayham.postask.presentation.theme.Dimens

@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: AppButtonVariant = AppButtonVariant.Primary,
    size: AppButtonSize = AppButtonSize.Medium,
    enabled: Boolean = true,
    loading: Boolean = false,
    startIcon: ImageVector? = null,
) {
    val height = when (size) {
        AppButtonSize.Small -> Dimens.Button.Height.Small
        AppButtonSize.Medium -> Dimens.Button.Height.Medium
        AppButtonSize.Large -> Dimens.Button.Height.Large
    }
    val content: @Composable () -> Unit = { ButtonContent(text, loading, startIcon) }
    val buttonModifier = modifier.heightIn(min = height)
    val isEnabled = enabled && !loading

    when (variant) {
        AppButtonVariant.Primary -> Button(
            onClick = onClick,
            modifier = buttonModifier,
            enabled = isEnabled,
        ) { content() }

        AppButtonVariant.Outline -> OutlinedButton(
            onClick = onClick,
            modifier = buttonModifier,
            enabled = isEnabled,
        ) { content() }

        AppButtonVariant.Text -> TextButton(
            onClick = onClick,
            modifier = buttonModifier,
            enabled = isEnabled,
        ) { content() }
    }
}

@Composable
private fun ButtonContent(
    text: String,
    loading: Boolean,
    startIcon: ImageVector?,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(Dimens.Spacing.S),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        when {
            loading -> CircularProgressIndicator(modifier = Modifier.size(Dimens.Icon.Medium))
            startIcon != null -> AppIcon(imageVector = startIcon, modifier = Modifier.size(Dimens.Icon.Medium))
        }
        AppText(text = text, style = AppTextStyle.Label.LargeEmphasized)
    }
}
