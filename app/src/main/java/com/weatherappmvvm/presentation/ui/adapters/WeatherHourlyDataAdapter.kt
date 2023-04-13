package com.weatherappmvvm.presentation.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weatherappmvvm.R
import com.weatherappmvvm.data.datasource.local.ApplicationEnum
import com.weatherappmvvm.data.datasource.remote.model.WeatherDataInformationModel
import com.weatherappmvvm.data.manager.DateFormatManager
import com.weatherappmvvm.data.manager.DateFormatManager.formatDateTo
import com.weatherappmvvm.databinding.ElementWeatherHourlyDataBinding

class WeatherHourlyDataAdapter(
    private val context: Context,
) : RecyclerView.Adapter<WeatherHourlyDataAdapter.WeatherDataInformationItemViewHolder>() {

    private var listOfHourlyData = listOf<WeatherDataInformationModel>()

    // Here we set default value for the location name to Sofia
    private var weatherDataLocation: String = ApplicationEnum.CityName.SOFIA.cityName

    fun setListOfWeatherData(
        listOfWeatherHourlyData: List<WeatherDataInformationModel>,
        locationName: String,
    ) {
        listOfHourlyData = listOfWeatherHourlyData

        weatherDataLocation = locationName
        // Here we update the recycler view with the new data from range 0 to the size of the list
        notifyItemRangeChanged(0, listOfWeatherHourlyData.size)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): WeatherDataInformationItemViewHolder {
        val binding = ElementWeatherHourlyDataBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return WeatherDataInformationItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listOfHourlyData.count()
    }

    override fun onBindViewHolder(holder: WeatherDataInformationItemViewHolder, position: Int) {
        holder.bind(listOfHourlyData[position])
    }

    inner class WeatherDataInformationItemViewHolder(private val binding: ElementWeatherHourlyDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(weatherData: WeatherDataInformationModel) {
            setTextViewText(weatherData)
        }

        private fun setTextViewText(weatherData: WeatherDataInformationModel) {
            val weatherDataTimeInFormat = weatherData.time.formatDateTo(
                DateFormatManager.DEFAULT_WEATHER_DATA_DATE_FORMAT,
                DateFormatManager.YEAR_MONTH_DAY_HOUR_MINUTES_FORMAT,
            )
            binding.textViewWeatherHeadline.text =
                context.getString(
                    R.string.weather_hourly_data_adapter_headline,
                    weatherDataLocation,
                )
            binding.textViewWeatherTime.text =
                context.getString(
                    R.string.weather_hourly_data_adapter_time,
                    weatherDataTimeInFormat,
                )

            binding.textViewWeatherTemperature.text = context.getString(
                R.string.weather_hourly_data_adapter_temperature,
                weatherData.temperature,
            )
            binding.textViewWeatherCondition.text = context.getString(
                R.string.weather_hourly_data_adapter_condition,
                weatherData.weather.condition,
            )
            binding.textViewWeatherWindSpeed.text = context.getString(
                R.string.weather_hourly_data_adapter_windSpeed,
                weatherData.windSpeed,
            )
        }
    }
}
