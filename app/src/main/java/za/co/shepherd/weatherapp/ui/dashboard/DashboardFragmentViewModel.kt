package za.co.shepherd.weatherapp.ui.dashboard

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import dagger.hilt.android.lifecycle.HiltViewModel
import za.co.shepherd.weatherapp.core.BaseViewModel
import za.co.shepherd.weatherapp.domain.usecase.CurrentWeatherUseCase
import za.co.shepherd.weatherapp.domain.usecase.WeatherForecastUseCase
import javax.inject.Inject

@HiltViewModel
class DashboardFragmentViewModel @Inject internal constructor(
    private val weatherForecastUseCase: WeatherForecastUseCase,
    private val currentWeatherUseCase: CurrentWeatherUseCase,
    var sharedPreferences: SharedPreferences
) : BaseViewModel() {

    private val _weatherForecastParams: MutableLiveData<WeatherForecastUseCase.ForecastParams> = MutableLiveData()
    private val _currentWeatherParams: MutableLiveData<CurrentWeatherUseCase.CurrentWeatherParams> = MutableLiveData()

    fun getWeatherForecastViewState() = weatherForecastViewState
    fun getCurrentWeatherViewState() = currentWeatherViewState

    private val weatherForecastViewState: LiveData<WeatherForecastViewState> = _weatherForecastParams.switchMap { params ->
        weatherForecastUseCase.execute(params)
    }
    private val currentWeatherViewState: LiveData<CurrentWeatherViewState> = _currentWeatherParams.switchMap { params ->
        currentWeatherUseCase.execute(params)
    }

    fun setWeatherForecastParams(params: WeatherForecastUseCase.ForecastParams) {
        if (_weatherForecastParams.value == params) {
            return
        }
        _weatherForecastParams.postValue(params)
    }

    fun setCurrentWeatherParams(params: CurrentWeatherUseCase.CurrentWeatherParams) {
        if (_currentWeatherParams.value == params) {
            return
        }
        _currentWeatherParams.postValue(params)
    }
}
