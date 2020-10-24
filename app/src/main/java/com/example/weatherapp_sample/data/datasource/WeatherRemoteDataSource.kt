package com.example.weatherapp_sample.data.datasource

import com.example.weatherapp_sample.data.model.WeatherList
import retrofit2.Response

interface WeatherRemoteDataSource {

    suspend fun getWeatherDetailsRemoteForCity(city: String) :Response<WeatherList>?
}