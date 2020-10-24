package com.example.weatherapp_sample.data.model


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.weatherapp_sample.data.typeConverters.DataConverter
import com.google.gson.annotations.SerializedName

@Entity(tableName = "weather_details")
data class WeatherList(
    @SerializedName("cod")
    val cod: Int,
    @SerializedName("dt")
    val dt: Int,
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @Embedded(prefix = "main_")
    @SerializedName("main")
    val main: Main,
    @SerializedName("name")
    val name: String,
    @SerializedName("visibility")
    val visibility: Int,
    @TypeConverters(DataConverter::class)
    @SerializedName("weather")
    val weather: List<Weather>,
    @Embedded(prefix = "wind_")
    @SerializedName("wind")
    val wind: Wind

)