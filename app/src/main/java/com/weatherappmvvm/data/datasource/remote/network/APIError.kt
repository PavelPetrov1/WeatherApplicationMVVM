package com.weatherappmvvm.data.datasource.remote.network

// This is our class for handling error responses
class APIError(
    private val message: String,
    private val statusCode: Int,
) {

    fun getErrorMessage(): String {
        return message
    }

    fun getErrorStatusCode(): Int {
        return statusCode
    }

    companion object {

        private const val RETROFIT_REQUEST_DEFAULT_ERROR_MESSAGE = "Something went wrong"
        private const val BAD_REQUEST = 400

        // This is the default error response that will be returned in case of an unexpected error
        fun getDefaultErrorResponse() = APIError(
            message = RETROFIT_REQUEST_DEFAULT_ERROR_MESSAGE,
            statusCode = BAD_REQUEST,
        )
    }
}
