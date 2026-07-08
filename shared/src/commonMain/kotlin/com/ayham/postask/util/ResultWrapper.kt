package com.ayham.postask.util

sealed class ResultWrapper<out T> {
    data object Loading : ResultWrapper<Nothing>()
    data class Success<T>(val data: T) : ResultWrapper<T>()
    data class Error(val error: Throwable) : ResultWrapper<Nothing>()
}
