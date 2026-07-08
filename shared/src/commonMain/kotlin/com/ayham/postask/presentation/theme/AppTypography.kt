package com.ayham.postask.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Immutable
data class ExtendedTypography(
    val headlineLarge: TextStyle,
    val headlineLargeEmphasized: TextStyle,
    val headlineMedium: TextStyle,
    val headlineMediumEmphasized: TextStyle,
    val headlineSmall: TextStyle,
    val headlineSmallEmphasized: TextStyle,
    val titleLarge: TextStyle,
    val titleLargeEmphasized: TextStyle,
    val titleMedium: TextStyle,
    val titleMediumEmphasized: TextStyle,
    val titleSmall: TextStyle,
    val titleSmallEmphasized: TextStyle,
    val labelLarge: TextStyle,
    val labelLargeEmphasized: TextStyle,
    val labelMedium: TextStyle,
    val labelMediumEmphasized: TextStyle,
    val labelSmall: TextStyle,
    val labelSmallEmphasized: TextStyle,
    val bodyLarge: TextStyle,
    val bodyLargeEmphasized: TextStyle,
    val bodyMedium: TextStyle,
    val bodyMediumEmphasized: TextStyle,
    val bodySmall: TextStyle,
    val bodySmallEmphasized: TextStyle,
)

val LocalExtendedTypography = staticCompositionLocalOf<ExtendedTypography> {
    error("ExtendedTypography not provided")
}

private fun textStyle(
    fontFamily: FontFamily,
    fontSize: TextUnit,
    lineHeight: TextUnit,
    fontWeight: FontWeight,
) = TextStyle(
    fontFamily = fontFamily,
    fontWeight = fontWeight,
    fontSize = fontSize,
    lineHeight = lineHeight,
)

fun extendedTypography(fontFamily: FontFamily) = ExtendedTypography(
    headlineLarge = textStyle(fontFamily, 32.sp, 40.sp, FontWeight.Normal),
    headlineLargeEmphasized = textStyle(fontFamily, 32.sp, 40.sp, FontWeight.Bold),
    headlineMedium = textStyle(fontFamily, 28.sp, 36.sp, FontWeight.Normal),
    headlineMediumEmphasized = textStyle(fontFamily, 28.sp, 36.sp, FontWeight.Bold),
    headlineSmall = textStyle(fontFamily, 24.sp, 32.sp, FontWeight.Normal),
    headlineSmallEmphasized = textStyle(fontFamily, 24.sp, 32.sp, FontWeight.Bold),
    titleLarge = textStyle(fontFamily, 22.sp, 28.sp, FontWeight.Normal),
    titleLargeEmphasized = textStyle(fontFamily, 22.sp, 28.sp, FontWeight.Bold),
    titleMedium = textStyle(fontFamily, 16.sp, 24.sp, FontWeight.Medium),
    titleMediumEmphasized = textStyle(fontFamily, 16.sp, 24.sp, FontWeight.Bold),
    titleSmall = textStyle(fontFamily, 14.sp, 20.sp, FontWeight.Medium),
    titleSmallEmphasized = textStyle(fontFamily, 14.sp, 20.sp, FontWeight.Bold),
    labelLarge = textStyle(fontFamily, 14.sp, 20.sp, FontWeight.Medium),
    labelLargeEmphasized = textStyle(fontFamily, 14.sp, 20.sp, FontWeight.Bold),
    labelMedium = textStyle(fontFamily, 12.sp, 16.sp, FontWeight.Medium),
    labelMediumEmphasized = textStyle(fontFamily, 12.sp, 16.sp, FontWeight.Bold),
    labelSmall = textStyle(fontFamily, 11.sp, 16.sp, FontWeight.Medium),
    labelSmallEmphasized = textStyle(fontFamily, 11.sp, 16.sp, FontWeight.Bold),
    bodyLarge = textStyle(fontFamily, 16.sp, 24.sp, FontWeight.Normal),
    bodyLargeEmphasized = textStyle(fontFamily, 16.sp, 24.sp, FontWeight.Bold),
    bodyMedium = textStyle(fontFamily, 14.sp, 20.sp, FontWeight.Normal),
    bodyMediumEmphasized = textStyle(fontFamily, 14.sp, 20.sp, FontWeight.Bold),
    bodySmall = textStyle(fontFamily, 12.sp, 16.sp, FontWeight.Normal),
    bodySmallEmphasized = textStyle(fontFamily, 12.sp, 16.sp, FontWeight.Bold),
)

fun appTypography(fontFamily: FontFamily) = Typography(
    headlineLarge = textStyle(fontFamily, 32.sp, 40.sp, FontWeight.Normal),
    headlineMedium = textStyle(fontFamily, 28.sp, 36.sp, FontWeight.Normal),
    headlineSmall = textStyle(fontFamily, 24.sp, 32.sp, FontWeight.Normal),
    titleLarge = textStyle(fontFamily, 22.sp, 28.sp, FontWeight.Normal),
    titleMedium = textStyle(fontFamily, 16.sp, 24.sp, FontWeight.Medium),
    titleSmall = textStyle(fontFamily, 14.sp, 20.sp, FontWeight.Medium),
    bodyLarge = textStyle(fontFamily, 16.sp, 24.sp, FontWeight.Normal),
    bodyMedium = textStyle(fontFamily, 14.sp, 20.sp, FontWeight.Normal),
    bodySmall = textStyle(fontFamily, 12.sp, 16.sp, FontWeight.Normal),
    labelLarge = textStyle(fontFamily, 14.sp, 20.sp, FontWeight.Medium),
    labelMedium = textStyle(fontFamily, 12.sp, 16.sp, FontWeight.Medium),
    labelSmall = textStyle(fontFamily, 11.sp, 16.sp, FontWeight.Medium),
)
