package com.example.weatherapp_sample.data.datasource

import com.example.weatherapp_sample.data.model.WeatherList


interface WeatherDbDataSource {

    suspend fun getCityListFromDb() : List<String>
    suspend fun getWeatherDetailsDbForCity(city :String) : WeatherList
    suspend fun saveWeatherDetailsDbForCity(weatherList : WeatherList)
    suspend fun clearAllWeatherList()

}