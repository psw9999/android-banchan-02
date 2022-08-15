package com.example.banchan.domain.model

sealed class ResponseState<T>(
    val data: T? = null,
    val message: Throwable? = null
) {
    class Success<T>(data: T) : ResponseState<T>(data)
    class Error<T>(message: Throwable?) : ResponseState<T>(message = message)
    class Loading<T> : ResponseState<T>()
}
