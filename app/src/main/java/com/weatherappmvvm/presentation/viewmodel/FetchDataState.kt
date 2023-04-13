package com.weatherappmvvm.presentation.viewmodel

// Handle ViewModel response data
sealed class FetchDataState<out T> {
    data class OnSuccessResponse<out T>(val data: T) : FetchDataState<T>()

    data class OnFailedResponse(val message: String?) : FetchDataState<Nothing>()
}
