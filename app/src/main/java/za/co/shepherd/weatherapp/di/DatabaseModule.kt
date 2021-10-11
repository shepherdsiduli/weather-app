package za.co.shepherd.weatherapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import za.co.shepherd.weatherapp.db.WeatherDatabase
import za.co.shepherd.weatherapp.db.dao.CitiesForSearchDao
import za.co.shepherd.weatherapp.db.dao.CurrentWeatherDao
import za.co.shepherd.weatherapp.db.dao.WeatherForecastDao
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): WeatherDatabase =
        Room.databaseBuilder(
            context,
            WeatherDatabase::class.java,
            "WeatherApp-DB"
        ).build()

    @Provides
    @Singleton
    fun provideWeatherForecastDao(database: WeatherDatabase): WeatherForecastDao = database.weatherForecastDao()

    @Provides
    @Singleton
    fun provideCurrentWeatherDao(database: WeatherDatabase): CurrentWeatherDao = database.currentWeatherDao()

    @Provides
    @Singleton
    fun provideCitiesForSearchDao(database: WeatherDatabase): CitiesForSearchDao = database.citiesForSearchDao()

}