package za.co.shepherd.weatherapp.domain.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class City(

    @Json(name = "country")
    val country: String?,

    @Json(name = "coord")
    val coordinate: Coordinate?,

    @Json(name = "name")
    val name: String?,

    @Json(name = "id")
    val id: Int?
) : Parcelable