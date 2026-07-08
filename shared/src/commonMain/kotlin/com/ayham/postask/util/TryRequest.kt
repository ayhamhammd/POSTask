package com.ayham.postask.util

import com.ayham.postask.network.model.NetworkResult

suspend inline fun <DATA, DOMAIN> tryRequest(
    request: suspend () -> NetworkResult<DATA>,
    dataToDomain: (DATA?) -> DOMAIN,
): ResultWrapper<DOMAIN> = when (val result = request()) {
    is NetworkResult.Success -> ResultWrapper.Success(dataToDomain(result.data))
    is NetworkResult.Error -> ResultWrapper.Error(result.error)
}
