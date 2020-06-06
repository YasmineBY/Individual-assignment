package com.example.individualassignment.api

import com.example.individualassignment.model.apimodel.ResultObject
import retrofit2.Call
import retrofit2.http.GET

interface PrayerTimesApiService {

    @GET("v1/calendarByAddress?address=Sultanahmet Mosque, Istanbul, Turkey&method=2&month=04&year=2017")
    fun getPrayerTimes(): Call<ResultObject>
}

