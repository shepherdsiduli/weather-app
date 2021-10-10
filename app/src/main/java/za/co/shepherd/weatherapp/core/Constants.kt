package za.co.shepherd.weatherapp.core

object Constants {
    object OpenWeatherNetworkService {
        const val BASE_URL = "http://api.openweathermap.org/data/2.5/"
        const val API_KEY_VALUE = "751d80f6c314139192ffcb62c107e654"
        const val RATE_LIMITER_TYPE = "data"
        const val API_KEY_QUERY = "appid"
    }

    object AlgoliaKeys {
        const val APPLICATION_ID = "plNW8IW0YOIN"
        const val SEARCH_API_KEY = "029766644cb160efa51f2a32284310eb"
    }

    object Coordinates {
        const val LATITUDE = "lat"
        const val LONGITUDE = "lon"
        const val METRIC = "metric"
    }
}