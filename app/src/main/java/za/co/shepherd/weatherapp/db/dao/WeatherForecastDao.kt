package za.co.shepherd.weatherapp.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import za.co.shepherd.weatherapp.db.entities.WeatherForecastEntity

@Dao
interface WeatherForecastDao {
    @Query("SELECT * FROM Forecast")
    fun getWeatherForecast(): LiveData<WeatherForecastEntity>

    @Query("SELECT * FROM Forecast ORDER BY abs(lat-:latitude) AND abs(lon-:longitude) LIMIT 1")
    fun getWeatherForecastByCoordinates(latitude: Double, longitude: Double): LiveData<WeatherForecastEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeatherForecast(forecast: WeatherForecastEntity)

    @Transaction
    fun deleteAndInsert(weatherForecast: WeatherForecastEntity) {
        deleteAll()
        insertWeatherForecast(weatherForecast)
    }

    @Update
    fun updateWeatherForecast(weatherForecast: WeatherForecastEntity)

    @Delete
    fun deleteWeatherForecast(weatherForecast: WeatherForecastEntity)

    @Query("DELETE FROM Forecast")
    fun deleteAll()

    @Query("Select count(*) from Forecast")
    fun getCount(): Int
}