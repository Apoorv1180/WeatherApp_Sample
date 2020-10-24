package com.example.weatherapp_sample.di.core


import com.example.weatherapp_sample.data.api.WeatherApiService
import com.example.weatherapp_sample.data.datasource.WeatherRemoteDataSource
import com.example.weatherapp_sample.data.implementation.dataSourceImpl.WeatherRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RemoteDataSourceModule(
    private val apikey: String
) {
    @Singleton
    @Provides
    fun provideWeatherRemoteDataSource(weatherApiService: WeatherApiService): WeatherRemoteDataSource =
        WeatherRemoteDataSourceImpl(weatherApiService, apikey)
}