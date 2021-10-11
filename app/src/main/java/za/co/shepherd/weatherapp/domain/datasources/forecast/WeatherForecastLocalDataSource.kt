package za.co.shepherd.weatherapp.domain.datasources.forecast

import za.co.shepherd.weatherapp.db.dao.WeatherForecastDao
import za.co.shepherd.weatherapp.db.entities.WeatherForecastEntity
import za.co.shepherd.weatherapp.domain.model.ForecastResponse
import javax.inject.Inject

class WeatherForecastLocalDataSource @Inject constructor(private val weatherForecastDao: WeatherForecastDao) {

    fun getWeatherForecast() = weatherForecastDao.getWeatherForecast()

    fun insertWeatherForecast(weatherFocast: ForecastResponse) = weatherForecastDao.deleteAndInsert(
        WeatherForecastEntity(weatherFocast)
    )
}