package za.co.shepherd.weatherapp.db.entities

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize
import za.co.shepherd.weatherapp.domain.model.ForecastResponse
import za.co.shepherd.weatherapp.domain.model.ListItem

@Parcelize
@Entity(tableName = "Forecast")
data class WeatherForecastEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,

    @Embedded
    var city: CityEntity?,

    @ColumnInfo(name = "list")
    var list: List<ListItem>?
) : Parcelable {

    @Ignore
    constructor(forecastResponse: ForecastResponse) : this(
        id = 0,
        city = forecastResponse.city?.let { CityEntity(it) },
        list = forecastResponse.list
    )
}