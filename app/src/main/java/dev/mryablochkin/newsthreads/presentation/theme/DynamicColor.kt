package dev.mryablochkin.newsthreads.presentation.theme

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
internal val DynamicColorsAvailable = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S