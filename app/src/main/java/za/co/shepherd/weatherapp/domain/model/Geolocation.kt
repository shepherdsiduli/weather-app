package za.co.shepherd.weatherapp.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Geolocation(

    @Json(name = "lng")
    val longitude: Double? = null,

    @Json(name = "lat")
    val latitude: Double? = null
)