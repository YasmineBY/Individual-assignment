package com.example.individualassignment.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.individualassignment.api.PrayerTimesRepositry
import com.example.individualassignment.model.PrayerResults
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PrayerTimesActivityViewModel(application: Application) : AndroidViewModel(application) {

        var prayerTimesRepositry: PrayerTimesRepositry = PrayerTimesRepositry()
        var error = MutableLiveData<String>()
        var listOfPrayers = MutableLiveData<PrayerResults>()


    fun getPrayerTimes() {
        prayerTimesRepositry.getPrayers().enqueue(object : Callback<PrayerResults> {
            override fun onResponse(call: Call<PrayerResults>, response: Response<PrayerResults>) {
                if (response.isSuccessful) {
                    var results = response.body()
                    println(results)
                    listOfPrayers.value = results
                } else error.value = "An error occurred: ${response.errorBody().toString()}"
            }

            override fun onFailure(call: Call<PrayerResults>, t: Throwable) {
                error.value = t.message
            }
        })
    }
}