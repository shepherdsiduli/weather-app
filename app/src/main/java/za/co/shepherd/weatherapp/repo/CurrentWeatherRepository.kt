package za.co.shepherd.weatherapp.repo

import androidx.lifecycle.LiveData
import io.reactivex.Single
import za.co.shepherd.weatherapp.core.Constants.OpenWeatherNetworkService.RATE_LIMITER_TYPE
import za.co.shepherd.weatherapp.db.entities.CurrentWeatherEntity
import za.co.shepherd.weatherapp.domain.datasources.currentWeather.CurrentWeatherLocalDataSource
import za.co.shepherd.weatherapp.domain.datasources.currentWeather.CurrentWeatherRemoteDataSource
import za.co.shepherd.weatherapp.domain.model.CurrentWeatherResponse
import za.co.shepherd.weatherapp.utils.domain.FrequencyLimiter
import za.co.shepherd.weatherapp.utils.domain.Resource
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CurrentWeatherRepository @Inject constructor(
    private val currentWeatherRemoteDataSource: CurrentWeatherRemoteDataSource,
    private val currentWeatherLocalDataSource: CurrentWeatherLocalDataSource
) {

    private val currentWeatherRateLimit = FrequencyLimiter<String>(30, TimeUnit.SECONDS)

    fun loadCurrentWeatherByGeoCoordinates(
        latitude: Double,
        longitude: Double,
        fetchRequired: Boolean,
        units: String
    ): LiveData<Resource<CurrentWeatherEntity>> {
        return object : NetworkBoundResource<CurrentWeatherEntity, CurrentWeatherResponse>() {
            override fun saveCallResponse(item: CurrentWeatherResponse) = currentWeatherLocalDataSource.insertCurrentWeather(
                item
            )

            override fun shouldFetch(data: CurrentWeatherEntity?): Boolean = fetchRequired

            override fun loadFromDatabase(): LiveData<CurrentWeatherEntity> = currentWeatherLocalDataSource.getCurrentWeather()

            override fun createCall(): Single<CurrentWeatherResponse> = currentWeatherRemoteDataSource.getCurrentWeatherByGeoCoordinates(
                latitude,
                longitude,
                units
            )

            override fun onFetchFailed() = currentWeatherRateLimit.reset(RATE_LIMITER_TYPE)
        }.asLiveData
    }
}