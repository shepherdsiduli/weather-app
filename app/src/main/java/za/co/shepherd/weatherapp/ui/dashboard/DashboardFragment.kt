package za.co.shepherd.weatherapp.ui.dashboard

import android.transition.TransitionInflater
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import za.co.shepherd.weatherapp.R
import za.co.shepherd.weatherapp.core.BaseFragment
import za.co.shepherd.weatherapp.core.Constants
import za.co.shepherd.weatherapp.databinding.FragmentDashboardBinding
import za.co.shepherd.weatherapp.domain.model.ListItem
import za.co.shepherd.weatherapp.domain.usecase.CurrentWeatherUseCase
import za.co.shepherd.weatherapp.domain.usecase.WeatherForecastUseCase
import za.co.shepherd.weatherapp.ui.dashboard.forecast.WeatherForecastAdapter
import za.co.shepherd.weatherapp.ui.home.MainActivity
import za.co.shepherd.weatherapp.utils.extensions.isNetworkAvailable
import za.co.shepherd.weatherapp.utils.extensions.observeWith

@AndroidEntryPoint
class DashboardFragment : BaseFragment<DashboardFragmentViewModel, FragmentDashboardBinding>(
    R.layout.fragment_dashboard,
    DashboardFragmentViewModel::class.java,
) {

    override fun init() {
        super.init()
        initWeatherForecastAdapter()
        sharedElementReturnTransition = TransitionInflater.from(context).inflateTransition(
            android.R.transition.move
        )

        val latitude: String? = binding.viewModel?.sharedPreferences?.getString(Constants.Coordinates.LATITUDE, "")
        val longitude: String? = binding.viewModel?.sharedPreferences?.getString(Constants.Coordinates.LONGITUDE, "")

        if (latitude?.isNotEmpty() == true && longitude?.isNotEmpty() == true) {
            binding.viewModel?.setCurrentWeatherParams(
                CurrentWeatherUseCase.CurrentWeatherParams(
                    latitude,
                    longitude,
                    isNetworkAvailable(requireContext()),
                    Constants.Coordinates.METRIC
                )
            )
            binding.viewModel?.setWeatherForecastParams(
                WeatherForecastUseCase.ForecastParams(
                    latitude,
                    longitude,
                    isNetworkAvailable(requireContext()),
                    Constants.Coordinates.METRIC
                )
            )
        }

        binding.viewModel?.getWeatherForecastViewState()?.observeWith(
            viewLifecycleOwner
        ) {
            with(binding) {
                viewState = it
                it.data?.list?.let { weatherForecasts -> initWeatherForecast(weatherForecasts) }
                (activity as MainActivity).viewModel.toolbarTitle.set(
                    it.data?.city?.getCityAndCountry()
                )
            }
        }

        binding.viewModel?.getCurrentWeatherViewState()?.observeWith(
            viewLifecycleOwner
        ) {
            with(binding) {
                containerForecast.viewState = it
            }
        }
    }

    private fun initWeatherForecastAdapter() {
        val adapter = WeatherForecastAdapter { item, cardView, forecastIcon, dayOfWeek, temp, tempMaxMin ->
            val action = DashboardFragmentDirections.actionDashboardFragmentToWeatherDetailFragment(
                item
            )
            findNavController()
                .navigate(
                    action,
                    FragmentNavigator.Extras.Builder()
                        .addSharedElements(
                            mapOf(
                                cardView to cardView.transitionName,
                                forecastIcon to forecastIcon.transitionName,
                                dayOfWeek to dayOfWeek.transitionName,
                                temp to temp.transitionName,
                                tempMaxMin to tempMaxMin.transitionName
                            )
                        )
                        .build()
                )
        }

        binding.recyclerForecast.adapter = adapter
        binding.recyclerForecast.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        postponeEnterTransition()
        binding.recyclerForecast.viewTreeObserver
            .addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
    }

    private fun initWeatherForecast(list: List<ListItem>) {
        (binding.recyclerForecast.adapter as WeatherForecastAdapter).submitList(list)
    }
}
