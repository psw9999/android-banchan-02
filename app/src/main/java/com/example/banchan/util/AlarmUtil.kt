package com.example.banchan.util

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.banchan.AlarmReceiver

object AlarmUtil {
    fun makeAlarm(context: Context, historyId: Long, triggerTime: Long) {
        context.run {
            val alarmManager = getSystemService(Application.ALARM_SERVICE) as AlarmManager
            val pendingIntent = PendingIntent.getBroadcast(
                this, historyId.toInt(), Intent(this, AlarmReceiver::class.java).apply {
                    putExtra(AlarmReceiver.ID, historyId)
                },
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                triggerTime,
                pendingIntent
            )
        }
    }
}