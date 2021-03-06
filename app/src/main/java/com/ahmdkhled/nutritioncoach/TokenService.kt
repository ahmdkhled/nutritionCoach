package com.ahmdkhled.nutritioncoach

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.ahmdkhled.nutritioncoach.repo.UserRepo
import com.ahmdkhled.nutritioncoach.utils.SharedHelper
import com.ahmdkhled.nutritioncoach.view.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class TokenService :FirebaseMessagingService() {

    private val CHANNEL_ID="65"
    private  val TAG = "TokenService"
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "onNewToken: "+token)
        SharedHelper.saveToken(applicationContext,token)
        UserRepo().updateToken(token)

    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d(TAG, "onMessageReceived: "+message.data)

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel =NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            notificationManager.createNotificationChannel(channel)

        }

        val action1Intent: Intent = Intent(applicationContext, MainActivity::class.java)


        val action1PendingIntent = PendingIntent.getService(applicationContext, 0,
                action1Intent, PendingIntent.FLAG_ONE_SHOT)




        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_message_24)
            .setContentTitle(message.notification?.title)
            .setContentText(message.notification?.body)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        builder.addAction(R.drawable.ic_baseline_message_24,"message",action1PendingIntent)

        notificationManager.notify(2,builder.build())

    }
}