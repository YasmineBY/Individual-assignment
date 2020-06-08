package com.example.individualassignment.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
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
import kotlinx.android.synthetic.main.content_retrieved_prayers.*
import kotlinx.android.synthetic.main.item_navigation.*
import java.util.*
import kotlin.collections.ArrayList

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
    }


    fun initViews() {
        iconLoading.visibility = View.VISIBLE
        initalizeRecyclerView()
        initNavigation()
        viewModel.getPrayerTimes()
    }

    fun getPrayerTimesOfToday() {
//        month=04&year=2017

    }


    private fun preparePrayerDataListView(prayers: Array<PrayerDetails>): ArrayList<ListViewPrayer> {
        //to-do
        //Make API call: This month, this year. : ListOfPrayers[Today] only

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