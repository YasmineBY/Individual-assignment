package com.example.individualassignment.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "timings_table")
data class TimesObject(

    @SerializedName("timings")
    var timings: List<PrayerObject>


)