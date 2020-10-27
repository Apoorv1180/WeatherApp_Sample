package com.example.weatherapp_sample.di.weathermodule


import com.example.weatherapp_sample.domain.usecase.GetAllCityUseCase
import com.example.weatherapp_sample.domain.usecase.GetAllWeatherDetailsUseCase
import com.example.weatherapp_sample.domain.usecase.GetWeatherDetailsForCityUseCase
import com.example.weatherapp_sample.domain.usecase.UpdateWeatherDetailsForCityUseCase
import com.example.weatherapp_sample.presentation.viewmodel.WeatherViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class WeatherModule {

    @WeatherScope
    @Provides
    fun provideWeatherViewModelFactory(
        getWeatherDetailsForCityUseCase: GetWeatherDetailsForCityUseCase,
        getAllCityUseCase: GetAllCityUseCase,
        updateWeatherDetailsForCityUseCase: UpdateWeatherDetailsForCityUseCase,
        getAllWeatherDetailsUseCase: GetAllWeatherDetailsUseCase
    ):WeatherViewModelFactory{
        return WeatherViewModelFactory(getWeatherDetailsForCityUseCase,getAllCityUseCase,updateWeatherDetailsForCityUseCase,getAllWeatherDetailsUseCase)
    }
}