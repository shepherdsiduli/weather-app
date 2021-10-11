package za.co.shepherd.weatherapp.domain.datasources.forecast

import io.reactivex.Single
import za.co.shepherd.weatherapp.domain.ApplicationAPI
import za.co.shepherd.weatherapp.domain.model.ForecastResponse

class WeatherForecastRemoteDataSource constructor(private val api: ApplicationAPI) {

    fun getWeatherForecastByGeoCoordinates(latitude: Double, longitude: Double, units: String): Single<ForecastResponse> = api.getForecastByGeoCoordinates(
        latitude,
        longitude,
        units
    )
}