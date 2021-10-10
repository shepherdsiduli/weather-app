package za.co.shepherd.weatherapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import za.co.shepherd.weatherapp.db.dao.CitiesForSearchDao
import za.co.shepherd.weatherapp.db.dao.CurrentWeatherDao
import za.co.shepherd.weatherapp.db.dao.WeatherForecastDao
import za.co.shepherd.weatherapp.db.entities.CitiesForSearchEntity
import za.co.shepherd.weatherapp.db.entities.CurrentWeatherEntity
import za.co.shepherd.weatherapp.db.entities.WeatherForecastEntity
import za.co.shepherd.weatherapp.utils.DataConverter

@Database(
    entities = [
        WeatherForecastEntity::class,
        CurrentWeatherEntity::class,
        CitiesForSearchEntity::class
    ],
    version = 1
)
@TypeConverters(DataConverter::class)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherForecastDao(): WeatherForecastDao

    abstract fun currentWeatherDao(): CurrentWeatherDao

    abstract fun citiesForSearchDao(): CitiesForSearchDao
}