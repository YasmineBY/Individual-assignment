package com.example.individualassignment.api

import com.example.individualassignment.model.apimodel.ResultObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap


interface PrayerTimesApiService {

    @GET("v1/calendarByAddress ")
    fun getPrayerTimes(
        @Query("address") country: String,
        @Query("address") city: String ,
        @Query("month") month:Int,
        @Query("year") year: Int,
        @Query("method") method: Int = 2

    ): Call<ResultObject>

}

