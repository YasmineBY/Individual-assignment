package com.example.individualassignment.api

import com.example.individualassignment.model.PrayerResults
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PrayerTimesApiService {

    @GET("v1/calendarByAddress?address=Sultanahmet Mosque, Istanbul, Turkey&method=2&month=04&year=2017")
    fun getPrayerTimes(): Call<PrayerResults>
}

