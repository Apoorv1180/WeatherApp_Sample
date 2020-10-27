package com.example.weatherapp_sample.data.model


import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.weatherapp_sample.R
import com.google.gson.annotations.SerializedName


data class Weather(
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val main: String
)
