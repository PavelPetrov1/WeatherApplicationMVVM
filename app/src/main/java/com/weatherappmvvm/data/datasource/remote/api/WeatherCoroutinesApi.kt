package com.weatherappmvvm.data.datasource.remote.api

import com.weatherappmvvm.data.datasource.local.ApplicationEnum
import com.weatherappmvvm.data.datasource.remote.model.WeatherResponseData
import com.weatherappmvvm.data.datasource.remote.network.Router
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherCoroutinesApi {
    @GET(Router.GET_RAIL_ROAD_DATA)
    suspend fun getWeatherData(
        @Query("latitude") latitude: Float,
        @Query("longitude") longitude: Float,
        @Query("hourly") hourly: String = ApplicationEnum.RequestQueryParam.HOURLY.value,
    ): Response<WeatherResponseData>
}
