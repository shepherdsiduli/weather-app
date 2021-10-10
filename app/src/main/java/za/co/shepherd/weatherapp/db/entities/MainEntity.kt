package za.co.shepherd.weatherapp.db.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import kotlinx.parcelize.Parcelize
import za.co.shepherd.weatherapp.domain.model.Main

@Parcelize
@Entity(tableName = "Main")
data class MainEntity(
    @ColumnInfo(name = "temp")
    var temp: Double?,
    @ColumnInfo(name = "tempMin")
    var tempMin: Double?,
    @ColumnInfo(name = "grndLevel")
    var grndLevel: Double?,
    @ColumnInfo(name = "tempKf")
    var tempKf: Double?,
    @ColumnInfo(name = "humidity")
    var humidity: Int?,
    @ColumnInfo(name = "pressure")
    var pressure: Double?,
    @ColumnInfo(name = "seaLevel")
    var seaLevel: Double?,
    @ColumnInfo(name = "tempMax")
    var tempMax: Double?
) : Parcelable {
    @Ignore
    constructor(main: Main?) : this(
        temp = main?.temp,
        tempMin = main?.tempMin,
        grndLevel = main?.grndLevel,
        tempKf = main?.tempKf,
        humidity = main?.humidity,
        pressure = main?.pressure,
        seaLevel = main?.seaLevel,
        tempMax = main?.tempMax
    )

    fun getTempString(): String {
        return temp.toString().substringBefore(".") + "°"
    }

    fun getHumidityString(): String {
        return humidity.toString() + "°"
    }
}