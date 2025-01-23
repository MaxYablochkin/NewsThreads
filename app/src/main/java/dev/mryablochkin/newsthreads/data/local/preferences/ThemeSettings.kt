package dev.mryablochkin.newsthreads.data.local.preferences

enum class ThemeSettings {
    Light, Dark, System;

    companion object {
        fun fromOrdinal(ordinal: Int) = entries[ordinal]
    }
}