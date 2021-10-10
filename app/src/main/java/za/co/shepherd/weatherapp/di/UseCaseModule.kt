package za.co.shepherd.weatherapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import za.co.shepherd.weatherapp.domain.usecase.CurrentWeatherUseCase
import za.co.shepherd.weatherapp.domain.usecase.SearchCitiesUseCase
import za.co.shepherd.weatherapp.domain.usecase.WeatherForecastUseCase
import za.co.shepherd.weatherapp.repo.CurrentWeatherRepository
import za.co.shepherd.weatherapp.repo.SearchCitiesRepository
import za.co.shepherd.weatherapp.repo.WeatherForecastRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object UseCaseModule {

    @Provides
    @Singleton
    fun provideCurrentWeatherUseCase(currentWeatherRepository: CurrentWeatherRepository) =
        CurrentWeatherUseCase(currentWeatherRepository)

    @Provides
    @Singleton
    fun provideWeatherForecastUseCase(weatherForecastRepository: WeatherForecastRepository) =
        WeatherForecastUseCase(weatherForecastRepository)

    @Provides
    @Singleton
    fun provideSearchCitiesUseCase(searchCitiesRepository: SearchCitiesRepository) =
        SearchCitiesUseCase(searchCitiesRepository)

}