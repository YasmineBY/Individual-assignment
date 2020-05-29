package com.example.individualassignment.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.MutableLiveData
import com.example.individualassignment.PrayerTimesApiService
import com.example.individualassignment.R
import com.example.individualassignment.model.PrayerResults

import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            testCall()
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
        }
    }

    fun testCall()  {
      var call =   createApi()
        val movie = MutableLiveData<PrayerResults>()
        val error = MutableLiveData<String>()

        call.getPrayerTimes().enqueue(object : Callback<PrayerResults> {
            override fun onResponse(call: Call<PrayerResults>, response: Response<PrayerResults>) {
                if (response.isSuccessful) {
                    var results = response.body()
                    println(results)
                } else error.value = "An error occurred: ${response.errorBody().toString()}"
            }

            override fun onFailure(call: Call<PrayerResults>, t: Throwable) {
                error.value = t.message
            }
        })
    }

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }





    }

}















