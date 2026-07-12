package com.ayham.postask.network.api

import com.ayham.postask.network.model.NetworkResult
import com.ayham.postask.util.ResultWrapper

suspend inline fun <DATA, DOMAIN> tryRequest(
    request: suspend () -> NetworkResult<DATA>,
    dataToDomain: (DATA?) -> DOMAIN,
): ResultWrapper<DOMAIN> = when (val result = request()) {
    is NetworkResult.Success -> ResultWrapper.Success(dataToDomain(result.data))
    is NetworkResult.Error -> ResultWrapper.Error(result.error)
}
