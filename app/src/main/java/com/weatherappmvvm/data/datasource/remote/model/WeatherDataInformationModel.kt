package com.weatherappmvvm.data.datasource.remote.model

data class WeatherDataInformationModel(
    val time: String,
    val temperature: Double,
    val weather: WeatherCondition,
    val windSpeed: Double,
)
