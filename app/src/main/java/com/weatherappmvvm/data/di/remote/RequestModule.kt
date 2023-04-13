package com.weatherappmvvm.data.di.remote

import com.weatherappmvvm.data.datasource.remote.api.WeatherCoroutinesApi
import com.weatherappmvvm.data.datasource.remote.network.ApiFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

// Here we setup our dependency injection for railroad rest api
@Module
@InstallIn(SingletonComponent::class)
object RequestModule {
    @Provides
    fun provideWeatherRestApi(): WeatherCoroutinesApi = ApiFactory().weatherRestApi
}
