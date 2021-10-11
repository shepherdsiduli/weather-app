package za.co.shepherd.weatherapp.ui.weather_detail.weatherHourOfDay

import androidx.databinding.ObservableField
import za.co.shepherd.weatherapp.core.BaseViewModel
import za.co.shepherd.weatherapp.domain.model.ListItem
import javax.inject.Inject

class WeatherHourOfDayItemViewModel @Inject internal constructor() : BaseViewModel() {
    var item = ObservableField<ListItem>()
}