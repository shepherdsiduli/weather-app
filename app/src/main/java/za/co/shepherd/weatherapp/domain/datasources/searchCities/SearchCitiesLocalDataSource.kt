package za.co.shepherd.weatherapp.domain.datasources.searchCities

import androidx.lifecycle.LiveData
import za.co.shepherd.weatherapp.db.dao.CitiesForSearchDao
import za.co.shepherd.weatherapp.db.entities.CitiesForSearchEntity
import za.co.shepherd.weatherapp.domain.model.SearchResponse
import javax.inject.Inject

class SearchCitiesLocalDataSource @Inject constructor(
    private val citiesForSearchDao: CitiesForSearchDao
) {

    fun getCityByName(cityName: String?): LiveData<List<CitiesForSearchEntity>> = citiesForSearchDao.getCityByName(
        cityName
    )

    fun insertCities(response: SearchResponse) {
        response.hits
            ?.map { CitiesForSearchEntity(it) }
            ?.let { citiesForSearchDao.insertCities(it) }
    }
}
