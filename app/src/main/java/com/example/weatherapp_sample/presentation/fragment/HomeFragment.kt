package com.example.weatherapp_sample.presentation.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp_sample.R
import com.example.weatherapp_sample.databinding.FragmentHomeBinding
import com.example.weatherapp_sample.di.Injector
import com.example.weatherapp_sample.presentation.viewmodel.WeatherViewModel
import com.example.weatherapp_sample.presentation.viewmodel.WeatherViewModelFactory
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject


class HomeFragment : Fragment() {
    @Inject
    lateinit var factory : WeatherViewModelFactory
    private lateinit var weatherViewModel: WeatherViewModel

    private lateinit var binding: FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity().application as Injector).createWeatherSubComponent().inject(this)
        weatherViewModel = ViewModelProvider(this,factory).get(WeatherViewModel::class.java)
        val responseCities = weatherViewModel.getAllCities()
        responseCities.observe(viewLifecycleOwner, Observer { Log.e("city",it.toString()) })

        include.setOnClickListener{
            val responseData = weatherViewModel.getWeatherForCity("Pune")
            if (responseData != null) {
                responseData.observe(viewLifecycleOwner, Observer { Log.e("data",it.toString()) })
            }
        }
        // val responseData = weatherViewModel.getWeatherForCity("Pune")



    }
    companion object {

    }
}