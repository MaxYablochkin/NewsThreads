package dev.mryablochkin.newsthreads.utils

sealed class RequestResult<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T?) : RequestResult<T>(data = data)
    class Loading<T>(message: String?) : RequestResult<T>()
    class Error<T>(message: String?) : RequestResult<T>(message = message)
}