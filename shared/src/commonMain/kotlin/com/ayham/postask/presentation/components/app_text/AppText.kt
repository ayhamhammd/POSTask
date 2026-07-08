package com.ayham.postask.presentation.components.app_text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import com.ayham.postask.presentation.theme.AppTheme

@Composable
fun AppText(
    text: String,
    style: TextStyleConfig,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    textAlign: TextAlign? = null,
    textDecoration: TextDecoration? = null,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        style = resolveTextStyle(style),
        textAlign = textAlign,
        textDecoration = textDecoration,
        maxLines = maxLines,
        overflow = overflow,
    )
}

@Composable
private fun resolveTextStyle(config: TextStyleConfig): TextStyle {
    val typography = AppTheme.extendedTypography
    return when (config.category) {
        TextCategory.Headline -> when (config.size) {
            TextSize.Large -> if (config.emphasized) typography.headlineLargeEmphasized else typography.headlineLarge
            TextSize.Medium -> if (config.emphasized) typography.headlineMediumEmphasized else typography.headlineMedium
            TextSize.Small -> if (config.emphasized) typography.headlineSmallEmphasized else typography.headlineSmall
        }
        TextCategory.Title -> when (config.size) {
            TextSize.Large -> if (config.emphasized) typography.titleLargeEmphasized else typography.titleLarge
            TextSize.Medium -> if (config.emphasized) typography.titleMediumEmphasized else typography.titleMedium
            TextSize.Small -> if (config.emphasized) typography.titleSmallEmphasized else typography.titleSmall
        }
        TextCategory.Label -> when (config.size) {
            TextSize.Large -> if (config.emphasized) typography.labelLargeEmphasized else typography.labelLarge
            TextSize.Medium -> if (config.emphasized) typography.labelMediumEmphasized else typography.labelMedium
            TextSize.Small -> if (config.emphasized) typography.labelSmallEmphasized else typography.labelSmall
        }
        TextCategory.Body -> when (config.size) {
            TextSize.Large -> if (config.emphasized) typography.bodyLargeEmphasized else typography.bodyLarge
            TextSize.Medium -> if (config.emphasized) typography.bodyMediumEmphasized else typography.bodyMedium
            TextSize.Small -> if (config.emphasized) typography.bodySmallEmphasized else typography.bodySmall
        }
    }
}
