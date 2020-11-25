package com.example.weatherapp_sample.presentation.viewmodel

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.example.weatherapp_sample.data.model.WeatherList
import com.example.weatherapp_sample.domain.usecase.GetAllWeatherDetailsUseCase
import com.example.weatherapp_sample.domain.usecase.GetWeatherDetailsForCityUseCase
import com.example.weatherapp_sample.domain.usecase.UpdateWeatherDetailsForCityUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class WeatherViewModel(
    private val getWeatherDetailsForCityUseCase: GetWeatherDetailsForCityUseCase,
    private val updateWeatherDetailsForCityUseCase: UpdateWeatherDetailsForCityUseCase,
    private val getAllWeatherDetailsUseCase: GetAllWeatherDetailsUseCase
) : ViewModel() {

    private val _city = MutableLiveData<WeatherList>()
    val city: LiveData<WeatherList>
        get() = _city

    fun fetchCity(cityStr: String): LiveData<WeatherList> {
        viewModelScope.launch {
            _city.value = getWeatherDetailsForCityUseCase.execute(cityStr)
        }
        return city
    }

    fun getAllWeatherDetails(): LiveData<List<WeatherList>> = getAllWeatherDetailsUseCase.execute()
    fun setSuggestion(list: List<WeatherList>): List<String> {
        return list.distinct().map { it.name }
    }

}


//    fun getAllCities(): MutableLiveData<List<WeatherList>> {
//        viewModelScope.launch {
//            cityList.value = getAllCityUseCase.execute().value
//        }
//        return cityList
//    }
//
//    fun getWeatherForCity(city: String):LiveData<WeatherList>? = liveData {
//        val weatherList: WeatherList? = getWeatherDetailsForCityUseCase.execute(city)
//        weatherList?.let { emit(it) }
//    }
//
//    suspend fun updateWeatherForDbCities() {
//        updateWeatherDetailsForCityUseCase.execute()
//    }
//
//    fun getAllWeatherDetails():LiveData<List<WeatherList>?> = liveData {
//        val list :List<WeatherList>?=getAllWeatherDetailsUseCase.execute()
//        emit(list)
//    }

