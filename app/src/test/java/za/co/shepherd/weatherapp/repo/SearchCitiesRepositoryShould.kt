package za.co.shepherd.weatherapp.repo

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import za.co.shepherd.weatherapp.domain.datasources.searchCities.SearchCitiesLocalDataSource
import za.co.shepherd.weatherapp.domain.datasources.searchCities.SearchCitiesRemoteDataSource

//@Config(sdk = [Build.VERSION_CODES.P])
//@RunWith(AndroidJUnit4::class)
class SearchCitiesRepositoryShould {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var searchCitiesRemoteDataSource: SearchCitiesRemoteDataSource

    @MockK
    lateinit var searchCitiesLocalDataSource: SearchCitiesLocalDataSource

    private lateinit var searchCitiesRepository: SearchCitiesRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        searchCitiesRepository = SearchCitiesRepository(searchCitiesRemoteDataSource, searchCitiesLocalDataSource)
    }
}