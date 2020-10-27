package com.example.weatherapp_sample.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp_sample.data.model.WeatherList

@Dao
interface WeatherDao {
    @Query("SELECT name FROM weather_details")
    suspend fun getAllCities() : List<String>

    @Query("SELECT * FROM weather_details")
    suspend fun getAllWeatherDetails() : List<WeatherList>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWeatherDetails(weatherList: WeatherList)

    @Query("DELETE FROM weather_details")
    suspend fun deleteAllWeatherDetails()

    @Query("SELECT * FROM weather_details where name = :city")
    suspend fun getWeatherDetailsForCity(city : String) : WeatherList
}