package com.example.individualassignment.api

import com.example.individualassignment.model.apimodel.ResultObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap


interface PrayerTimesApiService {

//    @GET("v1/calendarByAddress?address=Netherlands, Amsterdam&method=2&month=06&year=2020")
//    @GET("v1/calendarByAddress?address=Netherlands, Amsterdam&method=2")



    @GET("v1/calendarByAddress ")
    fun getPrayerTimes(
        @Query("address") country: String = "Netherlands",
        @Query("address") city: String = "Amsterdam",
        @Query("method") method: String = "2",
        @Query("month") month:String  = "06",
        @Query("year") year: Int = 2020

    ): Call<ResultObject>







//    @GET("v1/calendarByAddress?address=Netherlands, Amsterdam")
//    fun getPrayerTimes(
////    @QueryMap("adress") adress:  Map<String, String>  = "Netherlands, Amsterdam",
////    @QueryMap(("adress") adress:  Map<String, String>  = Map<"Netherlands", "Amsterdam">),
////    var dict: Map<String, Any> = listOf()
//    @Query("method") method: String = "2",
//    @Query("month") month:String  = "06",
//    @Query("year") year: Int = 2020
//
//    ): Call<ResultObject>

    //      v1/calendarByAddress?address=Netherlands, Amsterdam&method=2&month=06&year=2020

    //      v1/calendarByAddress?address=Netherlands, Amsterdam&method=2&month=06&year=2020



//    @GET("v1/{calendarByAddress}")
//    fun getPrayerTimes(
//       @Path( "calendarByAddress") adress: String = "Sultanahmet Mosque, Istanbul",
//       @Query( "adress") adress: String = "Sultanahmet Mosque, Istanbul",
//       @Query("method") method : Int = 2,
//       @Query("month") month : String = "04",
//       @Query("year") year : Int = 2017
//    ): Call<ResultObject>
}

