package com.example.individualassignment.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.activity.viewModels
import com.example.individualassignment.R
import com.example.individualassignment.model.CustomPrayer
import com.example.individualassignment.vm.EditCustomPrayerViewModel
import kotlinx.android.synthetic.main.activity_edit_custom_prayer.*
import java.util.*

class PopupEditCustomPrayerActivity : AppCompatActivity() {
    private val viewModel: EditCustomPrayerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_custom_prayer)
        this.getSupportActionBar()?.hide();
        createPopup()
        retrieveExistingPrayerData()

        intitViews()
    }

    fun intitViews() {
        btnCancelEdit.setOnClickListener {
            buttonCancelOnClick()
        }
        btnUpdateEdit.setOnClickListener {
            buttonOnUpdateClick()
        }

        retrieveExistingPrayerData()
    }

    fun createPopup() {
        var displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        var width: Int = displayMetrics.widthPixels
        var height: Int = displayMetrics.heightPixels
        window.setLayout((width * .8).toInt(), (height * .6).toInt())
    }

    //todo this should update or something? I dont know maybe after you click finish it should update
    fun retrieveExistingPrayerData(): CustomPrayer {
        val prefs = this.getSharedPreferences(CUSTOM_PRAYER, 0)

        var prayerName = prefs.getString("PRAYER_NAME", "")
        var prayerDate = prefs.getString("PRAYER_DATE", "")
        var startTime = prefs.getString("START_TIME", "")
        var endTime = prefs.getString("END_TIME", "")
        var id = prefs.getString("ID", "")

        etCustomPrayerNameEdit.setText(prayerName)
        println(prayerName)

        val calendar = Calendar.getInstance()
        calendar.set(2020,1,1)
        calendar.set(Calendar.HOUR_OF_DAY, 14);

        var newName = "HELLO WORLD 2020"

        var updatedPrayer = CustomPrayer(
            newName,
            calendar.time,
            calendar.time,
            calendar.time,
            id?.toLong()
        )
        return  updatedPrayer
    }



    fun buttonOnUpdateClick() {
        var customPrayer =  retrieveExistingPrayerData()
        viewModel.updateCustomPrayer(customPrayer)
        finish()
    }


    fun buttonCancelOnClick() {
        finish()
    }


}
