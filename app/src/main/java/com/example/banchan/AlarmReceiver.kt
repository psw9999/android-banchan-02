package com.example.banchan

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.banchan.presentation.main.MainActivity
import com.example.banchan.util.DEFAULT_DELIVERY_TIME

class AlarmReceiver : BroadcastReceiver() {
    lateinit var notificationManager: NotificationManager

    override fun onReceive(context: Context, intent: Intent) {
        notificationManager = context.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager

        val id = intent.getLongExtra(ID, -1L)
        setUpOrderWork(context, id)
        createNotificationChannel()
        createNotification(context, id)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun createNotification(context: Context, id: Long) {
        val contentIntent = Intent(context, MainActivity::class.java).putExtra(
            OPEN_ODER_ID, id
        )

        val contentPendingIntent = PendingIntent.getActivity(
            context,
            NOTIFICATION_ID,
            contentIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_cart)
            .setContentTitle(context.getString(R.string.notification_title))
            .setContentText(context.getString(R.string.notification_content))
            .setContentIntent(contentPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)

        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    private fun setUpOrderWork(context: Context, id: Long) {
        val data = Data.Builder()
            .putLong(ID, id)
            .build()

        val orderWork = OneTimeWorkRequestBuilder<OrderWorkManager>()
            .setInputData(data)
            .build()

        WorkManager
            .getInstance(context)
            .enqueue(orderWork)
    }

    companion object {
        const val NOTIFICATION_ID = 0
        const val CHANNEL_ID = "delivery channel"
        const val CHANNEL_NAME = "delivery channel"
        const val ID = "ID"

        const val OPEN_ODER_ID = "OPEN_ODER_ID"

        const val ALARM_TIMER = DEFAULT_DELIVERY_TIME * 60 * 1000
    }
}