package com.example.individualassignment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.individualassignment.R
import com.example.individualassignment.model.ListViewPrayer
import kotlinx.android.synthetic.main.prayer_item.view.*

class PrayerTimesAdapter (private val prayers: List<ListViewPrayer>) : RecyclerView.Adapter<PrayerTimesAdapter.ViewHolder>() {


    val prayerNames: List<String> =  listOf("fajr", "sunrise", "duhr", "asr", "maghrib", "isha")

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(prayer: ListViewPrayer) {
            itemView.txtItemPrayeTime.text = prayer.prayername
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrayerTimesAdapter.ViewHolder {
        return ViewHolder (
            LayoutInflater.from(parent.context).inflate(R.layout.prayer_item, parent, false)
        )
    }

    override fun getItemCount(): Int = prayers.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(prayers[position])
    }

}
