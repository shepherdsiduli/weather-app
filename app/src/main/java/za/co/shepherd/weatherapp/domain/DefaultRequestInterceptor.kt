package za.co.shepherd.weatherapp.domain

import okhttp3.Interceptor
import okhttp3.Response
import za.co.shepherd.weatherapp.core.Constants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultRequestInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url
            .newBuilder()
            .addQueryParameter(
                Constants.OpenWeatherNetworkService.API_KEY_QUERY,
                Constants.OpenWeatherNetworkService.API_KEY_VALUE
            )
            .build()
        val request = chain.request().newBuilder().url(url).build()
        return chain.proceed(request)
    }
}