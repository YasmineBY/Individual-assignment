package com.example.individualassignment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.individualassignment.R
import com.example.individualassignment.model.PrayerObject
import kotlinx.android.synthetic.main.prayer_item.view.*

class PrayerTimesAdapter (private val prayers: List<PrayerObject>) : RecyclerView.Adapter<PrayerTimesAdapter.ViewHolder>() {



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(prayer: PrayerObject) {
            itemView.txtItemPrayerName.text = prayer.fajr
//            itemView.txtGameName.text = game.title
//            itemView.txtPlatform.text = game.platform
//            itemView.txtDate.text = game.releaseDate.toString()
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
