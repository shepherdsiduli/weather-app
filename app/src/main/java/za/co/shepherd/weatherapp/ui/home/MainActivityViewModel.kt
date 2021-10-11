package za.co.shepherd.weatherapp.ui.home

import androidx.databinding.ObservableField
import dagger.hilt.android.lifecycle.HiltViewModel
import za.co.shepherd.weatherapp.core.BaseViewModel
import javax.inject.Inject


@HiltViewModel
class MainActivityViewModel @Inject internal constructor() : BaseViewModel() {
    var toolbarTitle: ObservableField<String> = ObservableField()
}