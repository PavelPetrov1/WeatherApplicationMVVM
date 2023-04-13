package com.weatherappmvvm.data.datasource.remote.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class HourlyUnits(
    @SerializedName("time") val time: String,
    @SerializedName("temperature_2m") val temperature2m: String,
    @SerializedName("weathercode") val weathercode: String,
    @SerializedName("windspeed_10m") val windspeed10m: String,
) : Serializable
