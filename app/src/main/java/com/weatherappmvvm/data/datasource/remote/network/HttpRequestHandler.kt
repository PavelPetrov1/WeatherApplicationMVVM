package com.weatherappmvvm.data.datasource.remote.network

import retrofit2.Response

// Here we create an extension for Response with a generic class type
inline fun <reified T> Response<T>.response(
    onSuccess: (T) -> Unit,
    onError: (APIError) -> Unit,
) {
    // We encapsulate the request in a try-catch block in case something goes wrong
    try {
        if (this.isSuccessful) {
            // If body is empty but request is successful, continue with flow
            val responseBody = this.body() ?: Any()

            // We check if the response body is of the type we expect
            if (responseBody is T) {
                onSuccess(responseBody)
            } else {
                onError(APIError.getDefaultErrorResponse())
            }
        } else {
            val apiError = APIError.getDefaultErrorResponse()

            onError(apiError)
        }
    } catch (e: Exception) {
        onError(APIError.getDefaultErrorResponse())
    }
}
