package com.example.weatherapp_sample.data.implementation.dataSourceImpl


import com.example.weatherapp_sample.data.api.WeatherApiService
import com.example.weatherapp_sample.data.datasource.WeatherRemoteDataSource
import com.example.weatherapp_sample.data.model.WeatherList
import retrofit2.Response

class WeatherRemoteDataSourceImpl(val apiService: WeatherApiService, val appId : String) :
    WeatherRemoteDataSource {

    override suspend fun getWeatherDetailsRemoteForCity(city: String): Response<WeatherList> =
        apiService.getWeatherForCity(appId, city)

}