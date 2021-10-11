package za.co.shepherd.weatherapp.di

import com.algolia.search.saas.places.PlacesClient
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import za.co.shepherd.weatherapp.db.dao.CitiesForSearchDao
import za.co.shepherd.weatherapp.db.dao.CurrentWeatherDao
import za.co.shepherd.weatherapp.db.dao.WeatherForecastDao
import za.co.shepherd.weatherapp.domain.ApplicationAPI
import za.co.shepherd.weatherapp.domain.datasources.currentWeather.CurrentWeatherLocalDataSource
import za.co.shepherd.weatherapp.domain.datasources.currentWeather.CurrentWeatherRemoteDataSource
import za.co.shepherd.weatherapp.domain.datasources.forecast.WeatherForecastLocalDataSource
import za.co.shepherd.weatherapp.domain.datasources.forecast.WeatherForecastRemoteDataSource
import za.co.shepherd.weatherapp.domain.datasources.searchCities.SearchCitiesLocalDataSource
import za.co.shepherd.weatherapp.domain.datasources.searchCities.SearchCitiesRemoteDataSource
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataSourceModule {

    @Provides
    @Singleton
    fun provideCurrentWeatherRemoteDataSource(applicationAPI: ApplicationAPI) =
        CurrentWeatherRemoteDataSource(applicationAPI)

    @Provides
    @Singleton
    fun provideWeatherForecastRemoteDataSource(applicationAPI: ApplicationAPI) =
        WeatherForecastRemoteDataSource(applicationAPI)

    @Provides
    @Singleton
    fun provideSearchCitiesRemoteDataSource(
        client: PlacesClient,
        moshi: Moshi,
    ) = SearchCitiesRemoteDataSource(client, moshi)

    @Provides
    @Singleton
    fun provideCurrentWeatherLocalDataSource(currentWeatherDao: CurrentWeatherDao) =
        CurrentWeatherLocalDataSource(currentWeatherDao)

    @Provides
    @Singleton
    fun provideWeatherForecastLocalDataSource(weatherForecastDao: WeatherForecastDao) =
        WeatherForecastLocalDataSource(weatherForecastDao)

    @Provides
    @Singleton
    fun provideSearchCitiesLocalDataSource(citiesForSearchDao: CitiesForSearchDao) =
        SearchCitiesLocalDataSource(citiesForSearchDao)
}