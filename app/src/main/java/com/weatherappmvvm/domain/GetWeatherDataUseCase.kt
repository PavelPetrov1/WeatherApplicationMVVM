package com.weatherappmvvm.domain

import com.weatherappmvvm.data.datasource.remote.model.Hourly
import com.weatherappmvvm.data.datasource.remote.model.WeatherCondition
import com.weatherappmvvm.data.datasource.remote.model.WeatherDataInformationModel
import com.weatherappmvvm.data.datasource.remote.model.WeatherResponseData
import com.weatherappmvvm.data.datasource.remote.network.APIError
import javax.inject.Inject

// Here should be our business logic regarding the received data
class GetWeatherDataUseCase @Inject constructor() {

    fun handleSuccessResponse(weatherData: WeatherResponseData): List<WeatherDataInformationModel> {
        return mapHourlyDataToWeatherModel(weatherData.hourly)
    }

    fun handleErrorResponse(error: APIError): String {
        return error.getErrorMessage()
    }

    private fun mapHourlyDataToWeatherModel(hourly: Hourly): List<WeatherDataInformationModel> {
        return hourly.time.mapIndexed { index, time ->
            val weatherCondition = hourly.weathercode[index]

            WeatherDataInformationModel(
                time = time,
                temperature = hourly.temperature2m[index],
                weather = weatherCondition.getWeatherConditionBasedOnCode(),
                windSpeed = hourly.windspeed10m[index],
            )
        }
    }

    private fun Int.getWeatherConditionBasedOnCode(): WeatherCondition {
        return when (this) {
            0 -> WeatherCondition.ClearSky
            in 1..3 -> WeatherCondition.MainlyClear
            45, 48 -> WeatherCondition.FogAndRime
            in 51..53 -> WeatherCondition.LightDrizzle
            55 -> WeatherCondition.DenseDrizzle
            56 -> WeatherCondition.LightFreezingDrizzle
            57 -> WeatherCondition.DenseFreezingDrizzle
            in 61..63 -> WeatherCondition.SlightRain
            65 -> WeatherCondition.ModerateRain
            66 -> WeatherCondition.LightFreezingRain
            67 -> WeatherCondition.HeavyFreezingRain
            in 71..73 -> WeatherCondition.SlightSnow
            75 -> WeatherCondition.ModerateSnow
            77 -> WeatherCondition.SnowGrains
            in 80..81 -> WeatherCondition.SlightRainShowers
            82 -> WeatherCondition.ViolentRainShowers
            85 -> WeatherCondition.SlightSnowShowers
            86 -> WeatherCondition.HeavySnowShowers
            in 95..96 -> WeatherCondition.SlightThunderstorm
            99 -> WeatherCondition.HeavyHailThunderstorm
            else -> WeatherCondition.Unknown
        }
    }
}
