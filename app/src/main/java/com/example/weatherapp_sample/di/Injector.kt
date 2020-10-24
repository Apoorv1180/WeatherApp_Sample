package com.example.weatherapp_sample.di

import com.example.weatherapp_sample.di.weathermodule.WeatherSubComponent


interface Injector {

    fun createWeatherSubComponent(): WeatherSubComponent
}