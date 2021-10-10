package za.co.shepherd.weatherapp.domain

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import za.co.shepherd.weatherapp.domain.model.CurrentWeatherResponse
import za.co.shepherd.weatherapp.domain.model.ForecastResponse

interface ApplicationAPI {

    @GET("forecast")
    fun getForecastByGeoCoordinates(
        @Query("lat")
        latitude: Double,
        @Query("lon")
        longitude: Double,
        @Query("units")
        units: String
    ): Single<ForecastResponse>

    @GET("weather")
    fun getCurrentByGeoCoordinates(
        @Query("lat")
        latitude: Double,
        @Query("lon")
        longitude: Double,
        @Query("units")
        units: String
    ): Single<CurrentWeatherResponse>
}