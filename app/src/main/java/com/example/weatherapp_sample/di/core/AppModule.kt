package com.example.weatherapp_sample.di.core

import android.content.Context
import com.example.weatherapp_sample.di.weathermodule.WeatherSubComponent
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(subcomponents = [WeatherSubComponent::class])
class AppModule(private val context: Context) {

    @Singleton
    @Provides
    fun provideApplicationContext():Context { return context.applicationContext}
}