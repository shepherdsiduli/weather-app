package za.co.shepherd.weatherapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import za.co.shepherd.weatherapp.domain.datasources.currentWeather.CurrentWeatherLocalDataSource
import za.co.shepherd.weatherapp.domain.datasources.currentWeather.CurrentWeatherRemoteDataSource
import za.co.shepherd.weatherapp.domain.datasources.forecast.WeatherForecastLocalDataSource
import za.co.shepherd.weatherapp.domain.datasources.forecast.WeatherForecastRemoteDataSource
import za.co.shepherd.weatherapp.domain.datasources.searchCities.SearchCitiesLocalDataSource
import za.co.shepherd.weatherapp.domain.datasources.searchCities.SearchCitiesRemoteDataSource
import za.co.shepherd.weatherapp.repo.CurrentWeatherRepository
import za.co.shepherd.weatherapp.repo.SearchCitiesRepository
import za.co.shepherd.weatherapp.repo.WeatherForecastRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCurrentWeatherRepository(
        currentWeatherRemoteDataSource: CurrentWeatherRemoteDataSource,
        currentWeatherLocalDataSource: CurrentWeatherLocalDataSource,
    ) = CurrentWeatherRepository(currentWeatherRemoteDataSource, currentWeatherLocalDataSource)

    @Provides
    @Singleton
    fun provideWeatherForecastRepository(
        weatherForecastRemoteDataSource: WeatherForecastRemoteDataSource,
        weatherForecastLocalDataSource: WeatherForecastLocalDataSource,
    ) = WeatherForecastRepository(weatherForecastRemoteDataSource, weatherForecastLocalDataSource)

    @Provides
    @Singleton
    fun provideSearchCitiesRepository(
        searchCitiesRemoteDataSource: SearchCitiesRemoteDataSource,
        searchCitiesLocalDataSource: SearchCitiesLocalDataSource,
    ) = SearchCitiesRepository(searchCitiesRemoteDataSource, searchCitiesLocalDataSource)

}