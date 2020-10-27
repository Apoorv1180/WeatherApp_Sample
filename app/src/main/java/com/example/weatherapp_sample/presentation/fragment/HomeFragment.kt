package com.example.weatherapp_sample.presentation.fragment

import android.app.SearchManager
import android.database.Cursor
import android.database.MatrixCursor
import android.os.Bundle
import android.provider.BaseColumns
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.cursoradapter.widget.CursorAdapter
import androidx.cursoradapter.widget.SimpleCursorAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp_sample.R
import com.example.weatherapp_sample.data.model.WeatherList
import com.example.weatherapp_sample.databinding.FragmentHomeBinding
import com.example.weatherapp_sample.di.Injector
import com.example.weatherapp_sample.domain.util.hideKeyboard
import com.example.weatherapp_sample.presentation.adapter.MovieAdapter
import com.example.weatherapp_sample.presentation.viewmodel.WeatherViewModel
import com.example.weatherapp_sample.presentation.viewmodel.WeatherViewModelFactory
import kotlinx.android.synthetic.main.custom_toolbar.*
import javax.inject.Inject


class HomeFragment : Fragment() {
    @Inject
    lateinit var factory : WeatherViewModelFactory
    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var adapter:MovieAdapter
    private lateinit var cursorAdapter :CursorAdapter
    var suggestions = ArrayList<String>()

    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initInjection()
        //setting up search view with default | no suggestions
        setSearchView()
        //initialising view model
        initViewModel()
        //recycler view for all searched weather history
        // - behavior expected - must update as soon as the query is searched
        initRecyclerView()
        //loads all weather details history [contains observer ]
        displayWeatherDetails()
        //fetches cities searched from cache or database once searched
        // and called for weather details .
        fetchSuggestions()

        //binding search view with cursor adapter
        searchViewQuery()

        //submit search
        searchButton.setOnClickListener { if(!TextUtils.isEmpty(autoCompleteCity.query.toString())){
            observeWeatherData(autoCompleteCity.query.toString())

        }
            hideKeyboard()
        }

        //submit and query change listeners
        searchqueryfunctioning()
    }

    private fun searchqueryfunctioning() {
        binding.include.autoCompleteCity.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                hideKeyboard()
                if(query!=null) {
                    observeWeatherData(query)
                    displayWeatherDetails()
                }
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                val cursor = MatrixCursor(arrayOf(BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1))
                query?.let {
                    suggestions.forEachIndexed { index, suggestion ->
                        if (suggestion.contains(query, true))
                            cursor.addRow(arrayOf(index, suggestion))
                    }
                }

                cursorAdapter.changeCursor(cursor)
                binding.include.autoCompleteCity.setSuggestionsAdapter(cursorAdapter);
                return true
            }
        })

        binding.include.autoCompleteCity.setOnSuggestionListener(object: SearchView.OnSuggestionListener {
            override fun onSuggestionSelect(position: Int): Boolean {
                return false
            }

            override fun onSuggestionClick(position: Int): Boolean {
                hideKeyboard()
                val cursor = autoCompleteCity.suggestionsAdapter.getItem(position) as Cursor
                val selection = cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1))
                autoCompleteCity.setQuery(selection, false)
                // Do something with selection
                observeWeatherData(selection)
                return true
            }
        })
    }

    private fun searchViewQuery() {
        autoCompleteCity.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                observeWeatherData(query)
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    private fun setSearchView() {
        val from = arrayOf(SearchManager.SUGGEST_COLUMN_TEXT_1)
        val to = intArrayOf(R.id.item_label)
        binding.include.autoCompleteCity.findViewById<AutoCompleteTextView>(R.id.search_src_text).threshold = 1
        cursorAdapter = SimpleCursorAdapter(context, R.layout.search_item, null, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER)
    }

    private fun initRecyclerView() {
        val mLayoutManager = LinearLayoutManager(requireContext())
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.recyclerView.layoutManager = mLayoutManager
        binding.recyclerView.itemAnimator = DefaultItemAnimator()
        adapter = MovieAdapter()
        binding.recyclerView.adapter = adapter
    }

    private fun displayWeatherDetails(){
        var responseData = weatherViewModel.getAllWeatherDetails()
        responseData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                adapter.setList(it)
                adapter.notifyDataSetChanged()
                Log.e("Alldata", it.toString())
            }

        })
    }

    private fun initInjection() {
        (requireActivity().application as Injector).createWeatherSubComponent().inject(this)
    }

    private fun initViewModel() {
        weatherViewModel = ViewModelProvider(this, factory).get(WeatherViewModel::class.java)
    }


    private fun observeWeatherData(city: String) {
        weatherViewModel.getWeatherForCity(city)
            ?.observe(viewLifecycleOwner, Observer {
                Log.e("data", it.toString())
                setData(it)
                adapter.notifyDataSetChanged()
                binding.recyclerView.adapter = adapter
                displayWeatherDetails()
            })
    }

    //added for checking
    override fun onResume() {
        super.onResume()
        displayWeatherDetails()
        fetchSuggestions()
    }

    //binding data for single card shown as result
    private fun setData(it: WeatherList?) {
        if(it!=null) {
            binding.weather = it
            binding.mainLayout.present = true
        }else {
            binding.mainLayout.present =false
        }
    }


    private fun fetchSuggestions() {
        var responseCities = weatherViewModel.getAllCities()
        if (responseCities != null) {
            responseCities.observe(viewLifecycleOwner, Observer {
                if (it != null && it.size > 0) {
                    Log.e("city", it.toString())
                    val arr = mutableListOf<String>()
                    for (value in it) {
                        arr.add(value)
                    }

                    suggestions = arr as ArrayList<String>

                }


            })
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar?.setTitle("")
    }


}