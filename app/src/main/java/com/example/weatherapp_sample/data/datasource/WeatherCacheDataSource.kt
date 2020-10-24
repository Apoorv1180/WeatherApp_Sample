package com.example.weatherapp_sample.data.datasource

import com.example.weatherapp_sample.data.model.WeatherList


interface WeatherCacheDataSource {

    suspend fun getCityListFromCache():ArrayList<String>
    suspend fun getWeatherDetailsFromCityFromCache(city : String) : WeatherList?
    suspend fun saveWeatherDetailsCacheForCity(weatherList: WeatherList)
    suspend fun saveCityList(cityList: ArrayList<String>)
}