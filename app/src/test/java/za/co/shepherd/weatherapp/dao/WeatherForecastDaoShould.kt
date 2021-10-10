package za.co.shepherd.weatherapp.dao

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import za.co.shepherd.weatherapp.db.WeatherDatabase
import za.co.shepherd.weatherapp.db.dao.WeatherForecastDao
import za.co.shepherd.weatherapp.utils.createSampleForecastWithCoord
import za.co.shepherd.weatherapp.utils.createSampleWeatherForecastResponse
import za.co.shepherd.weatherapp.utils.getOrAwaitValue

@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(AndroidJUnit4::class)
class WeatherForecastDaoShould {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var weatherDatabase: WeatherDatabase
    private lateinit var weatherForecastDao: WeatherForecastDao

    @Before
    fun setUp() {
        weatherDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WeatherDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        weatherForecastDao = weatherDatabase.weatherForecastDao()
    }

    @After
    fun closeDatabase() {
        weatherDatabase.close()
    }

    @Test
    fun `insert a forecast to forecast dao`() {
        // When
        weatherForecastDao.insertForecast(createSampleWeatherForecastResponse(3, "Istanbul"))

        // Then
        val value = weatherForecastDao.getForecast().getOrAwaitValue()
        Truth.assertThat(value.city?.cityCountry).isEqualTo("Turkey")
        Truth.assertThat(value.city?.cityName).isEqualTo("Istanbul")
    }

    @Test
    fun `insert two forecast to forecast dao and then delete all after this operations count must be 0`() {
        // When
        weatherForecastDao.insertForecast(createSampleWeatherForecastResponse(3, "Istanbul"))
        weatherForecastDao.insertForecast(createSampleWeatherForecastResponse(4, "Ankara"))

        val value = weatherForecastDao.getCount()
        Truth.assertThat(value).isEqualTo(2)

        // Then
        weatherForecastDao.deleteAll()
        val newValue = weatherForecastDao.getCount()
        Truth.assertThat(newValue).isEqualTo(0)
    }

    @Test
    fun `insert a forecast and then update`() {
        // When
        weatherForecastDao.insertForecast(createSampleWeatherForecastResponse(1, "Istanbul"))
        val value = weatherForecastDao.getForecast().getOrAwaitValue()
        Truth.assertThat(value.city?.cityName).isEqualTo("Istanbul")

        // Then
        weatherForecastDao.updateForecast(createSampleWeatherForecastResponse(1, "Ankara"))
        val updatedValue = weatherForecastDao.getForecast().getOrAwaitValue()
        Truth.assertThat(updatedValue.city?.cityName).isEqualTo("Ankara")
    }

    @Test
    fun `delete and insert a forecast`() {
        // When
        weatherForecastDao.insertForecast(createSampleWeatherForecastResponse(1, "Istanbul"))
        val count = weatherForecastDao.getCount()
        Truth.assertThat(count).isEqualTo(1)

        // Then
        weatherForecastDao.deleteAndInsert(createSampleWeatherForecastResponse(2, "Adana"))
        val newCount = weatherForecastDao.getCount()
        val value = weatherForecastDao.getForecast().getOrAwaitValue()
        Truth.assertThat(newCount).isEqualTo(1)
        Truth.assertThat(value.city?.cityName).isEqualTo("Adana")
    }

    @Test
    fun `first insert a forecast then delete and count must be zero`() {
        // When
        weatherForecastDao.insertForecast(createSampleWeatherForecastResponse(1, "Kayseri"))
        val count = weatherForecastDao.getCount()
        Truth.assertThat(count).isEqualTo(1)

        // Then
        weatherForecastDao.deleteForecast(createSampleWeatherForecastResponse(1, "Kayseri"))
        val newCount = weatherForecastDao.getCount()
        Truth.assertThat(newCount).isEqualTo(0)
    }

    @Test
    fun `first insert a forecast and then get it with coords`() {
        // When
        weatherForecastDao.insertForecast(createSampleForecastWithCoord(1, "Adana", 1.0, 2.0))
        weatherForecastDao.insertForecast(createSampleForecastWithCoord(3, "Kayseri", 10.0, 30.0))
        val count = weatherForecastDao.getCount()
        Truth.assertThat(count).isEqualTo(2)

        // Then
        val value = weatherForecastDao.getForecastByCoord(10.0, 30.0).getOrAwaitValue()
        Truth.assertThat(value.city?.cityName).isEqualTo("Kayseri")
    }
}