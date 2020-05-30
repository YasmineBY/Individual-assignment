package com.example.individualassignment.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName


@Entity(tableName = "prayer_table")
data class PrayerObject (

    @SerializedName("fajr")
    var fajr: String,

    @SerializedName("Sunrise")
    var Sunrise: String

)
