package dev.mryablochkin.newsthreads.presentation.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.mryablochkin.newsthreads.presentation.motion.transition.INITIAL_OFFSET_FACTOR
import dev.mryablochkin.newsthreads.presentation.motion.transition.materialSharedAxisXIn
import dev.mryablochkin.newsthreads.presentation.motion.transition.materialSharedAxisXOut

fun NavGraphBuilder.forwardAndBackwardComposable(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    composable(
        route = route,
        arguments = arguments,
        deepLinks = deepLinks,
        content = content,
        enterTransition = { materialSharedAxisXIn({ (it * INITIAL_OFFSET_FACTOR).toInt() }) },
        exitTransition = { materialSharedAxisXOut({ -(it * INITIAL_OFFSET_FACTOR).toInt() }) },
        popEnterTransition = { materialSharedAxisXIn({ -(it * INITIAL_OFFSET_FACTOR).toInt() }) },
        popExitTransition = { materialSharedAxisXOut({ (it * INITIAL_OFFSET_FACTOR).toInt() }) }
    )
}