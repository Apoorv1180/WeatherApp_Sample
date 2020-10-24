package com.example.weatherapp_sample.data.implementation.dataSourceImpl

import com.example.weatherapp_sample.data.datasource.WeatherCacheDataSource
import com.example.weatherapp_sample.data.model.WeatherList


class WeatherCacheDataSourceImpl : WeatherCacheDataSource {
    private var cityList = ArrayList<String>()
    private var weatherList = ArrayList<WeatherList>()

    override suspend fun getCityListFromCache(): ArrayList<String> {
        return cityList
    }

    override suspend fun getWeatherDetailsFromCityFromCache(city: String): WeatherList? {
        var weatherListItem : WeatherList? = null
        if (weatherList.size > 0) {
            for (weather in weatherList) {
                if (city.toUpperCase().equals((weather.name).toUpperCase())) {
                    weatherListItem = weather
                }
            }
        }
        return weatherListItem
    }

    override suspend fun saveWeatherDetailsCacheForCity(weatherList: WeatherList) {
        this.weatherList.clear()
        this.weatherList.add(weatherList)
    }

    override suspend fun saveCityList(cityList: ArrayList<String>) {
        this.cityList.clear()
        this.cityList.addAll(cityList)
    }
}