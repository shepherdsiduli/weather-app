package za.co.shepherd.weatherapp.db.entities

import android.os.Parcelable
import android.text.SpannableString
import androidx.room.*
import kotlinx.parcelize.Parcelize
import za.co.shepherd.weatherapp.domain.model.HitsItem
import za.co.shepherd.weatherapp.utils.extensions.bold
import za.co.shepherd.weatherapp.utils.extensions.italic
import za.co.shepherd.weatherapp.utils.extensions.plus
import za.co.shepherd.weatherapp.utils.extensions.spannable

@Parcelize
@Entity(tableName = "CitiesForSearch")
data class CitiesForSearchEntity(
    @ColumnInfo(name = "administrative")
    val administrative: String?,
    @ColumnInfo(name = "Country")
    val country: String?,
    @Embedded
    val coordinates: CoordinatesEntity?,
    @ColumnInfo(name = "fullName")
    val name: String?,
    @ColumnInfo(name = "county")
    val county: String?,
    @ColumnInfo(name = "importance")
    val importance: Int?,
    @PrimaryKey
    @ColumnInfo(name = "Id")
    val id: String
) : Parcelable {
    @Ignore
    constructor(hitsItem: HitsItem?) : this(
        country = hitsItem?.country,
        importance = hitsItem?.importance,
        administrative = hitsItem?.administrative?.first(),
        coordinates = CoordinatesEntity(hitsItem?.geoloc),
        name = hitsItem?.localeNames?.first(),
        county = hitsItem?.county?.first(),
        id = hitsItem?.objectID.toString()
    )

    fun getFullName(): SpannableString {
        return spannable {
            bold(name ?: "").plus(", ") +
                    bold(county ?: "").plus(", ") +
                    italic(administrative ?: "").plus(", ") +
                    italic(country ?: "")
        }
    }
}