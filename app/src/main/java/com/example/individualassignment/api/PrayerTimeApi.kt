package com.example.individualassignment.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PrayerTimeApi()  {
    companion object {

        fun createApi(): PrayerTimesApiService {
            val baseUrl = "https://api.aladhan.com/"

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

            val movieApi = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return movieApi.create(PrayerTimesApiService::class.java)
        }
    }
}
