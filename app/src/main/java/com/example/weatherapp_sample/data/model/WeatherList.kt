package com.example.weatherapp_sample.data.model


import androidx.databinding.BindingAdapter
import androidx.room.*
import com.example.weatherapp_sample.data.typeConverters.DataConverter
import com.google.gson.annotations.SerializedName

@Entity(tableName = "weather_details")
data class WeatherList(
    @SerializedName("cod")
    @ColumnInfo(name = "cod")
    val cod: Int,
    @ColumnInfo(name = "date")
    @SerializedName("date")
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
    @Embedded(prefix = "sys_")
    @SerializedName("sys")
    val sys: Sys,
    @TypeConverters(DataConverter::class)
    @SerializedName("weather")
    val weather: List<Weather>,
    @Embedded(prefix = "wind_")
    @SerializedName("wind")
    val wind: Wind

)
