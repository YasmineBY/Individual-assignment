package com.example.individualassignment.ui

import android.content.Intent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.individualassignment.R
import com.example.individualassignment.adapter.PrayerTimesAdapter
import com.example.individualassignment.model.PrayerObject
import com.example.individualassignment.vm.PrayerTimesActivityViewModel
import kotlinx.android.synthetic.main.fragment_navigation.*

class PrayerTimesActivity : AppCompatActivity() {

    private lateinit var prayers: ArrayList<PrayerObject>
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var prayerTimesAdapter: PrayerTimesAdapter
    private val viewModel: PrayerTimesActivityViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrieved_prayers)
        initNavigation()
        initViews()
    }


    fun initViews() {

    }

//    private fun initalizeRecyclerView() {
//
//        recyclerView = findViewById(R.id.rvMovies)
//        movies = arrayListOf()
//        movieAdapter = MovieAdapter(
//            movies,
//            { movies -> onMovieClick(movies) })
//        viewManager = GridLayoutManager(this, 2)
//
//        observeViewModel()
//
//        recyclerView.apply {
//            setHasFixedSize(true)
//            layoutManager = viewManager
//            adapter = movieAdapter
//        }
//    }


    //todo backtrack later to add the Edit screen
    fun initNavigation() {
        btnHome.setOnClickListener {
            val intent = Intent(this@PrayerTimesActivity, MainActivity::class.java)
            startActivity(intent)
        }
        btnListRetrievePrayers.setOnClickListener {
            viewModel.getPrayerTimes()
        }

    }


}
