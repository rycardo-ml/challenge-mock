package com.example.challengemock.data.database.di

import android.content.Context
import androidx.room.Room
import com.example.challengemock.data.ChallengeMockDatabase
import com.example.challengemock.data.database.dao.ProductDao
import com.example.challengemock.data.database.dao.ProductRemoteKeyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): ChallengeMockDatabase =
        Room
            .databaseBuilder(context, ChallengeMockDatabase::class.java, "challenge_database")
            .build()

    @Singleton
    @Provides
    fun provideProductDao(database: ChallengeMockDatabase): ProductDao = database.getProductDao()

    @Singleton
    @Provides
    fun provideProductRemoteKeyDao(database: ChallengeMockDatabase): ProductRemoteKeyDao = database.getProductRemoteKeyDao()
}