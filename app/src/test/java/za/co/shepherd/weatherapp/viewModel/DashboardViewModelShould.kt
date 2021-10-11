package za.co.shepherd.weatherapp.viewModel

import android.content.SharedPreferences
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import za.co.shepherd.weatherapp.domain.usecase.CurrentWeatherUseCase
import za.co.shepherd.weatherapp.domain.usecase.WeatherForecastUseCase
import za.co.shepherd.weatherapp.ui.dashboard.CurrentWeatherViewState
import za.co.shepherd.weatherapp.ui.dashboard.DashboardFragmentViewModel
import za.co.shepherd.weatherapp.ui.dashboard.WeatherForecastViewState
import za.co.shepherd.weatherapp.utils.domain.Status

@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(AndroidJUnit4::class)
class DashboardViewModelShould {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var currentWeatherUseCase: CurrentWeatherUseCase

    @MockK
    lateinit var weatherForecastUseCase: WeatherForecastUseCase

    @MockK
    lateinit var sharedPreferences: SharedPreferences

    private lateinit var dashboardFragmentViewModel: DashboardFragmentViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dashboardFragmentViewModel = DashboardFragmentViewModel(
            weatherForecastUseCase,
            currentWeatherUseCase,
            sharedPreferences
        )
    }

    @Test
    fun `given loading state, when setWeatherForecastParams called, then update live data for loading status`() {
        // Given
        val viewStateObserver: Observer<WeatherForecastViewState> = mockk(relaxUnitFun = true)
        dashboardFragmentViewModel.getWeatherForecastViewState().observeForever(viewStateObserver)

        val viewStateLiveData: MutableLiveData<WeatherForecastViewState> = MutableLiveData()
        viewStateLiveData.postValue(WeatherForecastViewState(Status.LOADING, null, null))

        // When
        every { weatherForecastUseCase.execute(any()) } returns viewStateLiveData
        dashboardFragmentViewModel.setWeatherForecastParams(
            WeatherForecastUseCase.ForecastParams("30", "32", true, "metric")
        )

        // Then
        val weatherForecastViewStateSlots = mutableListOf<WeatherForecastViewState>()
        verify { viewStateObserver.onChanged(capture(weatherForecastViewStateSlots)) }

        val loadingState = weatherForecastViewStateSlots[0]
        Truth.assertThat(loadingState.status).isEqualTo(Status.LOADING)
    }

    @Test
    fun `given error state, when setWeatherForecastParams called, then update live data for error status`() {
        // Given
        val viewStateObserver: Observer<WeatherForecastViewState> = mockk(relaxUnitFun = true)
        dashboardFragmentViewModel.getWeatherForecastViewState().observeForever(viewStateObserver)

        val viewStateLiveData: MutableLiveData<WeatherForecastViewState> = MutableLiveData()
        viewStateLiveData.postValue(WeatherForecastViewState(Status.ERROR, "Unhandled Exception", null))

        // When
        every { weatherForecastUseCase.execute(any()) } returns viewStateLiveData
        dashboardFragmentViewModel.setWeatherForecastParams(
            WeatherForecastUseCase.ForecastParams("30", "32", true, "metric")
        )

        // Then
        val weatherForecastViewStateSlots = mutableListOf<WeatherForecastViewState>()
        verify { viewStateObserver.onChanged(capture(weatherForecastViewStateSlots)) }

        val errorState = weatherForecastViewStateSlots[0]
        Truth.assertThat(errorState.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `given success state, when setWeatherForecastParams called, then update live data for success status`() {
        // Given
        val viewStateObserver: Observer<WeatherForecastViewState> = mockk(relaxUnitFun = true)
        dashboardFragmentViewModel.getWeatherForecastViewState().observeForever(viewStateObserver)

        val viewStateLiveData: MutableLiveData<WeatherForecastViewState> = MutableLiveData()
        viewStateLiveData.postValue(WeatherForecastViewState(Status.SUCCESS, null, null))

        // When
        every { weatherForecastUseCase.execute(any()) } returns viewStateLiveData
        dashboardFragmentViewModel.setWeatherForecastParams(
            WeatherForecastUseCase.ForecastParams("30", "32", true, "metric")
        )

        // Then
        val weatherForecastViewStateSlots = mutableListOf<WeatherForecastViewState>()
        verify { viewStateObserver.onChanged(capture(weatherForecastViewStateSlots)) }

        val successState = weatherForecastViewStateSlots[0]
        Truth.assertThat(successState.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun `given loading state, when setCurrentWeatherParams called, then update live data for loading status`() {
        // Given
        val viewStateObserver: Observer<CurrentWeatherViewState> = mockk(relaxUnitFun = true)
        dashboardFragmentViewModel.getCurrentWeatherViewState().observeForever(viewStateObserver)

        val viewStateLiveData: MutableLiveData<CurrentWeatherViewState> = MutableLiveData()
        viewStateLiveData.postValue(CurrentWeatherViewState(Status.LOADING, null, null))

        // When
        every { currentWeatherUseCase.execute(any()) } returns viewStateLiveData
        dashboardFragmentViewModel.setCurrentWeatherParams(
            CurrentWeatherUseCase.CurrentWeatherParams("30", "32", true, "metric")
        )

        // Then
        val currentWeatherViewStateSlots = mutableListOf<CurrentWeatherViewState>()
        verify { viewStateObserver.onChanged(capture(currentWeatherViewStateSlots)) }

        val loadingState = currentWeatherViewStateSlots[0]
        Truth.assertThat(loadingState.status).isEqualTo(Status.LOADING)
    }

    @Test
    fun `given error state, when setCurrentWeatherParams called, then update live data for error status`() {
        // Given
        val viewStateObserver: Observer<CurrentWeatherViewState> = mockk(relaxUnitFun = true)
        dashboardFragmentViewModel.getCurrentWeatherViewState().observeForever(viewStateObserver)

        val viewStateLiveData: MutableLiveData<CurrentWeatherViewState> = MutableLiveData()
        viewStateLiveData.postValue(CurrentWeatherViewState(Status.ERROR, null, null))

        // When
        every { currentWeatherUseCase.execute(any()) } returns viewStateLiveData
        dashboardFragmentViewModel.setCurrentWeatherParams(
            CurrentWeatherUseCase.CurrentWeatherParams("30", "32", true, "metric")
        )

        // Then
        val currentWeatherViewStateSlots = mutableListOf<CurrentWeatherViewState>()
        verify { viewStateObserver.onChanged(capture(currentWeatherViewStateSlots)) }

        val errorState = currentWeatherViewStateSlots[0]
        Truth.assertThat(errorState.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `given success state, when setCurrentWeatherParams called, then update live data for success status`() {
        // Given
        val viewStateObserver: Observer<CurrentWeatherViewState> = mockk(relaxUnitFun = true)
        dashboardFragmentViewModel.getCurrentWeatherViewState().observeForever(viewStateObserver)

        val viewStateLiveData: MutableLiveData<CurrentWeatherViewState> = MutableLiveData()
        viewStateLiveData.postValue(CurrentWeatherViewState(Status.SUCCESS, null, null))

        // When
        every { currentWeatherUseCase.execute(any()) } returns viewStateLiveData
        dashboardFragmentViewModel.setCurrentWeatherParams(
            CurrentWeatherUseCase.CurrentWeatherParams("30", "32", true, "metric")
        )

        // Then
        val currentWeatherViewStateSlots = mutableListOf<CurrentWeatherViewState>()
        verify { viewStateObserver.onChanged(capture(currentWeatherViewStateSlots)) }

        val successState = currentWeatherViewStateSlots[0]
        Truth.assertThat(successState.status).isEqualTo(Status.SUCCESS)
    }
}