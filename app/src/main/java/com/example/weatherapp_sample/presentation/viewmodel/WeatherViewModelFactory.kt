package com.example.weatherapp_sample.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp_sample.domain.usecase.GetAllCityUseCase
import com.example.weatherapp_sample.domain.usecase.GetWeatherDetailsForCityUseCase
import com.example.weatherapp_sample.domain.usecase.UpdateWeatherDetailsForCityUseCase


class WeatherViewModelFactory(
    private val getWeatherDetailsForCityUseCase: GetWeatherDetailsForCityUseCase,
    private val getAllCityUseCase: GetAllCityUseCase,
    private val updateWeatherDetailsForCityUseCase: UpdateWeatherDetailsForCityUseCase
) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WeatherViewModel(getWeatherDetailsForCityUseCase,getAllCityUseCase,updateWeatherDetailsForCityUseCase) as T
    }
}