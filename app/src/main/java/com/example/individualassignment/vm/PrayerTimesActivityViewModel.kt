package com.example.individualassignment.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.individualassignment.api.PrayerTimesRepositry
import com.example.individualassignment.model.apimodel.PrayerDetails
import com.example.individualassignment.model.apimodel.PrayerObject
import com.example.individualassignment.model.apimodel.ResultObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PrayerTimesActivityViewModel(application: Application) : AndroidViewModel(application) {

        var prayerTimesRepositry: PrayerTimesRepositry = PrayerTimesRepositry()
        var error = MutableLiveData<String>()
//        var listOfPrayers = MutableLiveData<ResultObject>()
        var listOfPrayers = MutableLiveData<Array<PrayerDetails>>()
             var listOfPrayersOnly = MutableLiveData<List<PrayerObject>>()


    fun getPrayerTimes() {
        prayerTimesRepositry.getPrayers().enqueue(object : Callback<ResultObject> {
            override fun onResponse(call: Call<ResultObject>, response: Response<ResultObject>) {
                if (response.isSuccessful) {
                    var results = response.body()
                    if (results != null) {
                        listOfPrayers.value = results.data
                    }
                    println(results)
                } else error.value = "An error occurred: ${response.errorBody().toString()}"
            }

            override fun onFailure(call: Call<ResultObject>, t: Throwable) {
                error.value = t.message
            }
        })
    }
}