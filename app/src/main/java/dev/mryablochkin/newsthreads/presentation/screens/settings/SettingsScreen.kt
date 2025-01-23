@file:OptIn(ExperimentalMaterial3Api::class)
@file:RequiresApi(Build.VERSION_CODES.O)

package dev.mryablochkin.newsthreads.presentation.screens.settings

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import dev.mryablochkin.newsthreads.R
import dev.mryablochkin.newsthreads.data.local.preferences.ThemeSettings
import dev.mryablochkin.newsthreads.presentation.components.HyperlinkText2
import dev.mryablochkin.newsthreads.presentation.components.Setting
import dev.mryablochkin.newsthreads.presentation.motion.duration.MotionDurationTokens
import dev.mryablochkin.newsthreads.presentation.theme.DynamicColorsAvailable
import dev.mryablochkin.newsthreads.presentation.theme.NewsThreadsTheme
import dev.mryablochkin.newsthreads.useDarkTheme

@Composable
internal fun SettingsRoute(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val theme by viewModel.theme.collectAsState()
    val dynamicColor by viewModel.dynamicColor.collectAsState()
    val amoledColor by viewModel.amoledColor.collectAsState()
    val settingsUiState by viewModel.settingsUiState.collectAsState()

    SettingsScreen(
        modifier = modifier,
        onBackClick = onBackClick,
        visibleDialog = settingsUiState.themeDialogVisible,
        onOpenDialog = viewModel::showDialog,
        onCloseDialog = viewModel::hideDialog,
        theme = theme,
        onChangeTheme = viewModel::updateTheme,
        dynamicColor = dynamicColor,
        onChangeDynamicColor = viewModel::updateDynamicColor,
        amoledColor = amoledColor,
        onChangeAmoledColor = viewModel::updateAmoledColor
    )
}

@Composable
private fun SettingsScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    visibleDialog: Boolean,
    onOpenDialog: () -> Unit,
    onCloseDialog: () -> Unit,
    theme: ThemeSettings,
    onChangeTheme: (ThemeSettings) -> Unit,
    dynamicColor: Boolean,
    onChangeDynamicColor: () -> Unit,
    amoledColor: Boolean,
    onChangeAmoledColor: () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { SettingsTopAppBar(onBackClick = onBackClick, scrollBehavior = scrollBehavior) },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            val localHapticFeedback = LocalHapticFeedback.current
            val darkTheme = useDarkTheme(theme)

            val currentNameTheme = when (theme) {
                ThemeSettings.Light -> themeNames[0]
                ThemeSettings.Dark -> themeNames[1]
                ThemeSettings.System -> themeNames[2]
            }

            Setting(
                nameSetting = stringResource(R.string.name_setting_theme),
                primaryText = stringResource(R.string.primary_text_theme),
                secondaryText = currentNameTheme,
                onClick = onOpenDialog
            )

            ChooseThemeDialog(
                visibleDialog = visibleDialog,
                onCloseDialog = onCloseDialog,
                onChangeTheme = onChangeTheme,
                theme = theme
            )

            if (DynamicColorsAvailable) {
                Setting(
                    nameSetting = stringResource(R.string.name_setting_color),
                    primaryText = stringResource(R.string.primary_text_color),
                    secondaryText = stringResource(R.string.secondary_text_color),
                    onClick = {
                        onChangeDynamicColor()
                        localHapticFeedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    },
                    trailingContent = {
                        SettingsSwitch(
                            checked = dynamicColor,
                            onCheckedChange = {
                                onChangeDynamicColor()
                                localHapticFeedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            },
                            colors = SwitchColors
                        )
                    }
                )
            }

            AnimatedVisibility(darkTheme) {
                Setting(
                    primaryText = stringResource(R.string.primary_text_amoled),
                    secondaryText = stringResource(R.string.secondary_text_amoled),
                    onClick = {
                        onChangeAmoledColor()
                        localHapticFeedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    },
                    trailingContent = {
                        SettingsSwitch(
                            checked = amoledColor,
                            onCheckedChange = {
                                onChangeAmoledColor()
                                localHapticFeedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            },
                            colors = SwitchColors
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun SettingsTopAppBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    LargeTopAppBar(
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        title = { Text(text = stringResource(R.string.title_on_settings_screen)) },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = null)
            }
        },
    )
}

@Composable
private fun SettingsSwitch(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    enabled: Boolean = true,
    contentDescription: String? = null,
    colors: SwitchColors = SwitchDefaults.colors(),
    interactionSource: MutableInteractionSource? = null,
) {
    Switch(
        modifier = modifier,
        checked = checked,
        onCheckedChange = onCheckedChange,
        enabled = enabled,
        colors = colors,
        interactionSource = interactionSource,
        thumbContent = {
            Crossfade(
                targetState = checked,
                animationSpec = tween(MotionDurationTokens.MotionDurationShort3),
                label = ""
            ) { targetState ->
                Icon(
                    imageVector = if (targetState) Icons.Default.Check else Icons.Default.Close,
                    contentDescription = contentDescription,
                    modifier = Modifier.size(SwitchDefaults.IconSize)
                )
            }
        }
    )
}

@Composable
private fun ChooseThemeDialog(
    visibleDialog: Boolean,
    onCloseDialog: () -> Unit,
    onChangeTheme: (ThemeSettings) -> Unit,
    theme: ThemeSettings,
) {
    if (visibleDialog) {
        DialogSwitchTheme(
            onDismissRequest = onCloseDialog,
            selectedTheme = theme,
            onThemeSelected = {
                onChangeTheme(it)
                onCloseDialog()
            }
        )
    }
}

