package com.example.weatherapp_sample.di.core


import com.example.weatherapp_sample.domain.repository.WeatherRepository
import com.example.weatherapp_sample.domain.usecase.GetAllCityUseCase
import com.example.weatherapp_sample.domain.usecase.GetWeatherDetailsForCityUseCase
import com.example.weatherapp_sample.domain.usecase.UpdateWeatherDetailsForCityUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCaseModule {

    @Provides
    fun provideGetAllCityUseCase(weatherRepository: WeatherRepository): GetAllCityUseCase {
        return GetAllCityUseCase(weatherRepository)
    }
   
    @Provides
    fun provideGetWeatherDetailsForCityUseCase(weatherRepository: WeatherRepository): GetWeatherDetailsForCityUseCase {
        return GetWeatherDetailsForCityUseCase(weatherRepository)
    }
    @Provides
    fun provideUpdateWeatherDetailsForCityUseCase(weatherRepository: WeatherRepository): UpdateWeatherDetailsForCityUseCase {
        return UpdateWeatherDetailsForCityUseCase(weatherRepository)
    }
}