package com.ayham.postask.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.ayham.postask.presentation.components.app_text.AppText
import com.ayham.postask.presentation.components.app_text.AppTextStyle
import com.ayham.postask.presentation.theme.Dimens

@Composable
fun StatusChip(
    text: String,
    containerColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier,
) {
    AppText(
        text = text,
        style = AppTextStyle.Label.SmallEmphasized,
        color = contentColor,
        modifier = modifier
            .clip(RoundedCornerShape(Dimens.Radius.Full))
            .background(containerColor)
            .padding(horizontal = Dimens.Spacing.S, vertical = Dimens.Spacing.XXS),
    )
}
