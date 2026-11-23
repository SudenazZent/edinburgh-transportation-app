package com.example.edinburghtransportation.data.repository

import com.example.edinburghtransportation.data.local.dao.NotificationDao
import com.example.edinburghtransportation.data.local.entity.NotificationEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotificationRepository @Inject constructor(
    private val notificationDao: NotificationDao
) {

    fun getAllNotifications(): Flow<List<NotificationEntity>> {
        return notificationDao.getAllNotifications()
    }

    fun getUnreadCount(): Flow<Int> {
        return notificationDao.getUnreadCount()
    }

    suspend fun insertNotification(
        notification: NotificationEntity
    ) {
        notificationDao.insertNotification(notification)
    }

    suspend fun markAllAsRead() {
        notificationDao.markAllAsRead()
    }
}