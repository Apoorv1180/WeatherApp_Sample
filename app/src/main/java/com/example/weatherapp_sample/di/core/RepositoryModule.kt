package com.example.weatherapp_sample.di.core


import com.example.weatherapp_sample.data.datasource.WeatherDbDataSource
import com.example.weatherapp_sample.data.datasource.WeatherRemoteDataSource
import com.example.weatherapp_sample.data.implementation.repositoryImpl.WeatherRepositoryImpl
import com.example.weatherapp_sample.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideWeatherRepository(
        weatherRemoteDataSource: WeatherRemoteDataSource,
        weatherDbDataSource: WeatherDbDataSource,
    ): WeatherRepository {
        return WeatherRepositoryImpl(weatherRemoteDataSource, weatherDbDataSource)
    }
}