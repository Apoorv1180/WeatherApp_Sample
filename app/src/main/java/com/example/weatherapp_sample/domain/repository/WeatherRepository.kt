package com.example.weatherapp_sample.domain.repository

import androidx.lifecycle.LiveData
import com.example.weatherapp_sample.data.model.WeatherList

interface WeatherRepository {

    suspend fun getWeatherDetailsForCity(city: String): WeatherList?
    suspend fun updateWeatherDetailsForDbCities()
    fun getAllWeatherDetails(): LiveData<List<WeatherList>>
}