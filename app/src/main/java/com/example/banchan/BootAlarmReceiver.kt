package com.example.banchan

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

class BootAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            setUpAlarmWork(context)
        }
    }

    private fun setUpAlarmWork(context: Context) {
        val orderWork = OneTimeWorkRequestBuilder<HistoryAlarmWorker>()
            .build()

        WorkManager
            .getInstance(context)
            .enqueue(orderWork)
    }
}