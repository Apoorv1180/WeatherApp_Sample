package com.example.weatherapp_sample.presentation.fragment

import android.app.SearchManager
import android.database.Cursor
import android.database.MatrixCursor
import android.os.Bundle
import android.provider.BaseColumns
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.AutoCompleteTextView
import androidx.appcompat.widget.SearchView
import androidx.cursoradapter.widget.CursorAdapter
import androidx.cursoradapter.widget.SimpleCursorAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
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
    lateinit var factory: WeatherViewModelFactory
    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var adapter: MovieAdapter
    private lateinit var binding: FragmentHomeBinding
    private lateinit var cursorAdapter: SimpleCursorAdapter
    private lateinit var cursor: MatrixCursor

    //variables
    private lateinit var from: Array<String>
    private lateinit var to: IntArray
    private var suggestions: List<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        initInjection()
        initViewModel()
        initRecyclerView()
        initSearch()
        return binding.root
    }


    private fun initSearch() {
        setSearchClickListener()
        setSuggestionOnSearch()
        searchQueryFunctioning()
    }

    private fun setSearchClickListener() {
        binding.include.searchButton.setOnClickListener {
            if (!TextUtils.isEmpty(autoCompleteCity.query.toString())) {
                hideKeyboard()
                observeSearchedCity()
            }
        }
    }


    private fun setSuggestionOnSearch() {
        binding.include.autoCompleteCity.findViewById<AutoCompleteTextView>(R.id.search_src_text).threshold =
            1
        from = arrayOf(SearchManager.SUGGEST_COLUMN_TEXT_1)
        to = intArrayOf(R.id.item_label)
        cursor = MatrixCursor(arrayOf(BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1))
        cursorAdapter = SimpleCursorAdapter(
            context,
            R.layout.search_item,
            null,
            from,
            to,
            CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        )
        binding.include.autoCompleteCity.suggestionsAdapter = cursorAdapter
    }

    private fun observeAllWeatherData() {
        weatherViewModel.getAllWeatherDetails().observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                adapter.setList(it)
                adapter.notifyDataSetChanged()
                setData(it.get(0))
                setSuggestion(it)
            }
        }
        )
    }

    private fun setSuggestion(list: List<WeatherList>) {
        suggestions = weatherViewModel.setSuggestion(list)
    }


    private fun observeSearchedCity() {
        weatherViewModel.fetchCity(autoCompleteCity.query.toString())
            .observe(viewLifecycleOwner,
                Observer {
                    setData(it)
                    Log.e("it", it.toString())
                })
    }

    private fun searchQueryFunctioning() {
        binding.include.autoCompleteCity.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                hideKeyboard()
                if (query != null) {
                    binding.include.autoCompleteCity.setQuery(query, false)
                }
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                val cursor =
                    MatrixCursor(arrayOf(BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1))
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

        binding.include.autoCompleteCity.setOnSuggestionListener(object :
            SearchView.OnSuggestionListener {
            override fun onSuggestionSelect(position: Int): Boolean {
                return false
            }

            override fun onSuggestionClick(position: Int): Boolean {
                hideKeyboard()
                val cursor = autoCompleteCity.suggestionsAdapter.getItem(position) as Cursor
                val selection =
                    cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1))
                autoCompleteCity.setQuery(selection, false)
                return true
            }
        })
    }

    private fun setData(it: WeatherList?) {
        if (it != null) {
            binding.weather = it
            binding.mainLayout.present = true
        } else {
            binding.mainLayout.present = false
        }
    }

    private fun initRecyclerView() {
        val mLayoutManager = LinearLayoutManager(requireContext())
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.recyclerView.layoutManager = mLayoutManager
        binding.recyclerView.itemAnimator = DefaultItemAnimator()
        adapter = MovieAdapter(itemOnClick)
        binding.recyclerView.adapter = adapter
        observeAllWeatherData()
    }

    private fun initInjection() {
        (requireActivity().application as Injector).createWeatherSubComponent().inject(this)
    }

    private fun initViewModel() {
        weatherViewModel = ViewModelProvider(this, factory).get(WeatherViewModel::class.java)
    }

    val itemOnClick: (WeatherList) -> Unit = { item ->
        setData(item)
    }
}