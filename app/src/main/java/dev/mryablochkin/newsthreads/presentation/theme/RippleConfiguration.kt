@file:OptIn(ExperimentalMaterial3Api::class)

package dev.mryablochkin.newsthreads.presentation.theme

import android.os.Build
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RippleConfiguration
import androidx.compose.material3.RippleDefaults
import androidx.compose.runtime.Immutable

internal val DefaultRippleConfiguration = RippleConfiguration(
    rippleAlpha = NewsThreadsRipple.rippleAlpha
)

@Immutable
private object NewsThreadsRipple {
    val rippleAlpha = when (Build.VERSION.SDK_INT) {
        Build.VERSION_CODES.S -> RippleAlpha(0.16f, 0.1f, 0.08f, 0.25f)
        Build.VERSION_CODES.UPSIDE_DOWN_CAKE -> RippleAlpha(0.16f, 0.1f, 0.08f, 0.25f)
        else -> RippleDefaults.RippleAlpha
    }
}