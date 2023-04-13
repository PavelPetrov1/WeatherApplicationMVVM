package com.weatherappmvvm.data.datasource.remote.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Hourly(
    @SerializedName("time") val time: List<String>,
    @SerializedName("temperature_2m") val temperature2m: List<Double>,
    @SerializedName("weathercode") val weathercode: List<Int>,
    @SerializedName("windspeed_10m") val windspeed10m: List<Double>,
) : Serializable
