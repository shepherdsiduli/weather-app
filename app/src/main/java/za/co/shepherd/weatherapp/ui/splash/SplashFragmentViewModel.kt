package za.co.shepherd.weatherapp.ui.splash

import android.content.SharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import za.co.shepherd.weatherapp.core.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class SplashFragmentViewModel @Inject constructor(
    var sharedPreferences: SharedPreferences
) : BaseViewModel() {
    var navigateDashboard = false
}