@Composable
private fun DialogSwitchTheme(
    onDismissRequest: () -> Unit,
    selectedTheme: ThemeSettings,
    onThemeSelected: (ThemeSettings) -> Unit,
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            color = AlertDialogDefaults.containerColor,
            shape = AlertDialogDefaults.shape,
            tonalElevation = AlertDialogDefaults.TonalElevation,
            shadowElevation = 10.dp
        ) {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Text(
                    modifier = Modifier.padding(TitlePadding),
                    text = stringResource(R.string.primary_text_theme),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                RadioGroup(
                    modifier = Modifier.padding(ContentPadding),
                    items = ThemeItem.themeItems,
                    selected = selectedTheme.ordinal,
                    onItemSelected = { onThemeSelected(ThemeSettings.fromOrdinal(it)) }
                )
                HyperlinkText2(
                    modifier = Modifier.padding(BottomPadding),
                    text = stringResource(R.string.info_bottom_content),
                    linkText = listOf(stringResource(R.string.link_text)),
                    urls = listOf(AdditionalInfoAboutTheme)
                )
            }
        }
    }
}

@Composable
private fun RadioGroup(
    modifier: Modifier = Modifier,
    items: List<ThemeItem>,
    selected: Int,
    onItemSelected: ((Int) -> Unit)? = null,
) {
    Column(
        modifier = Modifier
            .selectableGroup()
            .then(modifier)
    ) {
        items.forEach { item ->
            RadioGroupItem(
                item = item,
                selected = selected == item.id,
                onClick = { onItemSelected?.invoke(item.id) }
            )
        }
    }
}

@Composable
private fun RadioGroupItem(
    item: ThemeItem,
    selected: Boolean,
    onClick: ((Int) -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .selectable(
                selected = selected,
                onClick = { onClick?.invoke(item.id) },
                role = Role.RadioButton
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(selected, null, Modifier.padding(RadioButtonPadding))
        Spacer(modifier = Modifier.width(15.dp))
        Text(text = item.title, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

data class ThemeItem(val id: Int, val title: String) {
    companion object {
        val themeItems: List<ThemeItem>
            @Composable
            get() = listOf(
                ThemeItem(id = ThemeSettings.Light.ordinal, title = themeNames[0]),
                ThemeItem(id = ThemeSettings.Dark.ordinal, title = themeNames[1]),
                ThemeItem(id = ThemeSettings.System.ordinal, title = themeNames[2])
            )
    }
}

private val themeNames: Array<String>
    @Composable
    get() = LocalContext.current.resources.getStringArray(R.array.secondary_text_theme)

internal val SwitchColors
    @Composable
    get() = SwitchDefaults.colors(
        checkedThumbColor = MaterialTheme.colorScheme.primary,
        checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
        uncheckedThumbColor = MaterialTheme.colorScheme.secondary,
        uncheckedTrackColor = MaterialTheme.colorScheme.secondaryContainer,
        checkedIconColor = MaterialTheme.colorScheme.onPrimary
    )

private val TitlePadding = PaddingValues(24.dp, 24.dp, 24.dp, 16.dp)
private val ContentPadding = PaddingValues(0.dp, 0.dp, 0.dp, 10.dp)
private val RadioButtonPadding = PaddingValues(24.dp, 0.dp, 0.dp, 0.dp)
private val BottomPadding = PaddingValues(24.dp, 0.dp, 24.dp, 24.dp)
private const val AdditionalInfoAboutTheme =
    "https://support.google.com/android/answer/9730472?hl=en"

@Preview
@Composable
fun PreviewChooseThemeDialogLight() {
    NewsThreadsTheme(darkTheme = false, dynamicColor = false, amoledColor = false) {
        ChooseThemeDialog(
            visibleDialog = true,
            onCloseDialog = { },
            onChangeTheme = { },
            theme = ThemeSettings.System
        )
    }
}

@Preview
@Composable
fun PreviewChooseThemeDialogDark() {
    NewsThreadsTheme(darkTheme = true, dynamicColor = false, amoledColor = false) {
        ChooseThemeDialog(
            visibleDialog = true,
            onCloseDialog = { },
            onChangeTheme = { },
            theme = ThemeSettings.System
        )
    }
}

@Preview
@Composable
fun PreviewSettingsScreenDynamicLight() {
    NewsThreadsTheme(darkTheme = false, dynamicColor = true, amoledColor = false) {
        SettingsScreen(
            onBackClick = { },
            visibleDialog = false,
            onOpenDialog = { },
            onCloseDialog = { },
            theme = ThemeSettings.System,
            onChangeTheme = { },
            dynamicColor = true,
            onChangeDynamicColor = { },
            amoledColor = false,
            onChangeAmoledColor = { }
        )
    }
}

@Preview
@Composable
fun PreviewSettingsScreenDynamicDark() {
    NewsThreadsTheme(darkTheme = true, dynamicColor = true, amoledColor = false) {
        SettingsScreen(
            onBackClick = { },
            visibleDialog = true,
            onOpenDialog = { },
            onCloseDialog = { },
            theme = ThemeSettings.System,
            onChangeTheme = { },
            dynamicColor = true,
            onChangeDynamicColor = { },
            amoledColor = false,
            onChangeAmoledColor = { }
        )
    }
}

@Preview
@Composable
fun PreviewSettingsTopAppBar() {
    NewsThreadsTheme {
        SettingsTopAppBar(onBackClick = { })
    }
}