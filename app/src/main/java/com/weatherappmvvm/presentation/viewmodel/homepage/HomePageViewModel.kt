package com.weatherappmvvm.presentation.viewmodel.homepage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherappmvvm.data.datasource.local.ApplicationEnum
import com.weatherappmvvm.data.datasource.remote.api.WeatherCoroutinesApi
import com.weatherappmvvm.data.datasource.remote.model.WeatherDataInformationModel
import com.weatherappmvvm.data.datasource.remote.model.WeatherResponseData
import com.weatherappmvvm.data.datasource.remote.network.APIError
import com.weatherappmvvm.data.datasource.remote.network.response
import com.weatherappmvvm.domain.GetWeatherDataUseCase
import com.weatherappmvvm.presentation.viewmodel.FetchDataState
import com.weatherappmvvm.presentation.viewmodel.MutableWeatherDataManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val weatherRestApi: WeatherCoroutinesApi,
    private val weatherDataUseCase: GetWeatherDataUseCase,
) : ViewModel() {

    // We create livedata of type FetchDataState to observe the state of the request
    private val _weatherData = MutableWeatherDataManager()
    val weatherData: LiveData<FetchDataState<List<WeatherDataInformationModel>>> = _weatherData

    // By default we get Sofia weather on app open
    // This is here because we want to have the city name in the UI when screen rotates
    private val _cityName = MutableLiveData<ApplicationEnum.CityName>()
    val cityName: LiveData<ApplicationEnum.CityName> = _cityName

    private fun setCityName(name: ApplicationEnum.CityName) = _cityName.postValue(name)

    // We call the method here to fetch the data from the API on ViewModel create
    init {
        getWeatherData(cityName = ApplicationEnum.CityName.SOFIA)
    }

    fun getWeatherData(
        weatherLocationLatLng: ApplicationEnum.CityLatLong = ApplicationEnum.CityLatLong.SOFIA,
        cityName: ApplicationEnum.CityName,
    ) {
        // We create a new coroutine scope to run the API call on a background thread
        // We use Dispatchers.IO because we are making a network call
        viewModelScope.launch(Dispatchers.IO) {
            val weatherDataResponse = weatherRestApi.getWeatherData(
                weatherLocationLatLng.latitude,
                weatherLocationLatLng.longitude,
            )
            setCityName(cityName)

            handleWeatherRequest(weatherDataResponse)
        }
    }

    private fun handleWeatherRequest(weatherDataResponse: Response<WeatherResponseData>) {
        weatherDataResponse.response(
            onSuccess = { weatherData -> handleAndReturnSuccessResponse(weatherData) },
            onError = { error -> handleAndReturnRequestFail(error) },
        )
    }

    private fun handleAndReturnSuccessResponse(weatherDataResponse: WeatherResponseData) {
        val listOfContent = weatherDataUseCase.handleSuccessResponse(weatherDataResponse)
        _weatherData.setValueOnSuccess(listOfContent)
    }

    private fun handleAndReturnRequestFail(error: APIError) {
        val errorMessage = weatherDataUseCase.handleErrorResponse(error)
        _weatherData.setError(errorMessage)
    }
}
