package za.co.shepherd.weatherapp.ui.dashboard

import za.co.shepherd.weatherapp.core.BaseViewState
import za.co.shepherd.weatherapp.db.entities.CurrentWeatherEntity
import za.co.shepherd.weatherapp.utils.domain.Status

class CurrentWeatherViewState(
    val status: Status,
    val error: String? = null,
    val data: CurrentWeatherEntity? = null
) : BaseViewState(status, error) {
    fun getForecast() = data
}