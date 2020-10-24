package com.example.weatherapp_sample.data.api


import com.example.weatherapp_sample.data.model.WeatherList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("data/2.5/weather")
    suspend fun getWeatherForCity(@Query("appid") appid: String,
    @Query("q") q :String):Response<WeatherList>

}