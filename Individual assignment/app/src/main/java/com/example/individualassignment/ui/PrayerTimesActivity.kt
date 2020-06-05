package com.example.individualassignment.ui

import android.content.Intent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.individualassignment.R
import com.example.individualassignment.adapter.PrayerTimesAdapter
import com.example.individualassignment.model.apimodel.PrayerDetails
import com.example.individualassignment.vm.PrayerTimesActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_navigation.*

class PrayerTimesActivity : AppCompatActivity() {

    private lateinit var prayers: ArrayList<PrayerDetails>
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
        initalizeRecyclerView()
        initNavigation()
        viewModel.getPrayerTimes()
    }

    private fun observeViewModel() {
        viewModel.listOfPrayers.observe(this, Observer {
                prayers ->
            this@PrayerTimesActivity.prayers.clear()
            this@PrayerTimesActivity.prayers.addAll(prayers)
            prayerTimesAdapter.notifyDataSetChanged()
        })
    }

    private fun initalizeRecyclerView() {

        recyclerView = findViewById(R.id.rvRetrievedPrayers)
        prayers = ArrayList<PrayerDetails>()
        prayerTimesAdapter = PrayerTimesAdapter(prayers)
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