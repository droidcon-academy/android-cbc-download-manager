package com.droidcon.downloadmanager.di

import android.app.DownloadManager
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DownloadManagerModule {
    @Provides
    fun provideDownloadManager(@ApplicationContext context: Context): DownloadManager {
        return context.getSystemService(DownloadManager::class.java)
    }
}