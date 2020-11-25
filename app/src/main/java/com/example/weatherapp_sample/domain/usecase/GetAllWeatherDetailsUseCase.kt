package com.example.weatherapp_sample.domain.usecase

import androidx.lifecycle.LiveData
import com.example.weatherapp_sample.data.model.WeatherList
import com.example.weatherapp_sample.domain.repository.WeatherRepository


class GetAllWeatherDetailsUseCase(private val weatherRepository : WeatherRepository) {

    fun execute(): LiveData<List<WeatherList>> = weatherRepository.getAllWeatherDetails()
}