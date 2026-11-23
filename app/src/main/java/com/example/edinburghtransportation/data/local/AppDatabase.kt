package com.example.edinburghtransportation.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.edinburghtransportation.data.local.dao.CardDao
import com.example.edinburghtransportation.data.local.entity.CardEntity
import com.example.edinburghtransportation.data.local.entity.NotificationEntity
import com.example.edinburghtransportation.data.local.dao.NotificationDao

@Database(
    entities = [CardEntity::class,
        NotificationEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cardDao(): CardDao
    abstract fun notificationDao(): NotificationDao
}