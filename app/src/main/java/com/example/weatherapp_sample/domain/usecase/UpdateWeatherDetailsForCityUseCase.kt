package com.example.weatherapp_sample.domain.usecase

import com.example.weatherapp_sample.domain.repository.WeatherRepository


class UpdateWeatherDetailsForCityUseCase(private val weatherRepository : WeatherRepository) {

    suspend fun execute() = weatherRepository.updateWeatherDetailsForDbCities()
}