package com.example.individualassignment.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "timings_table")
data class PrayerResult(

    @SerializedName("timings")
    var timings: List<String>

//    @SerializedName("timings")
//    var timings: List<PrayerObject>,
//
//    @SerializedName("date")
//    var date: PrayerDateObject

)