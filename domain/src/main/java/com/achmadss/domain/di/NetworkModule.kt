package com.achmadss.domain.di

import android.content.Context
import com.achmadss.domain.common.NetworkUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideNetworkUtils(
        @ApplicationContext context: Context
    ): NetworkUtils = NetworkUtils(context)

}