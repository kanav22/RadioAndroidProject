package com.wadhawan.radioandroidproject.data.network

import com.wadhawan.radioandroidproject.data.network.api.RadioApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
    val apiService: RadioApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            /**
             * Sets GSON converter to handle JSON serialization and deserialization during network requests.
             */
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RadioApiService::class.java)
    }
}
