package za.co.shepherd.weatherapp.db.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import kotlinx.parcelize.Parcelize
import za.co.shepherd.weatherapp.domain.model.Coordinate
import za.co.shepherd.weatherapp.domain.model.Geolocation

@Parcelize
@Entity(tableName = "Coord")
class CoordinatesEntity (
    @ColumnInfo(name = "lon")
    val longitude: Double?,
    @ColumnInfo(name = "lat")
    val latitude: Double?
) : Parcelable {
    @Ignore
    constructor(coordinate: Coordinate) : this(
        longitude = coordinate.lon,
        latitude = coordinate.lat
    )

    @Ignore
    constructor(geolocation: Geolocation?) : this(
        longitude = geolocation?.longitude,
        latitude = geolocation?.latitude
    )
}
