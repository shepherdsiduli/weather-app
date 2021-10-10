package za.co.shepherd.weatherapp.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import za.co.shepherd.weatherapp.db.entities.WeatherForecastEntity

@Dao
interface WeatherForecastDao {
    @Query("SELECT * FROM Forecast")
    fun getForecast(): LiveData<WeatherForecastEntity>

    @Query("SELECT * FROM Forecast ORDER BY abs(lat-:latitude) AND abs(lon-:longitude) LIMIT 1")
    fun getForecastByCoord(latitude: Double, longitude: Double): LiveData<WeatherForecastEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertForecast(forecast: WeatherForecastEntity)

    @Transaction
    fun deleteAndInsert(forecast: WeatherForecastEntity) {
        deleteAll()
        insertForecast(forecast)
    }

    @Update
    fun updateForecast(forecast: WeatherForecastEntity)

    @Delete
    fun deleteForecast(forecast: WeatherForecastEntity)

    @Query("DELETE FROM Forecast")
    fun deleteAll()

    @Query("Select count(*) from Forecast")
    fun getCount(): Int
}