package com.example.individualassignment.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "object_tables")
data class PrayerDetails (

    @SerializedName("timings")
    var timings: PrayerObject,

    @SerializedName("date")
    var date: PrayerDateObject
)
