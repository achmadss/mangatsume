package com.achmadss.data.di

import com.achmadss.data.local.database.MangatsumeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Singleton
    @Provides
    fun provideMangaDao(db: MangatsumeDatabase) = db.mangaDao()

}