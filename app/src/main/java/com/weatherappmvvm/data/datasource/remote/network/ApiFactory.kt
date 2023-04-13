package com.weatherappmvvm.data.datasource.remote.network

import com.weatherappmvvm.BuildConfig
import com.weatherappmvvm.data.datasource.remote.api.WeatherCoroutinesApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiFactory {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private fun retrofit(okHttpClient: OkHttpClient) = Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    // We create a new OkHttpClient and add a logging interceptor
    private val basicOkHttpClient = OkHttpClient().newBuilder()
        .addInterceptor(loggingInterceptor)
        .build()

    val weatherRestApi: WeatherCoroutinesApi =
        retrofit(basicOkHttpClient).create(WeatherCoroutinesApi::class.java)
}
