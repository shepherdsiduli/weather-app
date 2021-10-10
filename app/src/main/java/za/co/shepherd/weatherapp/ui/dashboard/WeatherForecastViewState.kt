package za.co.shepherd.weatherapp.ui.dashboard

import za.co.shepherd.weatherapp.core.BaseViewState
import za.co.shepherd.weatherapp.db.entities.WeatherForecastEntity
import za.co.shepherd.weatherapp.utils.domain.Status

class WeatherForecastViewState(
    val status: Status,
    val error: String? = null,
    val data: WeatherForecastEntity? = null
) : BaseViewState(status, error) {
    fun getForecast() = data
}