package za.co.shepherd.weatherapp.viewModel

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import za.co.shepherd.weatherapp.db.WeatherDatabase
import za.co.shepherd.weatherapp.db.dao.WeatherForecastDao
import za.co.shepherd.weatherapp.domain.datasources.forecast.WeatherForecastLocalDataSource
import za.co.shepherd.weatherapp.ui.weather_detail.WeatherDetailViewModel
import za.co.shepherd.weatherapp.utils.createSampleWeatherForecastResponse
import za.co.shepherd.weatherapp.utils.getOrAwaitValue

@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(AndroidJUnit4::class)
class WeatherDetailViewModelShould {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var weatherDetailViewModel: WeatherDetailViewModel
    private lateinit var weatherDatabase: WeatherDatabase
    private lateinit var weatherForecastDao: WeatherForecastDao
    private lateinit var weatherForecastLocalDataSource: WeatherForecastLocalDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        weatherDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WeatherDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        weatherForecastDao = weatherDatabase.weatherForecastDao()
        weatherForecastLocalDataSource = WeatherForecastLocalDataSource(weatherForecastDao)
        weatherDetailViewModel = WeatherDetailViewModel(weatherForecastLocalDataSource)
    }

    @After
    fun closeDatabase() {
        weatherDatabase.close()
    }

    @Test
    fun `insert weather forecast and when getWeatherForecast called the livedata result must be ForecastEntity`() {
        // When
        weatherForecastLocalDataSource.insertWeatherForecast(createSampleWeatherForecastResponse())

        // Then
        val result = weatherDetailViewModel.getWeatherForecast().getOrAwaitValue()
        Truth.assertThat(result.city?.cityName).isEqualTo("Istanbul")
    }
}