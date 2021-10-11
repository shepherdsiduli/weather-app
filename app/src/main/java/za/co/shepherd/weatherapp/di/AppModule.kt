package za.co.shepherd.weatherapp.di

import android.content.Context
import android.preference.PreferenceManager
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context) = PreferenceManager.getDefaultSharedPreferences(context)//Configuration.getInstance().load(this, androidx.preference.PreferenceManager.getDefaultSharedPreferences(this))

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().build()

}