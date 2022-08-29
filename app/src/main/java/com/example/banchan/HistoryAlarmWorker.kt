package com.example.banchan

import android.content.Context
import androidx.annotation.NonNull
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.banchan.domain.usecase.history.GetHistoryListUseCase
import com.example.banchan.util.AlarmUtil
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class HistoryAlarmWorker @AssistedInject constructor(
    private val getHistoryListUseCase: GetHistoryListUseCase,
    @Assisted @NonNull appContext: Context,
    @Assisted @NonNull params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        return try {
            getHistoryListUseCase().onSuccess {
                it.forEach { history ->
                    if (!history.isSuccess) {
                        AlarmUtil.makeAlarm(
                            context = applicationContext,
                            historyId = history.id,
                            triggerTime = history.date.time + AlarmReceiver.ALARM_TIMER
                        )
                    }
                }
            }
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}