package za.co.shepherd.weatherapp.utils

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import za.co.shepherd.weatherapp.db.entities.*
import za.co.shepherd.weatherapp.domain.model.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
    afterObserve: () -> Unit = {}
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }
    this.observeForever(observer)

    try {
        afterObserve.invoke()

        // Don't wait indefinitely if the LiveData is not set.
        if (!latch.await(time, timeUnit)) {
            throw TimeoutException("LiveData value was never set.")
        }
    } finally {
        this.removeObserver(observer)
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}

// Data Generators
fun createSampleWeatherForecastResponse(id: Int, cityName: String): WeatherForecastEntity {
    val weatherItem = WeatherItem("12d", "clouds", "cloud & sun", 1)
    val weather = listOf(weatherItem)
    val listItem = ListItem(
        123123, Rain(12.0), "132121", Snow(12.0), weather,
        Main(
            34.0,
            30.0,
            2.0,
            321.0,
            21,
            132.0,
            12.0,
            35.0
        ),
        Clouds(1), Sys("a"), Wind(12.0, 12.0)
    )
    val list = listOf(listItem)
    return WeatherForecastEntity(id, CityEntity("Turkey", CoordinatesEntity(34.0, 30.0), cityName, 34), list)
}

fun createSampleForecastWithCoord(id: Int, cityName: String, lat: Double, lon: Double): WeatherForecastEntity {
    val list = emptyList<ListItem>()
    return WeatherForecastEntity(id, CityEntity("Turkey", CoordinatesEntity(lon, lat), cityName, 34), list)
}

fun generateCitiesForSearchEntity(id: String, name: String): CitiesForSearchEntity {
    return CitiesForSearchEntity("Clear", "Turkey", CoordinatesEntity(34.0, 30.0), name, "Beyoglu", 1, id)
}

fun generateCurrentWeatherEntity(name: String, id: Int): CurrentWeatherEntity {
    val weatherItem = WeatherItem("12d", "clouds", "cloud & sun", 1)
    val weather = listOf(weatherItem)
    return CurrentWeatherEntity(1, 2, MainEntity(34.0, 30.0, 2.0, 321.0, 21, 132.0, 12.0, 35.0), null, 3421399123, weather, name, id, "Celciues", null)
}

fun createSampleWeatherForecastResponse(): ForecastResponse {
    val weatherItem = WeatherItem("12d", "clouds", "cloud & sun", 1)
    val weather = listOf(weatherItem)
    val listItem = ListItem(
        123123, Rain(12.0), "132121", Snow(12.0), weather,
        Main(
            34.0,
            30.0,
            2.0,
            321.0,
            21,
            132.0,
            12.0,
            35.0
        ),
        Clouds(1), Sys("a"), Wind(12.0, 12.0)
    )
    val list = listOf(listItem)
    return ForecastResponse(
        City("Turkey", Coordinate(32.32, 30.30), "Istanbul", 10),
        null,
        null,
        null,
        list
    )
}

fun createSampleCurrentWeatherResponse(): CurrentWeatherResponse {
    val weatherItem = WeatherItem("12d", "clouds", "cloud & sun", 1)
    val weather = listOf(weatherItem)
    return CurrentWeatherResponse(
        null, null, Main(34.0, 30.0, 2.0, 321.0, 21, 132.0, 12.0, 35.0),
        Clouds(
            1
        ),
        Sys("a"), null, Coordinate(32.32, 30.30), weather, "Istanbul", null, 10, null, null
    )
}

fun generateSampleSearchCitiesResponse(): SearchResponse {
    return SearchResponse(
        listOf(
            HitsItem(
                "Turkey", null, isCity = true, isCountry = false,
                administrative = listOf(
                    "İstanbul"
                ),
                adminLevel = null, postcode = null, county = listOf("Beyoğlu"), geoloc = null, importance = null, objectID = "10", isSuburb = null, localeNames = null
            )
        )
    )
}
