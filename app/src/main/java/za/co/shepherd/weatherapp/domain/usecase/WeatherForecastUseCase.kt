package za.co.shepherd.weatherapp.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import za.co.shepherd.weatherapp.core.Constants
import za.co.shepherd.weatherapp.db.entities.WeatherForecastEntity
import za.co.shepherd.weatherapp.repo.WeatherForecastRepository
import za.co.shepherd.weatherapp.ui.dashboard.WeatherForecastMapper
import za.co.shepherd.weatherapp.ui.dashboard.WeatherForecastViewState
import za.co.shepherd.weatherapp.utils.UseCaseLiveData
import za.co.shepherd.weatherapp.utils.domain.Resource
import javax.inject.Inject

class WeatherForecastUseCase @Inject internal constructor(private val repository: WeatherForecastRepository) : UseCaseLiveData<WeatherForecastViewState, WeatherForecastUseCase.ForecastParams, WeatherForecastRepository>() {

    override fun getRepository(): WeatherForecastRepository {
        return repository
    }

    override fun buildUseCaseObservable(params: ForecastParams?): LiveData<WeatherForecastViewState> {
        return repository.loadForecastByCoordinates(
            params?.latitude?.toDouble() ?: 0.0,
            params?.longitude?.toDouble() ?: 0.0,
            params?.fetchRequired
                ?: false,
            units = params?.units ?: Constants.Coordinates.METRIC
        ).map {
            onForecastResultReady(it)
        }
    }

    private fun onForecastResultReady(resource: Resource<WeatherForecastEntity>): WeatherForecastViewState {
        val mappedList = resource.data?.list?.let { WeatherForecastMapper().mapFrom(it) }
        resource.data?.list = mappedList

        return WeatherForecastViewState(
            status = resource.status,
            error = resource.message,
            data = resource.data
        )
    }

    class ForecastParams(
        val latitude: String = "",
        val longitude: String = "",
        val fetchRequired: Boolean,
        val units: String
    ) : Params()
}
