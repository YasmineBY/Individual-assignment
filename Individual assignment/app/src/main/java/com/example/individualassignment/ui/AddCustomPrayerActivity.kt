package com.example.individualassignment.ui

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.individualassignment.R

import kotlinx.android.synthetic.main.activity_add_custom_prayer.*

class AddCustomPrayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_custom_prayer)
        setSupportActionBar(toolbar)


    }

}
