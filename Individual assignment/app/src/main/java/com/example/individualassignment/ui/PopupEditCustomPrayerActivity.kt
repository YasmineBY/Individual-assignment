package com.example.individualassignment.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import com.example.individualassignment.R

class PopupEditCustomPrayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_custom_prayer)

        var displayMetrics  = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        var width: Int = displayMetrics.widthPixels
        var height: Int = displayMetrics.heightPixels
        window.setLayout((width*.8).toInt(),  (height*.6).toInt())
    }


}
