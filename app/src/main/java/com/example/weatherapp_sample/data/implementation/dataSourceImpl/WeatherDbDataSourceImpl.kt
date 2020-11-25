package com.example.weatherapp_sample.data.implementation.dataSourceImpl

import androidx.lifecycle.LiveData
import com.example.weatherapp_sample.data.datasource.WeatherDbDataSource
import com.example.weatherapp_sample.data.db.WeatherDao
import com.example.weatherapp_sample.data.model.WeatherList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherDbDataSourceImpl(val weatherDao: WeatherDao) : WeatherDbDataSource {

    override suspend fun getWeatherDetailsDbForCity(city: String): WeatherList = weatherDao.getWeatherDetailsForCity(city)

    override suspend fun saveWeatherDetailsDbForCity(weatherList: WeatherList) {
        CoroutineScope(Dispatchers.IO).launch { weatherDao.saveWeatherDetails(weatherList) }
    }

    override suspend fun clearAllWeatherList() {
        CoroutineScope(Dispatchers.IO).launch {
            weatherDao.deleteAllWeatherDetails()
        }
    }

    override fun getAllWeatherList(): LiveData<List<WeatherList>> =
        weatherDao.getAllWeatherDetails()
}