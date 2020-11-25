package com.example.weatherapp_sample.di.core


import com.example.weatherapp_sample.domain.repository.WeatherRepository
import com.example.weatherapp_sample.domain.usecase.GetAllWeatherDetailsUseCase
import com.example.weatherapp_sample.domain.usecase.GetWeatherDetailsForCityUseCase
import com.example.weatherapp_sample.domain.usecase.UpdateWeatherDetailsForCityUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCaseModule {

    @Provides
    fun provideGetWeatherDetailsForCityUseCase(weatherRepository: WeatherRepository): GetWeatherDetailsForCityUseCase {
        return GetWeatherDetailsForCityUseCase(weatherRepository)
    }
    @Provides
    fun provideUpdateWeatherDetailsForCityUseCase(weatherRepository: WeatherRepository): UpdateWeatherDetailsForCityUseCase {
        return UpdateWeatherDetailsForCityUseCase(weatherRepository)
    }

    @Provides
    fun provideGetAllWeatherDetailsUseCase(weatherRepository: WeatherRepository): GetAllWeatherDetailsUseCase {
        return GetAllWeatherDetailsUseCase(weatherRepository)
    }
}