package com.ayham.postask.presentation.app

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ayham.postask.presentation.components.ConnectivityStatus
import com.ayham.postask.presentation.components.app_text.AppText
import com.ayham.postask.presentation.components.app_text.AppTextStyle
import com.ayham.postask.presentation.theme.Dimens
import org.jetbrains.compose.resources.stringResource
import postask.shared.generated.resources.Res
import postask.shared.generated.resources.app_name

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PosTopBar(state: AppState) {
    TopAppBar(
        title = {
            AppText(
                text = stringResource(Res.string.app_name),
                style = AppTextStyle.Title.LargeEmphasized,
            )
        },
        actions = {
            ConnectivityStatus(
                isOnline = state.isOnline,
                modifier = Modifier.padding(end = Dimens.Spacing.M),
            )
        },
    )
}
