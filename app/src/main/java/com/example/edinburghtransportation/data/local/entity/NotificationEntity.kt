package com.example.edinburghtransportation.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,
    val message: String,
    val category: String,
    val isRead: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)