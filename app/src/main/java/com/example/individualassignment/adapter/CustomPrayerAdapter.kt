package com.example.individualassignment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.individualassignment.R
import com.example.individualassignment.model.CustomPrayer
import kotlinx.android.synthetic.main.custom_prayer_item.view.*
import kotlinx.android.synthetic.main.prayer_item.view.*


class CustomPrayerAdapter( private val customPrayers: List<CustomPrayer> , private val onClick: (CustomPrayer) -> Unit):

    RecyclerView.Adapter<CustomPrayerAdapter.ViewHolder>() {
    private lateinit var context: Context

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener { onClick(customPrayers[adapterPosition]) }
        }

        fun bind(customPrayer: CustomPrayer) {
            itemView.txtCustomPrayerName.text = customPrayer.prayerName
            itemView.txtCustomPrayerTime.text = customPrayer.startTime.toString()
//            itemView.txtCustomPrayerEndTime.text = customPrayer.endTime.toString()
//            itemView.txtCustomPrayerDate.text = customPrayer.prayerDate.toString()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        context = parent.context

        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.custom_prayer_item, parent, false)
        )
    }

    override fun getItemCount(): Int = customPrayers.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(customPrayers[position])
    }

}