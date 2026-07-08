package com.ayham.postask.network

sealed class NetworkException : Exception() {

    data object NoInternetFoundException : NetworkException() {
        override val message: String = "No internet connection. Please check your network."
    }

    data class ApiErrorException(
        val serverExceptionType: ServerExceptionType,
        override val message: String,
    ) : NetworkException()

    data class UnknownException(override val cause: Throwable) : NetworkException() {
        override val message: String = cause.message ?: "An unexpected error occurred."
    }
}
