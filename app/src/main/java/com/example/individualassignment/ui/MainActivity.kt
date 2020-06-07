package com.example.individualassignment.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
import kotlinx.android.synthetic.main.item_navigation.*
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
        showEditDialog()
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

            var startDay: Int = day.text.toString().toInt()
            var startMonth = month.text.toString().toInt()
            var startYear = year.text.toString().toInt()
            var startingHours = hours.text.toString().toInt()
            var startMinutes = startMinutes.text.toString().toInt()

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


    private fun showEditDialog() {
        val builder = AlertDialog.Builder(this)
        val dialogLayout = layoutInflater.inflate(R.layout.content_edit_custom_prayer, null)
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















