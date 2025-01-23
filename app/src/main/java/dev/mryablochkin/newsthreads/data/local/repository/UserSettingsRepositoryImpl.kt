package dev.mryablochkin.newsthreads.data.local.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.mryablochkin.newsthreads.data.local.preferences.ThemeSettings
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class UserSettingsRepositoryImpl @Inject constructor(
    @ApplicationContext context: Context,
) : UserSettingsRepository {

    private val preferences: SharedPreferences = context.getSharedPreferences(NAME_PREFERENCES, MODE)

    override var theme: ThemeSettings by ThemePreferenceDelegate(THEME_NAME, THEME_DEFAULT_VALUE)
    override val themeStateFlow = MutableStateFlow(theme)

    override var dynamicColor: Boolean by DynamicColorPreferenceDelegate(DYNAMIC_COLOR_NAME, DYNAMIC_COLOR_VALUE)
    override val dynamicColorStateFlow = MutableStateFlow(dynamicColor)

    override var amoledColor: Boolean by AmoledColorPreferenceDelegate(AMOLED_COLOR_NAME, AMOLED_COLOR_VALUE)
    override val amoledColorStateFlow = MutableStateFlow(amoledColor)

    override suspend fun updateTheme(themeSettings: ThemeSettings) {
        theme = themeSettings
    }

    override suspend fun updateDynamicColor() {
        dynamicColor = !dynamicColor
    }

    override suspend fun updateAmoledColor() {
        amoledColor = !amoledColor
    }

    inner class ThemePreferenceDelegate(
        private val name: String,
        private val defaultValue: ThemeSettings
    ) : ReadWriteProperty<Any?, ThemeSettings> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): ThemeSettings {
            return ThemeSettings.fromOrdinal(preferences.getInt(name, defaultValue.ordinal))
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: ThemeSettings) {
            themeStateFlow.value = value
            preferences.edit {
                putInt(name, value.ordinal)
            }
        }
    }

    inner class DynamicColorPreferenceDelegate(
        private val name: String,
        private val defaultValue: Boolean
    ) : ReadWriteProperty<Any?, Boolean> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): Boolean {
            return preferences.getBoolean(name, defaultValue)
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) {
            dynamicColorStateFlow.value = value
            preferences.edit {
                putBoolean(name, value)
            }
        }
    }

    inner class AmoledColorPreferenceDelegate(
        private val name: String,
        private val defaultValue: Boolean
    ) : ReadWriteProperty<Any?, Boolean> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): Boolean {
            return preferences.getBoolean(name, defaultValue)
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) {
            amoledColorStateFlow.value = value
            preferences.edit {
                putBoolean(name, value)
            }
        }
    }

    companion object {
        const val NAME_PREFERENCES = "NewsThreadsPreferences"
        const val MODE = Context.MODE_PRIVATE
        const val THEME_NAME = "current_theme"
        val THEME_DEFAULT_VALUE = ThemeSettings.System
        const val DYNAMIC_COLOR_NAME = "dynamic_color"
        const val DYNAMIC_COLOR_VALUE = true
        const val AMOLED_COLOR_NAME = "amoled_color"
        const val AMOLED_COLOR_VALUE = false
    }
}