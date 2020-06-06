package com.example.individualassignment.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
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

        val prefs = this.getSharedPreferences("CUSTOM_PRAYER", 0)
        val editor = prefs.edit()

        editor.putString("PRAYER_NAME", prayer.prayerName)
        editor.putString("PRAYER_DATE", prayer.prayerDate.toString())
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
            prayer.prayerDate,
            prayer.endTime,
            prayer.startTime,
            prayer.id

        )

//        bundle.putString(PRAYER_NAME, prayer.prayerName)s


//        var selectedPrayer = prayer
        val intent = Intent(this@MainActivity, PopupEditCustomPrayerActivity::class.java)
        val extras: Bundle? = intent.extras
        intent.putExtra(CUSTOM_PRAYER,customPrayer)

        startActivity(intent)




    }

    private fun observeViewModel() {
        viewModel.listOfPrayers.observe(this, Observer {
                prayers ->
            this@MainActivity.prayers.clear()
            this@MainActivity.prayers.addAll(prayers)
            customPrayerAdapter.notifyDataSetChanged()
        })
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


    fun initNavigation() {
        val calendar = Calendar.getInstance()
        calendar.set(2020,1,1)
        calendar.set(Calendar.HOUR_OF_DAY, 22)


        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault())
        println(sdf)
        println("HELLO WORLD")

        var customPrayer: CustomPrayer = CustomPrayer (
            "TESTING TIME SETTING22222222222S",
            calendar.time,
            calendar.time,
            calendar.time
        )


        btnListRetrievePrayers.setOnClickListener {
            val intent = Intent(this@MainActivity, PrayerTimesActivity::class.java)
            startActivity(intent)
        }
        btnHome.setOnClickListener {
            viewModel.addNewCustomPrayer(customPrayer)
        }
        btnAddPrayers.setOnClickListener {
            val intent = Intent(this@MainActivity, AddCustomPrayerActivity::class.java)
            startActivity(intent)
        }

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

}















