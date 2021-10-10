package za.co.shepherd.weatherapp.repo

import androidx.lifecycle.LiveData
import io.reactivex.Single
import za.co.shepherd.weatherapp.core.Constants.OpenWeatherNetworkService.RATE_LIMITER_TYPE
import za.co.shepherd.weatherapp.db.entities.CitiesForSearchEntity
import za.co.shepherd.weatherapp.domain.datasources.searchCities.SearchCitiesLocalDataSource
import za.co.shepherd.weatherapp.domain.datasources.searchCities.SearchCitiesRemoteDataSource
import za.co.shepherd.weatherapp.domain.model.SearchResponse
import za.co.shepherd.weatherapp.utils.domain.FrequencyLimiter
import za.co.shepherd.weatherapp.utils.domain.Resource
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchCitiesRepository @Inject constructor(
    private val searchCitiesRemoteDataSource: SearchCitiesRemoteDataSource,
    private val searchCitiesLocalDataSource: SearchCitiesLocalDataSource,
) {

    private val rateLimiter = FrequencyLimiter<String>(1, TimeUnit.SECONDS)

    fun loadCitiesByCityName(cityName: String?): LiveData<Resource<List<CitiesForSearchEntity>>> {
        return object : NetworkBoundResource<List<CitiesForSearchEntity>, SearchResponse>() {
            override fun saveCallResponse(item: SearchResponse) = searchCitiesLocalDataSource.insertCities(
                item
            )

            override fun shouldFetch(data: List<CitiesForSearchEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDatabase(): LiveData<List<CitiesForSearchEntity>> = searchCitiesLocalDataSource.getCityByName(
                cityName
            )

            override fun createCall(): Single<SearchResponse> = searchCitiesRemoteDataSource.getCityWithQuery(
                cityName
                    ?: ""
            )

            override fun onFetchFailed() = rateLimiter.reset(RATE_LIMITER_TYPE)
        }.asLiveData
    }
}
