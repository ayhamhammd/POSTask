package com.ayham.postask.presentation.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ayham.postask.presentation.components.app_button.AppButton
import com.ayham.postask.presentation.components.app_button.AppButtonVariant
import com.ayham.postask.presentation.components.app_text.AppText
import com.ayham.postask.presentation.components.app_text.AppTextStyle
import com.ayham.postask.presentation.theme.Dimens
import org.jetbrains.compose.resources.stringResource
import postask.shared.generated.resources.Res
import postask.shared.generated.resources.action_ok
import postask.shared.generated.resources.error_generic
import postask.shared.generated.resources.error_title

@Composable
fun BaseScreen(
    viewModel: BaseViewModel,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val error by viewModel.error.collectAsStateWithLifecycle()

    Box(modifier = modifier.fillMaxSize()) {
        content()

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = Dimens.Opacity.MEDIUM)),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }

        error?.let { throwable ->
            AlertDialog(
                onDismissRequest = viewModel::clearError,
                title = {
                    AppText(
                        text = stringResource(Res.string.error_title),
                        style = AppTextStyle.Title.MediumEmphasized,
                    )
                },
                text = {
                    AppText(
                        text = throwable.message ?: stringResource(Res.string.error_generic),
                        style = AppTextStyle.Body.Medium,
                    )
                },
                confirmButton = {
                    AppButton(
                        text = stringResource(Res.string.action_ok),
                        onClick = viewModel::clearError,
                        variant = AppButtonVariant.Text,
                    )
                },
            )
        }
    }
}
