package com.example.weatherapp_sample.data.datasource

import androidx.lifecycle.LiveData
import com.example.weatherapp_sample.data.model.WeatherList


interface WeatherDbDataSource {
    suspend fun getWeatherDetailsDbForCity(city :String) : WeatherList
    suspend fun saveWeatherDetailsDbForCity(weatherList : WeatherList)
    suspend fun clearAllWeatherList()
    fun getAllWeatherList(): LiveData<List<WeatherList>>
}