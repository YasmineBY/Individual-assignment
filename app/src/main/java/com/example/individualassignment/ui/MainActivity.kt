package com.example.individualassignment.ui

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.individualassignment.R
import com.example.individualassignment.adapter.CustomPrayerAdapter
import com.example.individualassignment.model.CustomPrayer
import com.example.individualassignment.vm.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_add_custom_prayer.view.*
import kotlinx.android.synthetic.main.content_edit_custom_prayer.view.*
import kotlinx.android.synthetic.main.item_navigation.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var prayers: ArrayList<CustomPrayer>
    private lateinit var recyclerView: RecyclerView
    private lateinit var customPrayerAdapter: CustomPrayerAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private val viewModel: MainActivityViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        initViews()
    }

    fun initViews() {
        initalizeRecyclerView()
        initNavigation()
        createSharedPreferences()
    }

    fun checkPreferenceValue(stringValue: String?): Boolean {
        return stringValue.toString().trim().length != 0
    }

    fun createSharedPreferences() {
        val prefs = this.getSharedPreferences(USER_LOCATION, 0)
        val editor = prefs.edit()

        if (!checkPreferenceValue(prefs.getString("COUNTRY", ""))) {
            editor.putString("COUNTRY", "Netherlands")
        }
        if (!checkPreferenceValue(prefs.getString("CITY", ""))) {
            editor.putString("CITY", "Amsterdam")
        }

        editor.apply()
    }

    private fun initalizeRecyclerView() {

        recyclerView = findViewById(R.id.rvCustomPrayers)
        prayers = arrayListOf()
        customPrayerAdapter = CustomPrayerAdapter(prayers,
            { customPrayer -> showEditDialog(customPrayer) })

        viewManager = LinearLayoutManager(this)
        createItemTouchHelper().attachToRecyclerView(recyclerView)

        recyclerView.addItemDecoration(
            DividerItemDecoration(
                this@MainActivity,
                DividerItemDecoration.VERTICAL
            )
        )

        observeViewModel()

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = customPrayerAdapter
        }
    }


    private fun observeViewModel() {
        viewModel.listOfPrayers.observe(this, Observer { prayers ->
            this@MainActivity.prayers.clear()
            this@MainActivity.prayers.addAll(prayers)
            customPrayerAdapter.notifyDataSetChanged()
        })
    }


    fun initNavigation() {
        btnListRetrievePrayers.setOnClickListener {
            val intent = Intent(this@MainActivity, PrayerTimesActivity::class.java)
            startActivity(intent)
        }
        btnAddPrayers.setOnClickListener {
            showAddNewCustomPrayer()
        }

    }


    private fun showAddNewCustomPrayer() {
        val builder = AlertDialog.Builder(this)
        val dialogLayout = layoutInflater.inflate(R.layout.content_add_custom_prayer, null)
        builder.setView(dialogLayout)
        builder.show()
        var prayerName = dialogLayout.findViewById<EditText>(R.id.etCustomPrayerName)

        var day = dialogLayout.findViewById<EditText>(R.id.etDay)
        var month = dialogLayout.findViewById<EditText>(R.id.etMonth)
        var year = dialogLayout.findViewById<EditText>(R.id.etYear)
        var hours = dialogLayout.findViewById<EditText>(R.id.etStartHours)
        var startMinutes = dialogLayout.findViewById<EditText>(R.id.etStartMinutes)

        var endDayText = dialogLayout.findViewById<EditText>(R.id.etEndDay)
        var endMonthText = dialogLayout.findViewById<EditText>(R.id.etEndMonth)
        var endYearText = dialogLayout.findViewById<EditText>(R.id.etEndYear)
        var endTimeText = dialogLayout.findViewById<EditText>(R.id.etEndHours)
        var etEndTimeMinutes = dialogLayout.findViewById<EditText>(R.id.etEndMinutes)

        fun getNewPrayer(): CustomPrayer {
            var startOfPrayerDate = Calendar.getInstance()
            var endOfPrayerDate = Calendar.getInstance()

            var newName: String = prayerName.getText().toString()

            var startDay: Int = day.text.toString().toInt()
            var startMonth = month.text.toString().toInt() - 1
            var startYear = year.text.toString().toInt()
            var startingHours = hours.text.toString().toInt()
            var startMinutes = startMinutes.text.toString().toInt()

            var endingDay: Int = endDayText.text.toString().toInt()
            var endingMonth = endMonthText.text.toString().toInt() - 1
            var endingYear = endYearText.text.toString().toInt()
            var endingTimeHours = endTimeText.text.toString().toInt()
            var endingTimeMinutes = etEndTimeMinutes.text.toString().toInt()

            startOfPrayerDate.set(startYear, startMonth, startDay, startingHours, startMinutes)
            endOfPrayerDate.set(
                endingYear,
                endingMonth,
                endingDay,
                endingTimeHours,
                endingTimeMinutes
            )


            var newCustomPrayer: CustomPrayer = CustomPrayer(
                newName,
                startOfPrayerDate.time,
                endOfPrayerDate.time
            )

            return newCustomPrayer
        }

        dialogLayout.btnCancelCustomPrayer.setOnClickListener {
            val intent = Intent(this@MainActivity, MainActivity::class.java)
            startActivity(intent)
        }

        dialogLayout.btnAddCustomPrayer.setOnClickListener {
            viewModel.addNewCustomPrayer(getNewPrayer())
            val intent = Intent(this@MainActivity, MainActivity::class.java)
            startActivity(intent)
        }


    }


    fun updateExistingPrayer(dialogLayout: View, prayerId: Long?): CustomPrayer {
        var prayerName =
            dialogLayout.findViewById<EditText>(R.id.etEditCustomPrayerName).text.toString()
        var startDay = dialogLayout.findViewById<EditText>(R.id.etEditDay).text.toString().toInt()
        var startMonth =
            dialogLayout.findViewById<EditText>(R.id.etEditMonth).text.toString().toInt() - 1
        var startYear = dialogLayout.findViewById<EditText>(R.id.etEditYear).text.toString().toInt()
        var startHour =
            dialogLayout.findViewById<EditText>(R.id.etEditStartHours).text.toString().toInt()
        var startMinutes =
            dialogLayout.findViewById<EditText>(R.id.etEditStartMinutes).text.toString().toInt()
        var endDay = dialogLayout.findViewById<EditText>(R.id.etEditEndDay).text.toString().toInt()
        var endMonth =
            dialogLayout.findViewById<EditText>(R.id.etEditEndMonth).text.toString().toInt() - 1
        var endYear =
            dialogLayout.findViewById<EditText>(R.id.etEditEndYear).text.toString().toInt()
        var endHour =
            dialogLayout.findViewById<EditText>(R.id.etEditEndHours).text.toString().toInt()
        var endMinutes =
            dialogLayout.findViewById<EditText>(R.id.etEditEndMinutes).text.toString().toInt()
        var startDate = Calendar.getInstance()
        var endDate = Calendar.getInstance()

        startDate.set(startYear, startMonth, startDay, startHour, startMinutes)
        endDate.set(endYear, endMonth, endDay, endHour, endMinutes)

        var newPrayer: CustomPrayer = CustomPrayer(
            prayerName,
            startDate.time,
            endDate.time,
            prayerId

        )
        return newPrayer
    }

    fun prefilllDialog(dialogLayout: View, prayer: CustomPrayer) {
        var receivedStartPrayerDay = prayer.startTime.time
        var receivedEndPrayerDay = prayer.endTime.time


        var dayFormat: SimpleDateFormat? = SimpleDateFormat("d")
        var monthFormat: SimpleDateFormat? = SimpleDateFormat("M")
        var yearFormat: SimpleDateFormat? = SimpleDateFormat("yyyy")
        var hourFormat: SimpleDateFormat? = SimpleDateFormat("h")
        var minuteformat: SimpleDateFormat? = SimpleDateFormat("m")

        var editPrayerName = dialogLayout.findViewById<EditText>(R.id.etEditCustomPrayerName)
            .setText(prayer.prayerName)

        var editStartDay = dialogLayout.findViewById<EditText>(R.id.etEditDay)
            .setText(dayFormat?.format(receivedStartPrayerDay))
        var editStartMonth = dialogLayout.findViewById<EditText>(R.id.etEditMonth)
            .setText(monthFormat?.format(receivedStartPrayerDay))
        var editStartYear = dialogLayout.findViewById<EditText>(R.id.etEditYear)
            .setText(yearFormat?.format(receivedStartPrayerDay))
        var editStartHours = dialogLayout.findViewById<EditText>(R.id.etEditStartHours)
            .setText(hourFormat?.format(receivedStartPrayerDay))
        var editStartMinutes = dialogLayout.findViewById<EditText>(R.id.etEditStartMinutes)
            .setText(minuteformat?.format(receivedStartPrayerDay))


        var editEndDay = dialogLayout.findViewById<EditText>(R.id.etEditEndDay)
            .setText(dayFormat?.format(receivedEndPrayerDay))
        var editEndMonth = dialogLayout.findViewById<EditText>(R.id.etEditEndMonth)
            .setText(monthFormat?.format(receivedEndPrayerDay))
        var editEndYear = dialogLayout.findViewById<EditText>(R.id.etEditEndYear)
            .setText(yearFormat?.format(receivedEndPrayerDay))
        var editEndtHours = dialogLayout.findViewById<EditText>(R.id.etEditEndHours)
            .setText(hourFormat?.format(receivedEndPrayerDay))
        var editEndMinutes = dialogLayout.findViewById<EditText>(R.id.etEditEndMinutes)
            .setText(minuteformat?.format(receivedEndPrayerDay))
    }


    private fun showEditDialog(prayer: CustomPrayer) {
        val builder = AlertDialog.Builder(this)
        val dialogLayout = layoutInflater.inflate(R.layout.content_edit_custom_prayer, null)

        prefilllDialog(dialogLayout, prayer)
        dialogLayout.btnEditCustomPrayer.setOnClickListener {
            viewModel.updateCustomPrayer(updateExistingPrayer(dialogLayout, prayer.id))
            val intent = Intent(this@MainActivity, MainActivity::class.java)
            startActivity(intent)
        }
        dialogLayout.btnCancelEdit.setOnClickListener {
            val intent = Intent(this@MainActivity, MainActivity::class.java)
            startActivity(intent)
        }
        builder.setView(dialogLayout)
        builder.show()
    }


    private fun createItemTouchHelper(): ItemTouchHelper {

        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            //Callback triggered when a user swiped an item.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                var customPrayerToDelete = prayers[position]
                viewModel.deleteCustomPrayer(customPrayerToDelete)

            }
        }
        return ItemTouchHelper(callback)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                viewModel.deleteAllCustomPrayers()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

}















