package za.co.shepherd.weatherapp.ui.search

import za.co.shepherd.weatherapp.core.BaseViewState
import za.co.shepherd.weatherapp.db.entities.CitiesForSearchEntity
import za.co.shepherd.weatherapp.utils.domain.Status

class SearchViewState(
    val status: Status,
    val error: String? = null,
    val data: List<CitiesForSearchEntity>? = null
) : BaseViewState(status, error) {
    fun getSearchResult() = data
}