package com.example.banchan

import android.content.Context
import androidx.annotation.NonNull
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.banchan.AlarmReceiver.Companion.ID
import com.example.banchan.domain.usecase.history.UpdateHistoryUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class OrderSuccessWorkManager @AssistedInject constructor(
    private val updateHistoryUseCase: UpdateHistoryUseCase,
    @Assisted @NonNull appContext: Context,
    @Assisted @NonNull params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        return try {
            updateHistoryUseCase(inputData.getLong(ID, 0))
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}