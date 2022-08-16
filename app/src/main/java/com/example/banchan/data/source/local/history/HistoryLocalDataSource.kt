package com.example.banchan.data.source.local.history

import com.example.banchan.data.source.HistoryDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HistoryLocalDataSource @Inject constructor(
    private val historyDao: HistoryDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : HistoryDataSource {
    override fun getHistoryWithItems() = historyDao.getHistoryWithItems().map {
        Result.success(it)
    }.catch { exception ->
        Result.failure<Exception>(exception)
    }

    override suspend fun insertHistoryWithItems(items: List<HistoryItem>) = runCatching {
        withContext(ioDispatcher) {
            historyDao.insertHistoryWithItems(items)
        }
    }
}