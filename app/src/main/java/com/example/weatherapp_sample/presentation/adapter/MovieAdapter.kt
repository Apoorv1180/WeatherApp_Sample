package com.example.weatherapp_sample.presentation.adapter

import android.graphics.Color

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

import com.example.weatherapp_sample.R
import com.example.weatherapp_sample.data.model.WeatherList
import com.example.weatherapp_sample.databinding.SingleRecyclerViewItemBinding


class MovieAdapter(val itemClickListener: (WeatherList) -> Unit) :
    RecyclerView.Adapter<MyViewHolder>() {
    private val movieList = ArrayList<WeatherList>()

    fun setList(movies: List<WeatherList>) {
        movieList.clear()
        movieList.addAll(movies)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: SingleRecyclerViewItemBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.single_recycler_view_item,
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(movieList[position], itemClickListener)
    }
}

class MyViewHolder(
    val binding: SingleRecyclerViewItemBinding,
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(movie: WeatherList, itemClickListener: (WeatherList) -> Unit) {
        binding.txtCityName.text = movie.name
        binding.txtTemp.text = movie.main.temp.toString() + "\u00B0"
        binding.txtTemp.setTextColor(Color.RED)
        binding.cardBack.setOnClickListener { itemClickListener(movie) }
    }

}