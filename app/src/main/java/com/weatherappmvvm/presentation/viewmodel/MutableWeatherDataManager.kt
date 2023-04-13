package com.weatherappmvvm.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.weatherappmvvm.data.datasource.remote.model.WeatherDataInformationModel

class MutableWeatherDataManager :
    MutableLiveData<FetchDataState<List<WeatherDataInformationModel>>>() {

    fun setValueOnSuccess(data: List<WeatherDataInformationModel>) {
        postValue(FetchDataState.OnSuccessResponse(data))
    }

    fun setError(errorMessage: String?) {
        FetchDataState.OnFailedResponse(errorMessage)
    }
}
