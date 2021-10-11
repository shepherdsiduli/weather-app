package za.co.shepherd.weatherapp.ui.search.response

import androidx.databinding.ObservableField
import dagger.hilt.android.lifecycle.HiltViewModel
import za.co.shepherd.weatherapp.core.BaseViewModel
import za.co.shepherd.weatherapp.db.entities.CitiesForSearchEntity
import javax.inject.Inject

@HiltViewModel
class SearchResponseItemViewModel @Inject internal constructor() : BaseViewModel() {
    var item = ObservableField<CitiesForSearchEntity>()
}