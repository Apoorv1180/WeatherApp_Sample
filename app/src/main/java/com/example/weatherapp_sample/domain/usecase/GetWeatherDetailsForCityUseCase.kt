package com.example.weatherapp_sample.domain.usecase

import com.example.weatherapp_sample.data.model.WeatherList
import com.example.weatherapp_sample.domain.repository.WeatherRepository


class GetWeatherDetailsForCityUseCase(private val weatherRepository : WeatherRepository) {

    suspend fun execute(city : String): WeatherList? = weatherRepository.getWeatherDetailsForCity(city)
}