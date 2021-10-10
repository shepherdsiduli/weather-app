package za.co.shepherd.weatherapp.ui.search

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import za.co.shepherd.weatherapp.core.BaseViewModel
import za.co.shepherd.weatherapp.core.Constants
import za.co.shepherd.weatherapp.db.entities.CoordinatesEntity
import za.co.shepherd.weatherapp.domain.usecase.SearchCitiesUseCase
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject internal constructor(
    private val useCase: SearchCitiesUseCase,
    private val pref: SharedPreferences
) : BaseViewModel() {

    private val _searchParams: MutableLiveData<SearchCitiesUseCase.SearchCitiesParams> = MutableLiveData()
    fun getSearchViewState() = searchViewState

    private val searchViewState: LiveData<SearchViewState> = _searchParams.switchMap { params ->
        useCase.execute(params)
    }

    fun setSearchParams(params: SearchCitiesUseCase.SearchCitiesParams) {
        if (_searchParams.value == params) {
            return
        }
        _searchParams.postValue(params)
    }

    fun saveCoordinatesToSharedPref(coordinatesEntity: CoordinatesEntity): Single<String>? {
        return Single.create<String> {
            pref.edit().putString(Constants.Coordinates.LATITUDE, coordinatesEntity.latitude.toString()).apply()
            pref.edit().putString(Constants.Coordinates.LONGITUDE, coordinatesEntity.longitude.toString()).apply()
            it.onSuccess("")
        }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }
}
