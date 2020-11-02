package com.globe.rolly

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import com.globe.R
import com.globe.rolly.ui.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber

class FcmMessageService : FirebaseMessagingService() {
    override fun onNewToken(p0: String?) {
        super.onNewToken(p0)
        Timber.d("tokken " + p0)
    }
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        Timber.d("kgp " + remoteMessage.toString())
        Timber.d("kgp " + remoteMessage!!.notification)
        Timber.d("kgp " + remoteMessage!!.notification!!.title)
        Timber.d("kgp " + remoteMessage!!.notification!!.body)

        Timber.d("kgp " + remoteMessage!!.data)

        if (remoteMessage!!.notification != null) {
            sendNotification(remoteMessage.notification!!.body!!)
        }
    }

    private fun sendNotification(body: String) {
        Timber.d("noti!!!")
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("Notification", body)
        }

        var pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        var notificationBuilder = NotificationCompat.Builder(this,"Notification")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Push Notification FCM")
            .setContentText(body)
            .setAutoCancel(true)
            .setSound(notificationSound)
            .setContentIntent(pendingIntent)

        var notificationManager: NotificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notificationBuilder.build())
    }
}