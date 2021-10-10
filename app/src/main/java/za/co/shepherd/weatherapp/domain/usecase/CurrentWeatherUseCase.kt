package za.co.shepherd.weatherapp.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.map
import za.co.shepherd.weatherapp.core.Constants
import za.co.shepherd.weatherapp.db.entities.CurrentWeatherEntity
import za.co.shepherd.weatherapp.repo.CurrentWeatherRepository
import za.co.shepherd.weatherapp.ui.dashboard.CurrentWeatherViewState
import za.co.shepherd.weatherapp.utils.UseCaseLiveData
import za.co.shepherd.weatherapp.utils.domain.Resource
import javax.inject.Inject

class CurrentWeatherUseCase @Inject internal constructor(
    private val repository: CurrentWeatherRepository
) : UseCaseLiveData<CurrentWeatherViewState, CurrentWeatherUseCase.CurrentWeatherParams, CurrentWeatherRepository>() {

    override fun getRepository(): CurrentWeatherRepository {
        return repository
    }

    override fun buildUseCaseObservable(params: CurrentWeatherParams?): LiveData<CurrentWeatherViewState> {
        return repository.loadCurrentWeatherByGeoCoordinates(
            params?.latitude?.toDouble() ?: 0.0,
            params?.longitude?.toDouble() ?: 0.0,
            params?.fetchRequired
                ?: false,
            units = params?.units ?: Constants.Coordinates.METRIC
        ).map {
            onCurrentWeatherResultReady(it)
        }
    }

    private fun onCurrentWeatherResultReady(resource: Resource<CurrentWeatherEntity>): CurrentWeatherViewState {
        return CurrentWeatherViewState(
            status = resource.status,
            error = resource.message,
            data = resource.data
        )
    }

    class CurrentWeatherParams(
        val latitude: String = "",
        val longitude: String = "",
        val fetchRequired: Boolean,
        val units: String
    ) : Params()
}
