package com.mscode.playercard.view.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import playercard.view.generated.resources.Res
import playercard.view.generated.resources.agbalumo_regular
import org.jetbrains.compose.resources.Font
import androidx.compose.material3.Typography

internal val displayFontFamily
    @Composable
    get() = FontFamily(Font(Res.font.agbalumo_regular))

private val baseline = Typography()

internal val materialTypography
    @Composable
    get() = Typography(
    displayLarge = baseline.displayLarge.copy(fontFamily = displayFontFamily),
    displayMedium = baseline.displayMedium.copy(fontFamily = displayFontFamily),
    displaySmall = baseline.displaySmall.copy(fontFamily = displayFontFamily),
    headlineLarge = baseline.headlineLarge.copy(fontFamily = displayFontFamily),
    headlineMedium = baseline.headlineMedium.copy(fontFamily = displayFontFamily),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = displayFontFamily),
    titleLarge = baseline.titleLarge.copy(fontFamily = displayFontFamily),
    titleMedium = baseline.titleMedium.copy(fontFamily = displayFontFamily),
    titleSmall = baseline.titleSmall.copy(fontFamily = displayFontFamily),
)
