package com.mgm.commandcenter.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Terra Organic Palette Colors
val ForestGreen = Color(0xFF4A7C59)
val WarmCreamBg = Color(0xFFFAF6F0)
val WarmAmber = Color(0xFF705C30)
val WarmCharcoal = Color(0xFF2E3230)
val SurfaceContainer = Color(0xFFF0ECE4)
val SurfaceContainerLowest = Color(0xFFFFFFFF)
val SurfaceContainerLow = Color(0xFFF5F1EA)
val SurfaceContainerHigh = Color(0xFFEAE6DE)
val SurfaceContainerHighest = Color(0xFFE4E0D8)
val TerraOutline = Color(0xFF74796E)
val TerraOutlineVariant = Color(0xFFC4C8BC)
val TerraError = Color(0xFFB83230)
val TerraErrorContainer = Color(0xFFFFDAD8)
val PrimaryContainer = Color(0xFF78A886)
val OnPrimary = Color(0xFFFFFFFF)
val SecondaryColor = Color(0xFF6B6358)
val SecondaryContainer = Color(0xFFF0E8DB)
val OnSecondaryContainer = Color(0xFF5E5548)
val TertiaryFixed = Color(0xFFF8E0A8)
val OnTertiaryFixed = Color(0xFF221A05)
val PrimaryFixed = Color(0xFFC8E8D0)
val OnPrimaryFixedVariant = Color(0xFF2A6038)

private val TerraLightColorScheme = lightColorScheme(
    primary = ForestGreen,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = Color(0xFFD8F0DE),
    secondary = SecondaryColor,
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = SecondaryContainer,
    onSecondaryContainer = OnSecondaryContainer,
    tertiary = WarmAmber,
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFC4A66A),
    onTertiaryContainer = Color(0xFF554020),
    background = WarmCreamBg,
    onBackground = WarmCharcoal,
    surface = WarmCreamBg,
    onSurface = WarmCharcoal,
    surfaceVariant = SurfaceContainerHighest,
    onSurfaceVariant = Color(0xFF4A4E4A),
    outline = TerraOutline,
    outlineVariant = TerraOutlineVariant,
    error = TerraError,
    onError = Color(0xFFFFFFFF),
    errorContainer = TerraErrorContainer,
    onErrorContainer = Color(0xFF690005)
)

val TerraShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(24.dp)
)

val TerraTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 32.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 28.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 22.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        lineHeight = 19.sp
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,
        lineHeight = 16.sp
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = 14.sp
    )
)

@Composable
fun TerraTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = TerraLightColorScheme,
        shapes = TerraShapes,
        typography = TerraTypography,
        content = content
    )
}
