package com.example.individualassignment.ui

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


const val CUSTOM_PRAYER: String = "CUSTOM_PRAYER"

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
    }


    private fun initalizeRecyclerView() {

        recyclerView = findViewById(R.id.rvCustomPrayers)
        prayers = arrayListOf()
        customPrayerAdapter = CustomPrayerAdapter(prayers,
            { customPrayer -> onCustomPrayerClick(customPrayer) })

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


    private fun onCustomPrayerClick(prayer: CustomPrayer) {
        showEditDialog(prayer)
        val prefs = this.getSharedPreferences("CUSTOM_PRAYER", 0)
        val editor = prefs.edit()

        editor.putString("PRAYER_NAME", prayer.prayerName)
        editor.putString("START_TIME", prayer.startTime.toString())
        editor.putString("END_TIME", prayer.endTime.toString())
        editor.putString("ID", prayer.id.toString())
        editor.commit()


//        data class CustomPrayer (
//            var prayerName: String,
//            var prayerDate: Date,
//            var startTime: Date,
//            var endTime: Date,
//            @PrimaryKey(autoGenerate = true)
//            var id: Long? = null
        var customPrayer = CustomPrayer(
            prayer.prayerName,
            prayer.endTime,
            prayer.startTime,
            prayer.id

        )

//        bundle.putString(PRAYER_NAME, prayer.prayerName)s


//        var selectedPrayer = prayer
//        val intent = Intent(this@MainActivity, PopupEditCustomPrayerActivity::class.java)
        val extras: Bundle? = intent.extras
        intent.putExtra(CUSTOM_PRAYER, customPrayer)

        startActivity(intent)


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
        btnHome.setOnClickListener {
//            viewModel.addNewCustomPrayer(customPrayer)
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

        var endDayText = dialogLayout.findViewById<EditText>(R.id.etDay)
        var endMonthText = dialogLayout.findViewById<EditText>(R.id.etMonth)
        var endYearText = dialogLayout.findViewById<EditText>(R.id.etYear)
        var endTimeText = dialogLayout.findViewById<EditText>(R.id.etEndHours)
        var etEndTimeMinutes = dialogLayout.findViewById<EditText>(R.id.etEndMinutes)

        fun getNewPrayer(): CustomPrayer {
            var startOfPrayerDate = Calendar.getInstance()
            var endOfPrayerDate = Calendar.getInstance()

            var newName: String = prayerName.getText().toString()

            //Retrieve the data for the start of prayer date
            var startDay: Int = day.text.toString().toInt()
            var startMonth = month.text.toString().toInt()
            var startYear = year.text.toString().toInt()
            var startingHours = hours.text.toString().toInt()
            var startMinutes = startMinutes.text.toString().toInt()

            //Retrieve the data for the end of prayer date
            var endingDay: Int = endDayText.text.toString().toInt()
            var endingMonth = endMonthText.text.toString().toInt()
            var endingYear = endYearText.text.toString().toInt()
            var endingTimeHours = endTimeText.text.toString().toInt()
            var endingTimeMinutes = etEndTimeMinutes.text.toString().toInt()

            startOfPrayerDate.set(startYear, startDay, startMonth)
            startOfPrayerDate.set(Calendar.HOUR_OF_DAY, startingHours)
            endOfPrayerDate.set(Calendar.MINUTE, startMinutes)

            endOfPrayerDate.set(endingYear, endingDay, endingMonth)
            endOfPrayerDate.set(Calendar.HOUR_OF_DAY, endingTimeHours)
            endOfPrayerDate.set(Calendar.MINUTE, endingTimeMinutes)


            var newCustomPrayer: CustomPrayer = CustomPrayer(
                newName,
                startOfPrayerDate.time,
                endOfPrayerDate.time
            )

            return newCustomPrayer
        }



        dialogLayout.btnCancelCustomPrayer.setOnClickListener {
            viewModel.addNewCustomPrayer(getNewPrayer())
        }
    }




    fun createUpdatedPrayer(dialogLayout: View, prayerId: Long?): CustomPrayer {

        var editedPrayerName = dialogLayout.findViewById<EditText>(R.id.etEditCustomPrayerName).getText().toString()

        var day = dialogLayout.findViewById<EditText>(R.id.etEditDay).getText().toString().toInt()
        var month = dialogLayout.findViewById<EditText>(R.id.etEditMonth).getText().toString().toInt()
        var year = dialogLayout.findViewById<EditText>(R.id.etEditYear).getText().toString().toInt()
        var hours = dialogLayout.findViewById<EditText>(R.id.etEditStartHours).getText().toString().toInt()
        var startMinutes = dialogLayout.findViewById<EditText>(R.id.etEditStartMinutes).getText().toString().toInt()

        var endDayText = dialogLayout.findViewById<EditText>(R.id.etEditEndDay).getText().toString().toInt()
        var endMonthText = dialogLayout.findViewById<EditText>(R.id.etEditEndMonth).getText().toString().toInt()
        var endYearText = dialogLayout.findViewById<EditText>(R.id.etEditEndYear).getText().toString().toInt()
        var endHoursText = dialogLayout.findViewById<EditText>(R.id.etEditEndHours).getText().toString().toInt()
        var etEndTimeMinutes = dialogLayout.findViewById<EditText>(R.id.etEditEndMinutes).getText().toString().toInt()

        var startOfPrayerDate = Calendar.getInstance()
        var endOfPrayerDate = Calendar.getInstance()

        //TODO CHANGE THIS AT ALL CALENDAR CREATIONS
        startOfPrayerDate.set(year, month, day)
        startOfPrayerDate.set(Calendar.HOUR_OF_DAY, hours)
        startOfPrayerDate.set(Calendar.MINUTE, startMinutes)


        endOfPrayerDate.set(endYearText, endDayText, endMonthText)
        endOfPrayerDate.set(Calendar.HOUR_OF_DAY, endHoursText)
        endOfPrayerDate.set(Calendar.MINUTE, etEndTimeMinutes)

        var newCustomPrayer: CustomPrayer = CustomPrayer(
            editedPrayerName,
            startOfPrayerDate.time,
            endOfPrayerDate.time,
            prayerId
        )
//
        return newCustomPrayer

    }

    fun prefilllDialog(dialogLayout: View, prayer: CustomPrayer)    {

        var receivedStartPrayerDay = prayer.startTime.time
        var receivedEndPrayerDay = prayer.endTime.time
        var dayFormat: SimpleDateFormat? = SimpleDateFormat("dd")
        var monthFormat: SimpleDateFormat? = SimpleDateFormat("MM")
        var yearFormat: SimpleDateFormat? = SimpleDateFormat("yyyy")
        var hourFormat: SimpleDateFormat? = SimpleDateFormat("hh")
        var minuteformat: SimpleDateFormat? = SimpleDateFormat("mm")


        var editPrayerName = dialogLayout.findViewById<EditText>(R.id.etEditCustomPrayerName).setText(prayer.prayerName)

        var editStartDay = dialogLayout.findViewById<EditText>(R.id.etEditDay).setText(dayFormat?.format(receivedStartPrayerDay))
        var editStartMonth = dialogLayout.findViewById<EditText>(R.id.etEditMonth).setText(monthFormat?.format(receivedStartPrayerDay))
        var editStartYear = dialogLayout.findViewById<EditText>(R.id.etEditYear).setText(yearFormat?.format(receivedStartPrayerDay))
        var editStartHours = dialogLayout.findViewById<EditText>(R.id.etEditStartHours).setText(hourFormat?.format(receivedStartPrayerDay))
        var editStartMinutes = dialogLayout.findViewById<EditText>(R.id.etEditStartMinutes).setText(minuteformat?.format(receivedStartPrayerDay))


        var editEndDay = dialogLayout.findViewById<EditText>(R.id.etEditEndDay).setText(dayFormat?.format(receivedEndPrayerDay))
        var editEndMonth = dialogLayout.findViewById<EditText>(R.id.etEditEndMonth).setText(monthFormat?.format(receivedEndPrayerDay))
        var editEndYear = dialogLayout.findViewById<EditText>(R.id.etEditEndYear).setText(yearFormat?.format(receivedEndPrayerDay))
        var editEndtHours = dialogLayout.findViewById<EditText>(R.id.etEditEndHours).setText(hourFormat?.format(receivedEndPrayerDay))
        var editEndMinutes = dialogLayout.findViewById<EditText>(R.id.etEditEndMinutes).setText(minuteformat?.format(receivedEndPrayerDay))

    }


    private fun showEditDialog(prayer: CustomPrayer) {
        val builder = AlertDialog.Builder(this)
        val dialogLayout = layoutInflater.inflate(R.layout.content_edit_custom_prayer, null)

        prefilllDialog(dialogLayout, prayer)



        dialogLayout.btnEditCustomPrayer.setOnClickListener {
            viewModel.updateCustomPrayer(createUpdatedPrayer(dialogLayout, prayer.id))
        }
        dialogLayout.btnCancelEdit.setOnClickListener {
           //todo dismiss window
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

            //          Callback triggered when a user swiped an item.
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }

    }

}















