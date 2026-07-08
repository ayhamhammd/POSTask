package com.ayham.postask.network.model

import com.ayham.postask.network.NetworkException

sealed class NetworkResult<out T> {
    data class Success<T>(val data: T?) : NetworkResult<T>()
    data class Error(val error: NetworkException) : NetworkResult<Nothing>()
}
