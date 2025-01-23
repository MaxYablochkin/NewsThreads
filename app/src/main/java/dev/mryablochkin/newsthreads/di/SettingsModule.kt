package dev.mryablochkin.newsthreads.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.mryablochkin.newsthreads.data.local.repository.UserSettingsRepository
import dev.mryablochkin.newsthreads.data.local.repository.UserSettingsRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingsModule {
    @Binds
    @Singleton
    abstract fun bindUserSettings(
        userSettingsRepositoryImpl: UserSettingsRepositoryImpl,
    ): UserSettingsRepository
}