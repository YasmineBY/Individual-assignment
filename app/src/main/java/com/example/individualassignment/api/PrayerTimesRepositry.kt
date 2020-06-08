package com.example.individualassignment.api

class PrayerTimesRepositry {
    private val prayerTimesApiService: PrayerTimesApiService = PrayerTimeApi.createApi()
    fun getPrayers(country: String, city: String, month: Int, year: Int) = prayerTimesApiService.getPrayerTimes(country, city, month, year)
}