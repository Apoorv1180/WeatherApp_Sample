package com.example.weatherapp_sample.di.core

import android.content.Context
import androidx.room.Room
import com.example.weatherapp_sample.data.db.AppDatabase
import com.example.weatherapp_sample.data.db.WeatherDao


import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataBaseModule {
    @Singleton
    @Provides
    fun provideWeatherDataBase(context: Context): AppDatabase {
     return Room.databaseBuilder(context,AppDatabase::class.java,"weather_database")
         .build()
    }
    @Singleton
    @Provides
    fun provideWeatherDao(appDatabase: AppDatabase): WeatherDao {
        return appDatabase.weatherDao()
    }
}