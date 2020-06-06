package com.example.individualassignment.ui

import android.content.Intent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
import kotlinx.android.synthetic.main.fragment_navigation.*

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

    private fun preparePrayerDataListView(prayers: Array<PrayerDetails>): ArrayList<ListViewPrayer> {
        val listViewPrayer: ArrayList<ListViewPrayer> = ArrayList()
        for(prayer in prayers){
            listViewPrayer.add(ListViewPrayer(prayer.timings.isha))
            listViewPrayer.add(ListViewPrayer(prayer.timings.fajr))
            listViewPrayer.add(ListViewPrayer(prayer.timings.duhr))
            listViewPrayer.add(ListViewPrayer(prayer.timings.asr))
            listViewPrayer.add(ListViewPrayer(prayer.timings.maghrib))
        }
        return listViewPrayer
    }

    private fun observeViewModel() {
        viewModel.listOfPrayers.observe(this, Observer {
                prayers ->
          var temp =  preparePrayerDataListView(prayers)
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