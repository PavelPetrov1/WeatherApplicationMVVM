package com.weatherappmvvm.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.weatherappmvvm.data.datasource.local.ApplicationEnum
import com.weatherappmvvm.data.datasource.remote.model.WeatherDataInformationModel
import com.weatherappmvvm.databinding.FragmentHomeBinding
import com.weatherappmvvm.presentation.ui.adapters.WeatherHourlyDataAdapter
import com.weatherappmvvm.presentation.viewmodel.FetchDataState
import com.weatherappmvvm.presentation.viewmodel.homepage.HomePageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // We set the view model as viewModels()
    // we want to have its lifecycle tied to the fragment
    private val mHomePageViewModel: HomePageViewModel by viewModels()

    private lateinit var mWeatherHourlyDataAdapter: WeatherHourlyDataAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mWeatherHourlyDataAdapter = WeatherHourlyDataAdapter(requireContext())

        initRecyclerView()

        initClicks()

        setupAndHandleViewModelData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initClicks() {
        binding.buttonSofia.setOnClickListener {
            mHomePageViewModel.getWeatherData(cityName = ApplicationEnum.CityName.SOFIA)
        }
        binding.buttonBurgas.setOnClickListener {
            mHomePageViewModel.getWeatherData(
                ApplicationEnum.CityLatLong.BURGAS,
                ApplicationEnum.CityName.BURGAS,
            )
        }
        binding.buttonPlovdiv.setOnClickListener {
            mHomePageViewModel.getWeatherData(
                ApplicationEnum.CityLatLong.PLOVDIV,
                ApplicationEnum.CityName.PLOVDIV,
            )
        }
    }

    private fun initRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.weatherDataRecycler.layoutManager = linearLayoutManager
        binding.weatherDataRecycler.adapter = mWeatherHourlyDataAdapter
    }

    // Here we are observing our liveData and on state changed we are handling the data
    private fun setupAndHandleViewModelData() {
        mHomePageViewModel.weatherData.observe(viewLifecycleOwner) { weatherData ->
            when (weatherData) {
                is FetchDataState.OnSuccessResponse -> {
                    onDataReceiveHideLoadingAndSetRecycler(weatherData.data)
                }

                is FetchDataState.OnFailedResponse -> {
                    binding.loadingGroup.visibility = View.INVISIBLE
                }
            }
        }
    }

    private fun onDataReceiveHideLoadingAndSetRecycler(
        listOfRailRoadData: List<WeatherDataInformationModel>,
    ) {
        binding.loadingGroup.visibility = View.INVISIBLE

        // Here we return default value if the value is null
        val cityName =
            mHomePageViewModel.cityName.value?.cityName ?: ApplicationEnum.CityName.SOFIA.cityName
        mWeatherHourlyDataAdapter.setListOfWeatherData(listOfRailRoadData, cityName)
    }
}
