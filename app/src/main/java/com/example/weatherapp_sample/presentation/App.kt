package com.example.weatherapp_sample.presentation

import android.app.Application
import com.example.weatherapp_sample.BuildConfig
import com.example.weatherapp_sample.di.Injector
import com.example.weatherapp_sample.di.core.*
import com.example.weatherapp_sample.di.weathermodule.WeatherSubComponent


class App : Application(), Injector {
    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(applicationContext))
            .netModule(NetModule(BuildConfig.BASE_URL))
            .remoteDataSourceModule(RemoteDataSourceModule(BuildConfig.API_KEY))
            .build()

    }

    override fun createWeatherSubComponent(): WeatherSubComponent =appComponent.weatherSubComponent().create()
}