package com.example.weatherapp_sample.di.weathermodule


import com.example.weatherapp_sample.presentation.activtiy.HomeActivity
import com.example.weatherapp_sample.presentation.fragment.HomeFragment
import dagger.Subcomponent

@WeatherScope
@Subcomponent(modules = [WeatherModule::class])
interface WeatherSubComponent {

    fun inject(homeActivity: HomeActivity)

    fun inject(homeFragment: HomeFragment)

    @Subcomponent.Factory
    interface Factory{
        fun create (): WeatherSubComponent
    }
}