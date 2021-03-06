package za.co.shepherd.weatherapp.repo

import androidx.lifecycle.LiveData
import io.reactivex.Single
import za.co.shepherd.weatherapp.core.Constants.OpenWeatherNetworkService.RATE_LIMITER_TYPE
import za.co.shepherd.weatherapp.db.entities.WeatherForecastEntity
import za.co.shepherd.weatherapp.domain.datasources.forecast.WeatherForecastLocalDataSource
import za.co.shepherd.weatherapp.domain.datasources.forecast.WeatherForecastRemoteDataSource
import za.co.shepherd.weatherapp.domain.model.ForecastResponse
import za.co.shepherd.weatherapp.utils.domain.FrequencyLimiter
import za.co.shepherd.weatherapp.utils.domain.Resource
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WeatherForecastRepository @Inject constructor(
    private val weatherForecastRemoteDataSource: WeatherForecastRemoteDataSource,
    private val weatherForecastLocalDataSource: WeatherForecastLocalDataSource
) {

    private val forecastListRateLimit = FrequencyLimiter<String>(30, TimeUnit.SECONDS)

    fun loadWeatherForecastByCoordinates(latitude: Double, longitude: Double, fetchRequired: Boolean, units: String): LiveData<Resource<WeatherForecastEntity>> {
        return object : NetworkBoundResource<WeatherForecastEntity, ForecastResponse>() {
            override fun saveCallResponse(item: ForecastResponse) = weatherForecastLocalDataSource.insertWeatherForecast(
                item
            )

            override fun shouldFetch(data: WeatherForecastEntity?): Boolean = fetchRequired

            override fun loadFromDatabase(): LiveData<WeatherForecastEntity> = weatherForecastLocalDataSource.getWeatherForecast()

            override fun createCall(): Single<ForecastResponse> = weatherForecastRemoteDataSource.getWeatherForecastByGeoCoordinates(
                latitude,
                longitude,
                units
            )

            override fun onFetchFailed() = forecastListRateLimit.reset(RATE_LIMITER_TYPE)
        }.asLiveData
    }
}
