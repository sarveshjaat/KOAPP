package com.nictko.services.notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.nictko.services.R

class FirebaseMessageReceiver : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    // Override onMessageReceived() method to extract the
    // title and
    // body from the message passed in FCM
    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        Log.e("sam","sam"+remoteMessage)
        remoteMessage.notification?.let {
            showNotification(it.title ?: "New Notification", it.body ?: "No message body")
        }

    }

//    private fun showNotification(title: String, message: String) {
//        val channelId = "NoticeChannel"
//        val notificationId = System.currentTimeMillis().toInt()
//        val soundUri = Uri.parse("android.resource://" + packageName + "/" + R.raw.notification)
//
//        val powerManager = getSystemService(POWER_SERVICE) as PowerManager
//        val wakeLock = powerManager.newWakeLock(
//            PowerManager.FULL_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP,
//            "MyApp::MyWakelockTag"
//        )
//        wakeLock.acquire(3000) //
//
//        // Create Notification Channel for Android 8.0+
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel = NotificationChannel(
//                channelId,
//                "NoticeChannel",
//                NotificationManager.IMPORTANCE_HIGH
//            ).apply {
//                description = "Channel for Firebase Cloud Messaging notifications"
//                setSound(soundUri, null)
//                lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
//            }
//
//            val manager = getSystemService(NotificationManager::class.java)
//            manager?.createNotificationChannel(channel)
//        }
//
//        // Build the notification
//        val notificationBuilder = NotificationCompat.Builder(this, channelId)
//            .setSmallIcon(R.drawable.logo) // Replace with your app's notification icon
//            .setContentTitle(title)
//            .setContentText(message)
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC) // Show on lock screen
//            .setAutoCancel(true)
//            .setSound(soundUri)
//        // Show the notification
//        with(NotificationManagerCompat.from(this)) {
//            if (ActivityCompat.checkSelfPermission(
//                    applicationContext,
//                    Manifest.permission.POST_NOTIFICATIONS
//                ) != PackageManager.PERMISSION_GRANTED
//            ) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return
//            }
//            notify(notificationId, notificationBuilder.build())
//        }
//    }

    private fun showNotification(title: String, message: String) {
        val channelId = "NoticeChannel"
        val notificationId = System.currentTimeMillis().toInt()
        val soundUri = Uri.parse("android.resource://${packageName}/" + R.raw.notification)

        // Wake up the screen if it's off
        val powerManager = getSystemService(POWER_SERVICE) as PowerManager
        val wakeLock = powerManager.newWakeLock(
            PowerManager.PARTIAL_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP,
            "MyApp::MyWakelockTag"
        )
        wakeLock.acquire(3000) // Wake the device for 3 seconds

        // Create Notification Channel (Android 8+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val attributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()

            val channel = NotificationChannel(
                channelId,
                "NoticeChannel",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Channel for Firebase Cloud Messaging notifications"
                setSound(soundUri, attributes) // ✅ Set sound properly
                lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
            }

            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }

        // Build the notification
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.logo) // Use your app's icon
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC) // Show on lock screen
            .setAutoCancel(true)
            .setSound(soundUri) // ✅ Ensure sound is set

        // Show the notification
        with(NotificationManagerCompat.from(this)) {
            if (ActivityCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(notificationId, notificationBuilder.build())
        }
    }

}