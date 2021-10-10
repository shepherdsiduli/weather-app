package za.co.shepherd.weatherapp.ui.dashboard.forecast

import androidx.databinding.ObservableField
import dagger.hilt.android.lifecycle.HiltViewModel
import za.co.shepherd.weatherapp.core.BaseViewModel
import za.co.shepherd.weatherapp.domain.model.ListItem
import javax.inject.Inject

@HiltViewModel
class WeatherForecastItemViewModel @Inject internal constructor() : BaseViewModel() {
    var item = ObservableField<ListItem>()
}