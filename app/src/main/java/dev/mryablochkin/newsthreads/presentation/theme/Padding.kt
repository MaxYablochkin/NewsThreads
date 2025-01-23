package dev.mryablochkin.newsthreads.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val MaterialTheme.paddings: Padding
    @Composable
    @ReadOnlyComposable
    get() = LocalPadding.current

@Immutable
data class Padding(
    val small: Dp = 5.dp,
    val medium: Dp = 10.dp,
    val large: Dp = 16.dp,
    val extraLarge: Dp = 20.dp,
)

internal val LocalPadding = staticCompositionLocalOf { Padding() }