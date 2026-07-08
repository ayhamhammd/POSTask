package com.ayham.postask.presentation.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class ExtendedColors(
    val success: Color,
    val onSuccess: Color,
    val successContainer: Color,
    val warning: Color,
    val onWarning: Color,
    val warningContainer: Color,
    val info: Color,
    val infoContainer: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val surfaceMuted: Color,
)

val LightExtendedColors = ExtendedColors(
    success = ColorTokens.Success100,
    onSuccess = ColorTokens.White,
    successContainer = ColorTokens.Success10,
    warning = ColorTokens.Warning100,
    onWarning = ColorTokens.Black,
    warningContainer = ColorTokens.Warning10,
    info = ColorTokens.Info100,
    infoContainer = ColorTokens.Info10,
    textPrimary = ColorTokens.TextPrimary,
    textSecondary = ColorTokens.TextSecondary,
    surfaceMuted = ColorTokens.Gray100,
)

val LocalExtendedColors = staticCompositionLocalOf { LightExtendedColors }
