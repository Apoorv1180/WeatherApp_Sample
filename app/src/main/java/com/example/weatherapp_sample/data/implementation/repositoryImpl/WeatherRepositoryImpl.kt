package com.example.weatherapp_sample.data.implementation.repositoryImpl

import android.util.Log
import com.example.weatherapp_sample.data.datasource.WeatherCacheDataSource
import com.example.weatherapp_sample.data.datasource.WeatherDbDataSource
import com.example.weatherapp_sample.data.datasource.WeatherRemoteDataSource
import com.example.weatherapp_sample.data.model.WeatherList
import com.example.weatherapp_sample.domain.repository.WeatherRepository

import retrofit2.Response
import java.lang.Exception

class WeatherRepositoryImpl(
    private val weatherRemoteDataSource: WeatherRemoteDataSource,
    private val weatherDbDataSource: WeatherDbDataSource,
    private val weatherCacheDataSource: WeatherCacheDataSource
) : WeatherRepository {
    override suspend fun getAllCities(): List<String> = getCitiesFromCache()


    override suspend fun getWeatherDetailsForCity(city: String): WeatherList = getWeatherDetailsForCityFromCache(city)


    override suspend fun updateWeatherDetailsForDbCities() {
        val cityList: List<String> = weatherDbDataSource.getCityListFromDb()
        for(city in cityList){
            val weather :WeatherList? = getWeatherDetailsForCityFromApi(city)
            weatherDbDataSource.clearAllWeatherList()
            if (weather != null) {
                weatherDbDataSource.saveWeatherDetailsDbForCity(weather)
            }
        }
    }

    override suspend fun getAllWeatherDetails(): List<WeatherList> =  getAllWeatherDetailsFromDb()


    suspend fun getAllWeatherDetailsFromDb():List<WeatherList> {
        lateinit var list:List<WeatherList>

        try {
            list = weatherDbDataSource.getAllWeatherList()
        }
        catch (e:Exception){
            Log.e("Repository1",e.localizedMessage.toString())
        }
        return list
    }


    suspend fun getWeatherDetailsForCityFromApi(city: String) :WeatherList? {
        var weatherList :WeatherList? = null
        try{
                val response : Response<WeatherList>? = weatherRemoteDataSource.getWeatherDetailsRemoteForCity(city)
            val body : WeatherList? = response?.body()
            if (body != null) {
                    weatherList = body
                }

        }catch (e: Exception) {
                Log.e("Repository",e.localizedMessage.toString())
        }
       return weatherList
    }

    suspend fun getWeatherDetailsForCityFromDb(city: String):WeatherList{
        var weatherList:WeatherList? = null
        try{
           weatherList= weatherDbDataSource.getWeatherDetailsDbForCity(city)
        }catch (e:Exception){
            Log.e("Repository",e.localizedMessage.toString())
        }
        if(weatherList!= null){
            return  weatherList
        }
        else {
            weatherList = getWeatherDetailsForCityFromApi(city)
            if (weatherList != null) {
                weatherDbDataSource.saveWeatherDetailsDbForCity(weatherList)
            }
        }
        return weatherList!!
    }

    suspend fun getWeatherDetailsForCityFromCache(city : String):WeatherList{
        var weatherList:WeatherList? = null
        try{
            weatherList = weatherCacheDataSource.getWeatherDetailsFromCityFromCache(city)
        }catch (e:Exception){
            Log.e("Repository",e.localizedMessage.toString())
        }
        if(weatherList!= null){
            return  weatherList
        }
        else {
            weatherList = getWeatherDetailsForCityFromDb(city)
            weatherCacheDataSource.saveWeatherDetailsCacheForCity(weatherList)
        }
        return weatherList!!

    }

    suspend fun getCitiesFromCache():ArrayList<String>{
        lateinit var cityList: ArrayList<String>
        try {
            cityList = weatherCacheDataSource.getCityListFromCache()
        }catch (e:Exception){
            Log.e("Repository",e.localizedMessage.toString())
        }
        if(cityList.size >0){
            return cityList
        }
        else {
            cityList = weatherDbDataSource.getCityListFromDb() as ArrayList<String>
            weatherCacheDataSource.saveCityList(cityList)
        }
        return cityList
    }
}