package com.example.edinburghtransportation.service

import com.example.edinburghtransportation.data.local.entity.NotificationEntity
import com.example.edinburghtransportation.data.repository.NotificationRepository
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EdinburghFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var notificationRepository: NotificationRepository

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        val title = remoteMessage.data["title"] ?: "Edinburgh Transportation"
        val message = remoteMessage.data["message"] ?: ""
        val category = remoteMessage.data["category"] ?: "Duyurular"

        val notification = NotificationEntity(
            title = title,
            message = message,
            category = category,
            isRead = false
        )

        CoroutineScope(Dispatchers.IO).launch {
            notificationRepository.insertNotification(notification)
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        println("FCM TOKEN: $token")
    }
}