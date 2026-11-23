package com.example.edinburghtransportation.di

import android.content.Context
import androidx.room.Room
import com.example.edinburghtransportation.data.local.AppDatabase
import com.example.edinburghtransportation.data.local.dao.CardDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.example.edinburghtransportation.data.local.dao.NotificationDao

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "edinburgh_transportation_database"
        ).build()
    }

    @Provides
    fun provideCardDao(
        database: AppDatabase
    ): CardDao {
        return database.cardDao()
    }

    @Provides
    fun provideNotificationDao(
        database: AppDatabase
    ): NotificationDao {
        return database.notificationDao()
    }
}