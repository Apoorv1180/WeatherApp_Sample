package com.example.weatherapp_sample.domain.usecase

import com.example.weatherapp_sample.data.model.WeatherList
import com.example.weatherapp_sample.domain.repository.WeatherRepository


class GetAllWeatherDetailsUseCase(private val weatherRepository : WeatherRepository) {

    suspend fun execute():List<WeatherList>? = weatherRepository.getAllWeatherDetails()
}