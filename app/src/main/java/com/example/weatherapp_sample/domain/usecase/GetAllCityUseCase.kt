package com.example.weatherapp_sample.domain.usecase

import com.example.weatherapp_sample.domain.repository.WeatherRepository


class GetAllCityUseCase(private val weatherRepository : WeatherRepository) {

    suspend fun execute():List<String>? = weatherRepository.getAllCities()
}