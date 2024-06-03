package com.wadhawan.radioandroidproject.data.network

import com.wadhawan.radioandroidproject.data.network.api.RadioApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * The base URL for the radio station API.
     */
    private const val BASE_URL = "https://de1.api.radio-browser.info/json/"


        val okHttpClient = OkHttpClient().newBuilder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()


    /**
     * Provides an instance of the RadioApiService using lazy initialization.
     * This ensures the service is created only when it's first needed and avoids unnecessary object creation.
     */

    @Provides
    fun provideRadioApiService(): RadioApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RadioApiService::class.java)
    }
}
