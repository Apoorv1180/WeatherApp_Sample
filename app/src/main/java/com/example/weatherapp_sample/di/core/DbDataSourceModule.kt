package com.example.weatherapp_sample.di.core


import com.example.weatherapp_sample.data.datasource.WeatherDbDataSource
import com.example.weatherapp_sample.data.db.WeatherDao
import com.example.weatherapp_sample.data.implementation.dataSourceImpl.WeatherDbDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbDataSourceModule {

    @Singleton
    @Provides
    fun provideWeatherDbDataSource(weatherDao: WeatherDao): WeatherDbDataSource = WeatherDbDataSourceImpl(weatherDao)

}