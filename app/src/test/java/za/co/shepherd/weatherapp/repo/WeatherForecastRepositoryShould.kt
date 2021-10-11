package za.co.shepherd.weatherapp.repo

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import za.co.shepherd.weatherapp.core.Constants
import za.co.shepherd.weatherapp.db.entities.WeatherForecastEntity
import za.co.shepherd.weatherapp.domain.datasources.forecast.WeatherForecastLocalDataSource
import za.co.shepherd.weatherapp.domain.datasources.forecast.WeatherForecastRemoteDataSource
import za.co.shepherd.weatherapp.utils.createSampleWeatherForecastResponse
import za.co.shepherd.weatherapp.utils.domain.Resource
import za.co.shepherd.weatherapp.utils.domain.Status


@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(AndroidJUnit4::class)
class WeatherForecastRepositoryShould {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var weatherForecastRemoteDataSource: WeatherForecastRemoteDataSource

    @MockK
    lateinit var weatherForecastLocalDataSource: WeatherForecastLocalDataSource

    private lateinit var weatherForecastRepository: WeatherForecastRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        weatherForecastRepository = WeatherForecastRepository(weatherForecastRemoteDataSource, weatherForecastLocalDataSource)
    }

    @Test
    fun `given fetchRequired = false, when loadForecastByCoord called, then make sure db called`() {
        // Given
        val fetchRequired = false
        val latitude = 30.0
        val longitude = 34.0
        val weatherForecastLiveData: MutableLiveData<WeatherForecastEntity> = MutableLiveData()
        weatherForecastLiveData.postValue(createSampleWeatherForecastResponse(1, "Istanbul"))
        val mockedObserver: Observer<Resource<WeatherForecastEntity>> = mockk(relaxUnitFun = true)

        // When
        every { weatherForecastLocalDataSource.getWeatherForecast() } returns weatherForecastLiveData
        every { weatherForecastRemoteDataSource.getWeatherForecastByGeoCoordinates(latitude, longitude, Constants.Coordinates.METRIC) } returns
                Single.just(createSampleWeatherForecastResponse())

        weatherForecastRepository
            .loadWeatherForecastByCoordinates(latitude, longitude, fetchRequired, Constants.Coordinates.METRIC)
            .observeForever(mockedObserver)

        verify { weatherForecastRemoteDataSource.getWeatherForecastByGeoCoordinates(latitude, longitude, Constants.Coordinates.METRIC) wasNot called }
        verify { weatherForecastLocalDataSource.getWeatherForecast() }

        // Then
        val forecastEntitySlots = mutableListOf<Resource<WeatherForecastEntity>>()
        verify { mockedObserver.onChanged(capture(forecastEntitySlots)) }

        val forecastEntity = forecastEntitySlots[0]
        Truth.assertThat(forecastEntity.status).isEqualTo(Status.SUCCESS)
        Truth.assertThat(forecastEntity.data?.city?.cityName).isEqualTo("Istanbul")
        Truth.assertThat(forecastEntity.data?.id).isEqualTo(1)
    }

    @Test
    fun `given fetchRequired = true, when loadWeatherForecastByCoordinates called, then make sure network called`() {
        // Given
        val fetchRequired = true
        val latitude = 30.0
        val longitude = 34.0
        val weatherForecastLiveData: MutableLiveData<WeatherForecastEntity> = MutableLiveData()
        weatherForecastLiveData.postValue(WeatherForecastEntity(createSampleWeatherForecastResponse()))
        val mockedObserver: Observer<Resource<WeatherForecastEntity>> = mockk(relaxUnitFun = true)

        // When
        every { weatherForecastRemoteDataSource.getWeatherForecastByGeoCoordinates(latitude, longitude, Constants.Coordinates.METRIC) } returns Single.just(
            createSampleWeatherForecastResponse()
        )
        every { weatherForecastLocalDataSource.insertWeatherForecast(createSampleWeatherForecastResponse()) } just runs
        every { weatherForecastLocalDataSource.getWeatherForecast() } returns weatherForecastLiveData

        weatherForecastRepository
            .loadWeatherForecastByCoordinates(latitude, longitude, fetchRequired, Constants.Coordinates.METRIC)
            .observeForever(mockedObserver)

        // Make sure network called
        verify { weatherForecastRemoteDataSource.getWeatherForecastByGeoCoordinates(latitude, longitude, Constants.Coordinates.METRIC) }
        // Make sure db called
        verify { weatherForecastLocalDataSource.getWeatherForecast() }

        // Then
        val weatherForecastEntitySlots = mutableListOf<Resource<WeatherForecastEntity>>()
        verify { mockedObserver.onChanged(capture(weatherForecastEntitySlots)) }

        val weatherForecastEntity = weatherForecastEntitySlots[0]

        Truth.assertThat(weatherForecastEntity.data?.city?.cityName).isEqualTo("Istanbul")
        Truth.assertThat(weatherForecastEntity.data?.id).isEqualTo(0)
    }
}