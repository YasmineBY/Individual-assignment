package com.example.individualassignment.api

class PrayerTimesRepositry {

    private val prayerTimesApiService: PrayerTimesApiService = PrayerTimeApi.createApi()
    fun getPrayers() = prayerTimesApiService.getPrayerTimes()

}