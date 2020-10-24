package com.example.weatherapp_sample.di.core


import com.example.weatherapp_sample.data.datasource.WeatherCacheDataSource
import com.example.weatherapp_sample.data.implementation.dataSourceImpl.WeatherCacheDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheDataSourceModule {

    @Singleton
    @Provides
    fun provideCacheDataSource(): WeatherCacheDataSource = WeatherCacheDataSourceImpl()
}