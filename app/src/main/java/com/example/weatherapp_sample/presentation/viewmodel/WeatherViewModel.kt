package com.example.weatherapp_sample.presentation.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.weatherapp_sample.data.model.WeatherList
import com.example.weatherapp_sample.domain.usecase.GetAllCityUseCase
import com.example.weatherapp_sample.domain.usecase.GetAllWeatherDetailsUseCase
import com.example.weatherapp_sample.domain.usecase.GetWeatherDetailsForCityUseCase
import com.example.weatherapp_sample.domain.usecase.UpdateWeatherDetailsForCityUseCase


class WeatherViewModel(
    private val getWeatherDetailsForCityUseCase: GetWeatherDetailsForCityUseCase,
    private val getAllCityUseCase: GetAllCityUseCase,
    private val updateWeatherDetailsForCityUseCase: UpdateWeatherDetailsForCityUseCase,
    private val getAllWeatherDetailsUseCase: GetAllWeatherDetailsUseCase
) : ViewModel() {

    fun getAllCities() = liveData {
        val cityList : List<String>? = getAllCityUseCase.execute()
        emit(cityList)
    }

    fun getWeatherForCity(city: String):LiveData<WeatherList>? = liveData {
        val weatherList: WeatherList? = getWeatherDetailsForCityUseCase.execute(city)
        weatherList?.let { emit(it) }
    }

    suspend fun updateWeatherForDbCities() {
        updateWeatherDetailsForCityUseCase.execute()
    }

    fun getAllWeatherDetails():LiveData<List<WeatherList>?> = liveData {
        val list :List<WeatherList>?=getAllWeatherDetailsUseCase.execute()
        emit(list)
    }


}