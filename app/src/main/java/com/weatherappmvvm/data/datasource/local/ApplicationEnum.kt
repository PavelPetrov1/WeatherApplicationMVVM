package com.weatherappmvvm.data.datasource.local

object ApplicationEnum {
    enum class RequestQueryParam(val value: String) {
        HOURLY("temperature_2m,weathercode,windspeed_10m"),
    }

    enum class CityLatLong(val latitude: Float, val longitude: Float) {
        SOFIA(42.70f, 23.32f),
        BURGAS(42.15f, 24.75f),
        PLOVDIV(42.51f, 27.47f),
    }

    enum class CityName(val cityName: String) {
        SOFIA("Sofia"),
        BURGAS("Burgas"),
        PLOVDIV("Plovdiv"),
    }
}
