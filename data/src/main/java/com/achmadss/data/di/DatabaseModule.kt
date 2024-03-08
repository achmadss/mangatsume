package com.achmadss.data.di

import android.content.Context
import androidx.room.Room
import com.achmadss.data.common.Constants.BASE_URL
import com.achmadss.data.local.database.MangatsumeDatabase
import com.achmadss.data.remote.services.MangaService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideRemoteDatabase(): MangaService {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
            .apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MangaService::class.java)
    }

    @Singleton
    @Provides
    fun provideLocalDatabase(
        @ApplicationContext context: Context
    ): MangatsumeDatabase {
        return Room.databaseBuilder(
            context,
            MangatsumeDatabase::class.java,
            MangatsumeDatabase.name
        ).fallbackToDestructiveMigration().build()
    }

}