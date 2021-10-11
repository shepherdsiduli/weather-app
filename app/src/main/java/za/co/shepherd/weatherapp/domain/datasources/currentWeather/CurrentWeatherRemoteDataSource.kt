package za.co.shepherd.weatherapp.domain.datasources.currentWeather

import io.reactivex.Single
import za.co.shepherd.weatherapp.domain.ApplicationAPI
import za.co.shepherd.weatherapp.domain.model.CurrentWeatherResponse
import javax.inject.Inject

class CurrentWeatherRemoteDataSource @Inject constructor(private val api: ApplicationAPI) {

    fun getCurrentWeatherByGeoCoordinates(latitude: Double, longitude: Double, units: String): Single<CurrentWeatherResponse> = api.getCurrentByGeoCoordinates(
        latitude,
        longitude,
        units
    )
}