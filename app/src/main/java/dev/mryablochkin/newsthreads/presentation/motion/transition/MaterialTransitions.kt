package dev.mryablochkin.newsthreads.presentation.motion.transition

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import dev.mryablochkin.newsthreads.presentation.motion.duration.MotionDurationTokens

fun materialSharedAxisXIn(
    initialOffsetX: (fullWidth: Int) -> Int,
    durationMillis: Int = MotionDurationTokens.MotionDurationMedium4,
) = slideInHorizontally(
    animationSpec = tween(durationMillis = durationMillis, easing = FastOutSlowInEasing),
    initialOffsetX = initialOffsetX
) + fadeIn(tween(durationMillis.ForIncoming, durationMillis.ForOutgoing, LinearOutSlowInEasing))

fun materialSharedAxisXOut(
    targetOffsetX: (fullWidth: Int) -> Int,
    durationMillis: Int = MotionDurationTokens.MotionDurationMedium4,
) = slideOutHorizontally(
    animationSpec = tween(durationMillis = durationMillis, easing = FastOutSlowInEasing),
    targetOffsetX = targetOffsetX
) + fadeOut(tween(durationMillis.ForOutgoing, 0, FastOutLinearInEasing))

private const val ProgressThreshold = 0.35f

private val Int.ForOutgoing: Int
    get() = (this * ProgressThreshold).toInt()

private val Int.ForIncoming: Int
    get() = this - this.ForOutgoing

internal const val INITIAL_OFFSET_FACTOR = 0.10f