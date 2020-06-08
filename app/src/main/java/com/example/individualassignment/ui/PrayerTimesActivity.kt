package com.example.individualassignment.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.individualassignment.R
import com.example.individualassignment.adapter.PrayerTimesAdapter
import com.example.individualassignment.model.ListViewPrayer
import com.example.individualassignment.model.apimodel.PrayerDetails
import com.example.individualassignment.vm.PrayerTimesActivityViewModel
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.content_retrieved_prayers.*
import kotlinx.android.synthetic.main.item_navigation.*
import java.util.*
import kotlin.collections.ArrayList

const val USER_LOCATION = "user_location"
//todo remove all PrintLn
class PrayerTimesActivity : AppCompatActivity() {

    private lateinit var listViewPrayers: ArrayList<ListViewPrayer>
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var prayerTimesAdapter: PrayerTimesAdapter
    private val viewModel: PrayerTimesActivityViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrieved_prayers)
        initViews()
        val prefs = this.getSharedPreferences(USER_LOCATION, 0)
        var prefCountry = prefs.getString("COUNTRY", "")
        var prefCity = prefs.getString("CITY", "")

        txtCurrentLocation.text = "Current location: ${prefCountry}, ${prefCity}"
    }


    fun initViews() {
        iconLoading.visibility = View.VISIBLE
        initalizeRecyclerView()
        initNavigation()
        retrievePrayersBasedOnLocation()
        getDefaultLocationPrayers()
        btnSearchPrayers.setOnClickListener {
            retrievePrayersBasedOnLocation()
        }

    }

    fun checkIfFieldIsEmpty(fieldText: TextInputEditText): Boolean {
        return fieldText.getText().toString().trim().length != 0
    }

    fun checkFields(): Boolean {
        var validationCheck = false
        var missingFields = ""
        var missingAllFields = "Missing values: Country, City"
        var missingCity = "Missing value:  City"
        var missingCountry = "Missing value: Country"

        if (!checkIfFieldIsEmpty(etRetrievedPrayerCountry) && !checkIfFieldIsEmpty(
                etRetrievedPrayerCity
            )
        ) {
            missingFields = missingAllFields
            validationCheck = true
        } else if (!checkIfFieldIsEmpty(etRetrievedPrayerCountry)) {
            missingFields = missingCountry
            validationCheck = true
        } else if (!checkIfFieldIsEmpty(etRetrievedPrayerCity)) {
            missingFields = missingCity
            validationCheck = true
        }

        if (validationCheck) {
            Toast.makeText(this, missingFields, Toast.LENGTH_SHORT).show();
        }
        return validationCheck

    }

    fun getDefaultLocationPrayers() {
        var currentDate = Calendar.getInstance()
        val currentMonth: Int = currentDate.get(Calendar.MONTH).toInt() + 1
        val currentYear: Int = currentDate.get(Calendar.YEAR).toInt()

        val prefs = this.getSharedPreferences(USER_LOCATION, 0)
        var prefCountry = prefs.getString("COUNTRY", "").toString()
        var prefCity = prefs.getString("CITY", "").toString()


        viewModel.getPrayerTimes(prefCountry, prefCity, currentMonth, currentYear)
    }



    //todo look at month in all calendar functions
    //todo implement shared preferences manager for the lcation
    fun retrievePrayersBasedOnLocation() {
        var currentDate = Calendar.getInstance()
        val currentMonth: Int = currentDate.get(Calendar.MONTH) + 1
        val currentYear: Int = currentDate.get(Calendar.YEAR)
        var newCountry = etRetrievedPrayerCountry.text.toString()
        var newCity = etRetrievedPrayerCity.text.toString()

        val prefs = this.getSharedPreferences(USER_LOCATION, 0)
        var prefCountry = prefs.getString("COUNTRY", "")
        var prefCity = prefs.getString("CITY", "")
        var currentlocationText =  "Current location: ${prefCountry}, ${prefCity}"
        var updatedLocationText =  "Current location: ${newCountry}, ${newCity}"
        txtCurrentLocation.text = currentlocationText

        if (!checkFields()) {
            updateSharedPreferences(newCountry, newCity)
            txtCurrentLocation.text = updatedLocationText
            viewModel.getPrayerTimes(prefCountry!!, prefCity!!, currentMonth, currentYear)
        }
    }



    fun updateSharedPreferences(country: String, city: String) {
        val prefs = this.getSharedPreferences(USER_LOCATION, 0)
        val editor = prefs.edit()

        editor.putString("COUNTRY", country)
        editor.putString("CITY", city)

        editor.apply()
    }




    private fun preparePrayerDataListView(prayers: Array<PrayerDetails>): ArrayList<ListViewPrayer> {
        var currentDate = Calendar.getInstance()
        var dayOfMonth: Int = currentDate.get(Calendar.DAY_OF_MONTH)
        var prayersOfToday = prayers[dayOfMonth]
        var listViewPrayer: ArrayList<ListViewPrayer> = ArrayList()

        listViewPrayer.add(ListViewPrayer("fajr", prayersOfToday.timings.fajr))
        listViewPrayer.add(ListViewPrayer("sunrise", prayersOfToday.timings.sunrise))
        listViewPrayer.add(ListViewPrayer("duhr", prayersOfToday.timings.duhr))
        listViewPrayer.add(ListViewPrayer("asr", prayersOfToday.timings.asr))
        listViewPrayer.add(ListViewPrayer("maghrib", prayersOfToday.timings.maghrib))
        listViewPrayer.add(ListViewPrayer("isha", prayersOfToday.timings.isha))


        return listViewPrayer
    }

    private fun observeViewModel() {
        viewModel.listOfPrayers.observe(this, Observer { prayers ->
            var temp = preparePrayerDataListView(prayers)
            this@PrayerTimesActivity.listViewPrayers.clear()
            this@PrayerTimesActivity.listViewPrayers.addAll(preparePrayerDataListView(prayers))
            prayerTimesAdapter.notifyDataSetChanged()
            iconLoading.visibility = View.GONE
        })
    }

    private fun initalizeRecyclerView() {

        recyclerView = findViewById(R.id.rvRetrievedPrayers)
        listViewPrayers = arrayListOf()
        prayerTimesAdapter = PrayerTimesAdapter(listViewPrayers)
        viewManager = LinearLayoutManager(this)

        observeViewModel()

        recyclerView.addItemDecoration(
            DividerItemDecoration(
                this@PrayerTimesActivity,
                DividerItemDecoration.VERTICAL
            )
        )

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = prayerTimesAdapter
        }

    }

    //todo backtrack later to add the Edit screen
    fun initNavigation() {
        btnHome.setOnClickListener {
            val intent = Intent(this@PrayerTimesActivity, MainActivity::class.java)
            startActivity(intent)
        }

    }
}