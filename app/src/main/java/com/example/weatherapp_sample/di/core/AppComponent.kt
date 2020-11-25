package com.example.weatherapp_sample.di.core

import com.example.weatherapp_sample.di.weathermodule.WeatherSubComponent
import dagger.Component
import dagger.Subcomponent
import javax.inject.Singleton

@Singleton
@Component
    (
    modules = [
        AppModule::class,
        NetModule::class,
        DataBaseModule::class,
        UseCaseModule::class,
        RepositoryModule::class,
        RemoteDataSourceModule::class,
        DbDataSourceModule::class]
)
interface AppComponent {

    fun weatherSubComponent(): WeatherSubComponent.Factory

}