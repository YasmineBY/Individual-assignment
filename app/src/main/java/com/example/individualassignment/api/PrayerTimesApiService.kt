package com.example.individualassignment.api

import com.example.individualassignment.model.apimodel.ResultObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PrayerTimesApiService {

    @GET("v1/calendarByAddress?address=Sultanahmet Mosque, Istanbul, Turkey&method=2&month=04&year=2017")
    fun getPrayerTimes(): Call<ResultObject>

    //    https://api.aladhan.com/v1/calendarByAddress?address=Sultanahmet%20Mosque,%20Istanbul,%20Turkey&method=2&month=04&year=2017
    //   https://api.aladhan.com/v1/calendarByAddress?&adress=Sultanahmet%20Mosque%2C%20Istanbul&method=2&month=04&year=2017


    //    https://api.aladhan.com/v1/calendarByAddress?&adress=Sultanahmet%20Mosque%2C%20Istanbul%2C%20Turkey&method=2&month=4&year=2017
    //    https://api.aladhan.com/v1/calendarByAddress?&adress=Sultanahmet%20Mosque%2CIstanbul%2C%20Turkey&method=2&month=4&year=2017
    //    https://api.aladhan.com/v1/calendarByAddress?&adress=Sultanahmet%20Mosque%2C%20Istanbul%2C%20Turkey&method=2&month=4&year=2017

//    @GET("v1/{calendarByAddress}")
//    fun getPrayerTimes(
//       @Path( "calendarByAddress") adress: String = "Sultanahmet Mosque, Istanbul",
//       @Query( "adress") adress: String = "Sultanahmet Mosque, Istanbul",
//       @Query("method") method : Int = 2,
//       @Query("month") month : String = "04",
//       @Query("year") year : Int = 2017
//    ): Call<ResultObject>
}

