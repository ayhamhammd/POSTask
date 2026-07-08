package com.ayham.postask.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayham.postask.util.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class BaseViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<Throwable?>(null)
    val error: StateFlow<Throwable?> = _error.asStateFlow()

    fun clearError() {
        _error.value = null
    }

    protected fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }

    protected fun emitError(throwable: Throwable) {
        _error.value = throwable
    }

    protected fun <T> Flow<ResultWrapper<T>>.collectResult(onSuccess: (T) -> Unit) {
        onEach { result ->
            when (result) {
                ResultWrapper.Loading -> setLoading(true)
                is ResultWrapper.Success -> {
                    setLoading(false)
                    onSuccess(result.data)
                }
                is ResultWrapper.Error -> {
                    setLoading(false)
                    emitError(result.error)
                }
            }
        }.launchIn(viewModelScope)
    }
}
